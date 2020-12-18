package com.tyskyworks.kakorot.commands.music.control;

import com.tyskyworks.kakorot.commands.CommandContext;
import com.tyskyworks.kakorot.commands.ICommand;
import com.tyskyworks.kakorot.commands.music.musicassets.GuildMusicManager;
import com.tyskyworks.kakorot.commands.music.musicassets.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.Arrays;
import java.util.List;

public class BassBoostCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        PlayerManager manager = PlayerManager.getInstance();
        GuildMusicManager music = manager.getGuildMusicManager(ctx.getGuild());

        if (ctx.getAuthor().isBot()) return;

        try {
            music.player.setVolume(2147483647);
            EmbedBuilder success = new EmbedBuilder();
            success.setColor(0xf51707);
            success.setTitle("🔊 FEEL THE BASS! 🔊");
            ctx.getChannel().sendMessage(success.build()).queue();
        } catch (IllegalArgumentException e) {
            if (e.toString().startsWith("java.lang.IllegalArgumentException: Message retrieval")) {
                EmbedBuilder error = new EmbedBuilder();
                error.setColor(0xf51707);
                error.setTitle("⛔ The hell did you put to get this");
                error.setDescription("Really please let me know");
                ctx.getChannel().sendMessage(error.build()).queue();
            }
        }
    }

    @Override
    public String getName() {
        return "BassBoot";
    }

    @Override
    public String getHelp() {
        return "Feel the bass";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("bb", "bass", "loud");
    }
}
