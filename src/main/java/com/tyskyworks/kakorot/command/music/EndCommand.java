package com.tyskyworks.kakorot.command.music;

import com.tyskyworks.kakorot.command.CommandContext;
import com.tyskyworks.kakorot.command.ICommand;
import com.tyskyworks.kakorot.command.music.musicassets.GuildMusicManager;
import com.tyskyworks.kakorot.command.music.musicassets.LeaveCommand;
import com.tyskyworks.kakorot.command.music.musicassets.PlayerManager;
import me.duncte123.botcommons.messaging.EmbedUtils;

public class EndCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        PlayerManager playerManager = PlayerManager.getInstance();
        GuildMusicManager musicManager = playerManager.getGuildMusicManager(ctx.getGuild());

        LeaveCommand leave = new LeaveCommand();
        leave.handle(ctx);
        musicManager.scheduler.getQueue().clear();
        musicManager.player.stopTrack();
        musicManager.player.setPaused(false);
        ctx.getChannel().sendMessage(EmbedUtils.embedMessage(String.format(
                "🛑 Stopping song and Clearing the queue"
        )).setColor(0xf51707).build()).queue();
    }

    @Override
    public String getName() {
        return "end";
    }

    @Override
    public String getHelp() {
        return "Bot will clear queue and leave the channel";
    }
}
