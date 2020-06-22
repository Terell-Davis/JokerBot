package com.tyskyworks.kakorot.command.music;

import com.tyskyworks.kakorot.command.CommandContext;
import com.tyskyworks.kakorot.command.ICommand;

public class StopCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        PlayerManager playerManager = PlayerManager.getINSTANCE();
        GuildMusicManager musicManager = playerManager.getGuildMusicManager(ctx.getGuild());

        LeaveCommand leave = new LeaveCommand();
        leave.handle(ctx);
        musicManager.scheduler.getQueue().clear();
        musicManager.player.stopTrack();
        musicManager.player.setPaused(false);

        ctx.getChannel().sendMessage("Stopping the player and clearing the queue").queue();
    }

    @Override
    public String getName() {
        return "stop";
    }

    @Override
    public String getHelp() {
        return "Stop the Bot from playing music";
    }
}
