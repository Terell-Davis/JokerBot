package com.tyskyworks.kakorot.command.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import com.tyskyworks.kakorot.command.CommandContext;
import com.tyskyworks.kakorot.command.ICommand;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class NowPlayingCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        TextChannel channel = ctx.getChannel();
        PlayerManager playerManager = PlayerManager.getINSTANCE();
        GuildMusicManager musicManager = playerManager.getGuildMusicManager(ctx.getGuild());
        AudioPlayer player = musicManager.player;

        if (player.getPlayingTrack() == null) {
            channel.sendMessage("The player is not playing any song.").queue();

            return;
        }

        AudioTrackInfo info = player.getPlayingTrack().getInfo();

        channel.sendMessage(EmbedUtils.embedMessage(String.format(
                "**Playing** [%s](%s)\n%s %s - %s",
                info.title,
                info.uri,
                player.isPaused() ? "\u23F8" : "▶",
                formatTime(player.getPlayingTrack().getPosition()),
                formatTime(player.getPlayingTrack().getDuration())
        )).build()).queue();
    }

    @Override
    public String getHelp() {
        return "Shows the currently playing track";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("Nowp", "nowplaying", "playing");
    }

    @Override
    public String getName() {
        return "np";
    }

    private String formatTime(long timeInMillis) {
        final long hours = timeInMillis / TimeUnit.HOURS.toMillis(1);
        final long minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1);
        final long seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}

