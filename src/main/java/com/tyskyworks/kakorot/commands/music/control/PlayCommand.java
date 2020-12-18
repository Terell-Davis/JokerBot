package com.tyskyworks.kakorot.commands.music.control;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import com.tyskyworks.kakorot.Config;
import com.tyskyworks.kakorot.commands.CommandContext;
import com.tyskyworks.kakorot.commands.ICommand;
import com.tyskyworks.kakorot.commands.music.logging.LoggingCommand;
import com.tyskyworks.kakorot.commands.music.musicassets.GuildMusicManager;
import com.tyskyworks.kakorot.commands.music.musicassets.JoinCommand;
import com.tyskyworks.kakorot.commands.music.musicassets.PlayerManager;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import javax.annotation.Nullable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PlayCommand implements ICommand {
    private final YouTube youTube;

    public PlayCommand() {
        YouTube temp = null;
        try {
            temp = new YouTube.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JacksonFactory.getDefaultInstance(),
                    null
            )
                    .setApplicationName("Jokerbot - Java Discord bot")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        youTube = temp;
    }

    @Override
    public void handle(CommandContext ctx) {
        AudioManager audioManager = ctx.getGuild().getAudioManager();
        final TextChannel channel = ctx.getChannel();
        PlayerManager manager = PlayerManager.getInstance();
        GuildMusicManager musicManager = manager.getGuildMusicManager(ctx.getGuild());
        AudioPlayer player = musicManager.player;

        if(manager.getGuildMusicManager(ctx.getGuild()).player.isPaused()){
            manager.getGuildMusicManager(ctx.getGuild()).player.setPaused(false);
            AudioTrackInfo info = player.getPlayingTrack().getInfo();
            channel.sendMessage(EmbedUtils.embedMessage(String.format(
                    "**__Now Playing:__** [%s](%s)\n%s %s - %s %s",
                    info.title,
                    info.uri,
                    player.isPaused() ? "\u23F8" : "🥞 ",
                    formatTime(player.getPlayingTrack().getPosition()),
                    formatTime(player.getPlayingTrack().getDuration()), " 🥞"
            )).setColor(0xf51707).build()).queue();
            return;
        }

        if ((ctx.getArgs().isEmpty())) {
            EmbedBuilder pause = new EmbedBuilder();
            pause.setColor(0xf51707);
            pause.setTitle("Please enter what you want to play.");
            pause.setDescription("Usage: `" + Config.get("PREFIX") + "play [url/song name]`");
            channel.sendMessage(pause.build()).queue();
            return;
        }

        String input = String.join(" ", ctx.getArgs());

        if (!isUrl(input)) {
            String ytSearched = searchYoutube(input);

            if (ytSearched == null) {
                EmbedBuilder yt = new EmbedBuilder();
                yt.setColor(0xf51707);
                yt.setDescription("Youtube returned no results.");
                channel.sendMessage(yt.build()).queue();
                return;
            }
            input = ytSearched;
        }

        if (!audioManager.isConnected()) {
            JoinCommand join = new JoinCommand();
            join.handle(ctx);
        }

        boolean toggle = LoggingCommand.toggle;
        GuildVoiceState memberVoiceState = ctx.getMember().getVoiceState();
        String path = Config.get("LOG");
        if(memberVoiceState.inVoiceChannel()) {
            manager.loadAndPlay(ctx.getChannel(), input);
            if (toggle){
                try{
                FileOutputStream log = new FileOutputStream(path, true);
                String write = input + "\n";
                byte[] strToBytes = write.getBytes();
                log.write(strToBytes);
                log.close();
            }catch (IOException e){
                    System.out.print("An error has occurred\n" + e);
                }
            }
            manager.getGuildMusicManager(ctx.getGuild()).player.setVolume(80);
        }else{
            EmbedBuilder other = new EmbedBuilder();
            other.setColor(0xf51707);
            other.setDescription("Please join a voice channel to use this command!");
            channel.sendMessage(other.build()).queue();
        }
    }

    private boolean isUrl(String input) {
        try {
            new URL(input);

            return true;
        } catch (MalformedURLException ignored) {
            return false;
        }
    }

    @Nullable
    private String searchYoutube(String input) {
        try {
            List<SearchResult> results = youTube.search()
                    .list("id,snippet")
                    .setQ(input)
                    .setMaxResults(1L)
                    .setType("video")
                    .setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)")
                    .setKey(Config.get("YOUTUBE"))
                    .execute()
                    .getItems();

            if (!results.isEmpty()) {
                String videoId = results.get(0).getId().getVideoId();

                return "https://www.youtube.com/watch?v=" + videoId;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getHelp() {
        return "Plays a song\n" +
                "Usage: `" + Config.get("prefix") + getName() + " <song url>`";
    }

    private String formatTime(long timeInMillis) {
        final long hours = timeInMillis / TimeUnit.HOURS.toMillis(1);
        final long minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1);
        final long seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    @Override
    public String getName() {
        return "play";
    }

    public List<String> getAliases() {
        return Arrays.asList("p", "pl");
    }
}
