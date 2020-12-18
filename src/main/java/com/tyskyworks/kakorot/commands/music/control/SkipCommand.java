package com.tyskyworks.kakorot.commands.music.control;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.tyskyworks.kakorot.commands.CommandContext;
import com.tyskyworks.kakorot.commands.ICommand;
import com.tyskyworks.kakorot.commands.music.NowPlayingCommand;
import com.tyskyworks.kakorot.commands.music.musicassets.GuildMusicManager;
import com.tyskyworks.kakorot.commands.music.musicassets.PlayerManager;
import com.tyskyworks.kakorot.commands.music.musicassets.TrackScheduler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
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
            GuildVoiceState memberVoiceState = ctx.getMember().getVoiceState();

            if(memberVoiceState.inVoiceChannel()) {
                if (player.getPlayingTrack() == null) {
                    EmbedBuilder empty = new EmbedBuilder();
                    empty.setColor(0xf51707);
                    empty.setDescription("The Player isn't playing anything");
                    channel.sendMessage(empty.build()).queue();
                    return;
                }

                scheduler.nextTrack();
                EmbedBuilder skip = new EmbedBuilder();
                skip.setColor(0xf51707);
                skip.setDescription("Skipping current track");
                channel.sendMessage(skip.build()).queue();
                NowPlayingCommand playing = new NowPlayingCommand();
                playing.handle(ctx);
            }else{
                EmbedBuilder other = new EmbedBuilder();
                other.setColor(0xf51707);
                other.setDescription("Please join a voice channel to use this command!");
                channel.sendMessage(other.build()).queue();
            }
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
