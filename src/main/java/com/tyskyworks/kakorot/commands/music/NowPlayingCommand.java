package com.tyskyworks.kakorot.commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import com.tyskyworks.kakorot.commands.CommandContext;
import com.tyskyworks.kakorot.commands.ICommand;
import com.tyskyworks.kakorot.commands.music.musicassets.GuildMusicManager;
import com.tyskyworks.kakorot.commands.music.musicassets.PlayerManager;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class NowPlayingCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        TextChannel channel = ctx.getChannel();
        PlayerManager playerManager = PlayerManager.getInstance();
        GuildMusicManager musicManager = playerManager.getGuildMusicManager(ctx.getGuild());
        GuildVoiceState memberVoiceState = ctx.getMember().getVoiceState();
        AudioPlayer player = musicManager.player;

        if (player.getPlayingTrack() == null) {
            EmbedBuilder empty = new EmbedBuilder();
            empty.setColor(0xf51707);
            empty.setDescription("The player isn't playing anything.");
            channel.sendMessage(empty.build()).queue();
            return;
        }

        if (memberVoiceState.inVoiceChannel()) {
            AudioTrackInfo info = player.getPlayingTrack().getInfo();

            channel.sendMessage(EmbedUtils.embedMessage(String.format(
                    "**__Now Playing:__** [%s](%s)\n%s %s - %s %s",
                    info.title,
                    info.uri,
                    player.isPaused() ? "\u23F8" : "🥞 ",
                    formatTime(player.getPlayingTrack().getPosition()),
                    formatTime(player.getPlayingTrack().getDuration()), " 🥞"
            )).setColor(0xf51707).build()).queue();
        }else{
            EmbedBuilder other = new EmbedBuilder();
            other.setColor(0xf51707);
            other.setDescription("Please join a voice channel to use this command!");
            channel.sendMessage(other.build()).queue();
        }
    }

    @Override
    public String getHelp() {
        return "Shows the currently playing track";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("Nowp", "nowplaying", "playing","np");
    }

    @Override
    public String getName() {
        return "NowPlaying";
    }

    private String formatTime(long timeInMillis) {
        final long hours = timeInMillis / TimeUnit.HOURS.toMillis(1);
        final long minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1);
        final long seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}

