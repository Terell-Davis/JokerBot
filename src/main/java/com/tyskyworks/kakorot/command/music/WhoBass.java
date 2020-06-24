package com.tyskyworks.kakorot.command.music;

import com.tyskyworks.kakorot.command.CommandContext;
import com.tyskyworks.kakorot.command.ICommand;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.Arrays;
import java.util.List;

public class WhoBass implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        PlayerManager manager = PlayerManager.getInstance();
        AudioManager audioManager = ctx.getGuild().getAudioManager();
        final TextChannel channel = ctx.getChannel();

        if (!audioManager.isConnected()) {
            JoinCommand join = new JoinCommand();
            join.handle(ctx);
        }

        manager.loadAndPlay(ctx.getChannel(), "https://www.youtube.com/watch?v=_pS5soRsrKQ");
        manager.getGuildMusicManager(ctx.getGuild()).player.setVolume(900000);
        channel.sendMessage("MYSTERY INTENSIFIES!!!!!!!!");

    }

    @Override
    public String getName() {
        return "WhoBass";
    }

    @Override
    public String getHelp() {
        return "Plays Who is there? (persona 4) but bass boosted! 💥";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("wb", "who", "mystery");
    }
}
