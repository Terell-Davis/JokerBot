package com.tyskyworks.kakorot.command.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.TrackEndEvent;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import com.tyskyworks.kakorot.command.CommandContext;
import com.tyskyworks.kakorot.command.ICommand;
import com.tyskyworks.kakorot.command.music.musicassets.GuildMusicManager;
import com.tyskyworks.kakorot.command.music.musicassets.PlayerManager;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class ShuffleCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        TextChannel channel = ctx.getChannel();
        PlayerManager playerManager = PlayerManager.getInstance();
        GuildMusicManager musicManager = playerManager.getGuildMusicManager(ctx.getGuild());
        BlockingQueue<AudioTrack> queue = musicManager.scheduler.getQueue();

        if (ctx.getAuthor().isBot()) return;

        GuildVoiceState memberVoiceState = ctx.getMember().getVoiceState();
        if (!memberVoiceState.inVoiceChannel()){
            channel.sendMessage("Please join a voice channel to use this command.").queue();
            return;
        }
        List<AudioTrack> list = new ArrayList<>();
        musicManager.scheduler.getQueue().drainTo(list);

        Collections.shuffle(list);
        musicManager.scheduler.getQueue().addAll(list);

        List<AudioTrack> tracks = new ArrayList<>(queue);
        AudioPlayer player = musicManager.player;
        AudioTrack track = tracks.get(0);
        AudioTrackInfo info = track.getInfo();


        channel.sendMessage(EmbedUtils.embedMessage(String.format(
                "**__Next Up:__** [%s](%s)\n%s %s",
                info.title,
                info.uri,
                player.isPaused() ? "\u23F8" : "🥞 ",
                formatTime(track.getDuration()), " 🥞"
        )).setColor(0xf51707).build()).queue();
    }

    @Override
    public String getName() {
        return "shuffle";
    }

    @Override
    public String getHelp() {
        return "Shuffles the entire queue";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("sh", "shuf");
    }

    private String formatTime(long timeInMillis){
        final long hours = timeInMillis / TimeUnit.HOURS.toMillis(1);
        final long minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1);
        final long seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
