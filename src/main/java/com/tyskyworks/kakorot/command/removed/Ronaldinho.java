package com.tyskyworks.kakorot.command.removed;

import com.tyskyworks.kakorot.command.CommandContext;
import com.tyskyworks.kakorot.command.ICommand;
import com.tyskyworks.kakorot.command.music.musicassets.JoinCommand;
import com.tyskyworks.kakorot.command.music.musicassets.PlayerManager;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.Arrays;
import java.util.List;

public class Ronaldinho implements ICommand {
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
