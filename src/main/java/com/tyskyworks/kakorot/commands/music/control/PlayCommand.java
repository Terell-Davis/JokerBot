package com.tyskyworks.kakorot.commands.music.control;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import com.tyskyworks.kakorot.Config;
import com.tyskyworks.kakorot.commands.CommandContext;
import com.tyskyworks.kakorot.commands.ICommand;
import com.tyskyworks.kakorot.commands.music.logging.LoggingCommand;
import com.tyskyworks.kakorot.commands.music.musicassets.JoinCommand;
import com.tyskyworks.kakorot.commands.music.musicassets.PlayerManager;
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

        if(manager.getGuildMusicManager(ctx.getGuild()).player.isPaused()){
            manager.getGuildMusicManager(ctx.getGuild()).player.setPaused(false);
            return;
        }

        if ((ctx.getArgs().isEmpty())) {
            channel.sendMessage("Please enter what you want to play").queue();
            return;
        }

        String input = String.join(" ", ctx.getArgs());

        if (!isUrl(input)) {
            String ytSearched = searchYoutube(input);

            if (ytSearched == null) {
                channel.sendMessage("Youtube returned no results").queue();
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
        if(memberVoiceState.inVoiceChannel()) {
            manager.loadAndPlay(ctx.getChannel(), input);
            if (toggle){
                try{
                FileOutputStream log = new FileOutputStream("src/main/java/com/tyskyworks/kakorot/musiclog.txt", true);
                String write = input + "\n";
                byte[] strToBytes = write.getBytes();
                log.write(strToBytes);
                log.close();
            }catch (IOException e){
                    System.out.print("An error has occurred\n" + e);
                }
            }
            manager.getGuildMusicManager(ctx.getGuild()).player.setVolume(80);
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

    @Override
    public String getName() {
        return "play";
    }

    public List<String> getAliases() {
        return Arrays.asList("p", "pl");
    }
}
