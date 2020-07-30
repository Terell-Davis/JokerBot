package com.tyskyworks.kakorot.command.jokecommands;

import com.tyskyworks.kakorot.command.CommandContext;
import com.tyskyworks.kakorot.command.ICommand;
import com.tyskyworks.kakorot.command.music.musicassets.JoinCommand;
import com.tyskyworks.kakorot.command.music.musicassets.PlayerManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.Arrays;
import java.util.List;

public class WhoIsThereBass implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        PlayerManager manager = PlayerManager.getInstance();
        AudioManager audioManager = ctx.getGuild().getAudioManager();
        final TextChannel channel = ctx.getChannel();
        final Member member = ctx.getMember();

        //if (!member.hasPermission(Permission.ADMINISTRATOR)) {
        //    channel.sendMessage("You are missing permission to hurt others").queue();
         //   return;
       // }

        if (!audioManager.isConnected()) {
            JoinCommand join = new JoinCommand();
            join.handle(ctx);
        }

        manager.loadAndPlay(ctx.getChannel(), "https://www.youtube.com/watch?v=_pS5soRsrKQ");
        manager.getGuildMusicManager(ctx.getGuild()).player.setVolume(2147483647);
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
        return Arrays.asList("wb", "who", "mystery","whobass");
    }
}
