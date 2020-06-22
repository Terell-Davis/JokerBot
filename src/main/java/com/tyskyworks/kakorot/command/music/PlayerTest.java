package com.tyskyworks.kakorot.command.music;

import com.tyskyworks.kakorot.command.CommandContext;
import com.tyskyworks.kakorot.command.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class PlayerTest implements  ICommand {

    @Override
    public void handle(CommandContext ctx) {
        PlayerManager manager = PlayerManager.getINSTANCE();

        manager.loadAndPlay(ctx.getChannel(), "https://youtu.be/b6QHGfphoXU");

        manager.getGuildMusicManager(ctx.getGuild()).player.setVolume(10);

    }

    @Override
    public String getHelp() {
        return "Plays the music bot";
    }



    @Override
    public String getName() {
        return "play";
    }
}

