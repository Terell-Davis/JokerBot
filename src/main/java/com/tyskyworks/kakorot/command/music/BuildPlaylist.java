package com.tyskyworks.kakorot.command.music;

import com.tyskyworks.kakorot.command.CommandContext;
import com.tyskyworks.kakorot.command.ICommand;
import com.tyskyworks.kakorot.command.music.musicassets.PlayerManager;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

//unfinished

public class BuildPlaylist implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        PlayerManager manager = PlayerManager.getInstance();
        AudioManager audioManager = ctx.getGuild().getAudioManager();

        String[] args = ctx.getMessage().getContentRaw().split("\\s+");

        List<Message> messages = ctx.getChannel().getHistory().retrievePast(100).complete();

        StringBuilder sb = new StringBuilder();

        // Appends characters one by one
        for (Message ch : messages) {
            sb.append(ch);
        }

        // convert in string
        String string = sb.toString();

        if(isUrl(string)) {
            manager.loadAndPlay(ctx.getChannel(), string);
            manager.getGuildMusicManager(ctx.getGuild()).player.setVolume(75);
            //turn into embed later
            channel.sendMessage("Song(s)" + " Added");
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
    @Override
    public String getName() {
        return "buildplaylist";
    }

    @Override
    public String getHelp() {
        return null;
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("build");
    }
}
