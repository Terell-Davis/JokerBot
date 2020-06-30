package com.tyskyworks.kakorot.command.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.tyskyworks.kakorot.Config;
import com.tyskyworks.kakorot.command.CommandContext;
import com.tyskyworks.kakorot.command.ICommand;
import com.tyskyworks.kakorot.command.music.musicassets.GuildMusicManager;
import com.tyskyworks.kakorot.command.music.musicassets.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.Arrays;
import java.util.List;

public class VolumeCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        AudioManager audioManager = ctx.getGuild().getAudioManager();
        PlayerManager manager = PlayerManager.getInstance();
        GuildMusicManager music = manager.getGuildMusicManager(ctx.getGuild());
        AudioPlayer player = music.player;

        if (ctx.getAuthor().isBot()) return;

        if (player.getPlayingTrack() == null) {
            channel.sendMessage("The player isn't playing anything").queue();
            return;
        }

        String[] args = ctx.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(Config.get("prefix") + "volume"))  {
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
                    success.setColor(0xd703fc);
                    success.setTitle("🔊 Volume set to:" + args[1]);
                    ctx.getChannel().sendMessage(success.build()).queue();
                } catch (IllegalArgumentException e) {
                    if (e.toString().startsWith("java.lang.IllegalArgumentException: Message retrieval")){
                        EmbedBuilder error = new EmbedBuilder();
                        error.setColor(0xff3923);
                        error.setTitle("⛔ The hell did you put to get this?");
                        error.setDescription("Really what did you do??????");
                        ctx.getChannel().sendMessage(error.build()).queue();
                    }

                }

            }
        }
        if (args[0].equalsIgnoreCase(Config.get("prefix") + "vol"))  {
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
                    success.setColor(0xd703fc);
                    success.setTitle("🔊 Volume set to:" + args[1]);
                    ctx.getChannel().sendMessage(success.build()).queue();
                } catch (IllegalArgumentException e) {
                    if (e.toString().startsWith("java.lang.IllegalArgumentException: Message retrieval")){
                        EmbedBuilder error = new EmbedBuilder();
                        error.setColor(0xff3923);
                        error.setTitle("⛔ The hell did you put to get this?");
                        error.setDescription("Really what did you do??????");
                        ctx.getChannel().sendMessage(error.build()).queue();
                    }

                }

            }
        }
        if (args[0].equalsIgnoreCase(Config.get("prefix") + "v"))  {
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
                    success.setColor(0xd703fc);
                    success.setTitle("🔊 Volume set to:" + args[1]);
                    ctx.getChannel().sendMessage(success.build()).queue();
                } catch (IllegalArgumentException e) {
                    if (e.toString().startsWith("java.lang.IllegalArgumentException: Message retrieval")){
                        EmbedBuilder error = new EmbedBuilder();
                        error.setColor(0xff3923);
                        error.setTitle("⛔ The hell did you put to get this?");
                        error.setDescription("Really what did you do??????");
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
        return Arrays.asList("vol", "v");
    }
}
