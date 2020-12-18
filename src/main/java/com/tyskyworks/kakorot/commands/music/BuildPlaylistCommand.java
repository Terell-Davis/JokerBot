package com.tyskyworks.kakorot.commands.music;

import com.tyskyworks.kakorot.commands.CommandContext;
import com.tyskyworks.kakorot.commands.ICommand;
import com.tyskyworks.kakorot.commands.music.musicassets.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class BuildPlaylistCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        File musiclog = new File("src/main/java/com/tyskyworks/kakorot/musiclog.txt");
        GuildVoiceState memberVoiceState = ctx.getMember().getVoiceState();
        AudioManager audioManager = ctx.getGuild().getAudioManager();
        final TextChannel channel = ctx.getChannel();
        PlayerManager manager = PlayerManager.getInstance();


        EmbedBuilder list = new EmbedBuilder();
        list.setColor(0xf51707);
        list.setTitle("🥞 __Building playlist from log__ 🥞");

        if(memberVoiceState.inVoiceChannel()) {
            try {
                BufferedReader log = new BufferedReader(new FileReader(musiclog));
                String read; int count = 25;
                while ((read = log.readLine()) != null && 0 < count) {
                    manager.loadAndPlay(ctx.getChannel(), read);
                    count--;
                }
                manager.getGuildMusicManager(ctx.getGuild()).player.setVolume(80);
                channel.sendMessage(list.build()).queue();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            EmbedBuilder other = new EmbedBuilder();
            other.setColor(0xf51707);
            other.setDescription("Please join a voice channel to use this command!");
            channel.sendMessage(other.build()).queue();
        }
    }
            @Override
    public String getName() {
        return "buildplaylist";
    }

    @Override
    public String getHelp() {
        return "Builds a playlist from the log";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("build", "bd");
    }
}
