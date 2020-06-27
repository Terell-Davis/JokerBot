package com.tyskyworks.kakorot.command.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.tyskyworks.kakorot.command.CommandContext;
import com.tyskyworks.kakorot.command.ICommand;
import com.tyskyworks.kakorot.command.music.musicassets.GuildMusicManager;
import com.tyskyworks.kakorot.command.music.musicassets.PlayerManager;
import com.tyskyworks.kakorot.command.music.musicassets.TrackScheduler;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.Arrays;
import java.util.List;

public class SkipCommand implements ICommand {
        @Override
        public void handle(CommandContext ctx) {
            TextChannel channel = ctx.getChannel();
            PlayerManager playerManager = PlayerManager.getInstance();
            GuildMusicManager musicManager = playerManager.getGuildMusicManager(ctx.getGuild());
            TrackScheduler scheduler = musicManager.scheduler;
            AudioPlayer player = musicManager.player;

            if (player.getPlayingTrack() == null) {
                channel.sendMessage("The player isn't playing anything").queue();
                return;
            }
            scheduler.nextTrack();
            channel.sendMessage("Skipping current track").queue();
            NowPlayingCommand playing = new NowPlayingCommand();
            playing.handle(ctx);


        }


    @Override
    public String getName() {
        return "skip";
    }

    @Override
    public String getHelp() {
        return "Skips to next song in queue";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("s", "sk");
    }

}
