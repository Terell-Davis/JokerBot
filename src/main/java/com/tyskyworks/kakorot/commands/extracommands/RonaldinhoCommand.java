package com.tyskyworks.kakorot.commands.extracommands;

import com.tyskyworks.kakorot.commands.CommandContext;
import com.tyskyworks.kakorot.commands.ICommand;
import com.tyskyworks.kakorot.commands.music.musicassets.JoinCommand;
import com.tyskyworks.kakorot.commands.music.musicassets.PlayerManager;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.Arrays;
import java.util.List;

public class RonaldinhoCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        AudioManager audioManager = ctx.getGuild().getAudioManager();
        final TextChannel channel = ctx.getChannel();

        if (!audioManager.isConnected()) {
            JoinCommand join = new JoinCommand();
            join.handle(ctx);
        }

        PlayerManager manager = PlayerManager.getInstance();

        manager.loadAndPlay(ctx.getChannel(), "https://www.youtube.com/watch?v=bQoLZWmlFb0");
        manager.getGuildMusicManager(ctx.getGuild()).player.setVolume(100);
    }


    @Override
    public String getName() {
        return "Ronaldinho";
    }

    @Override
    public String getHelp() {
        return "Ha, Ha, Ha!";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("rs64", "soccer", "ra", "ha");
    }
}
