package com.tyskyworks.kakorot.command.music;

import com.google.common.primitives.Ints;
import com.tyskyworks.kakorot.Config;
import com.tyskyworks.kakorot.command.CommandContext;
import com.tyskyworks.kakorot.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VolumeCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        AudioManager audioManager = ctx.getGuild().getAudioManager();
        PlayerManager manager = PlayerManager.getInstance();
        GuildMusicManager music = manager.getGuildMusicManager(ctx.getGuild());

        if (ctx.getAuthor().isBot()) return;

        String[] args = ctx.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(Config.get("prefix") + "volume")) {
            if (args.length < 2) {
                // Usage
                EmbedBuilder usage = new EmbedBuilder();
                usage.setColor(0xff3923);
                usage.setTitle("Specify Volume");
                usage.setDescription("Usage: `" + Config.get("prefix") + "Volume [volume]`");
                ctx.getChannel().sendMessage(usage.build()).queue();
            } else {
                try {
                    music.player.setVolume(Integer.parseInt(args[1]));
                    // Success
                    EmbedBuilder success = new EmbedBuilder();
                    success.setColor(0x22ff2a);
                    success.setTitle("🍆 Volume set to:" + args[1]);
                    ctx.getChannel().sendMessage(success.build()).queue();
                } catch (IllegalArgumentException e) {
                    if (e.toString().startsWith("java.lang.IllegalArgumentException: Message retrieval")){
                        // Too many messages
                        EmbedBuilder error = new EmbedBuilder();
                        error.setColor(0xff3923);
                        error.setTitle("⛔ The Fuck did you put to get this");
                        error.setDescription("Really what the hell");
                        ctx.getChannel().sendMessage(error.build()).queue();
                    }

                }

            }
        }
    }

    @Override
    public String getName() {
        return "volume";
    }

    @Override
    public String getHelp() {
        return "Changes the volume";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("vol", "sound", "v");
    }
}
