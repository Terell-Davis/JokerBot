package com.tyskyworks.kakorot.commands.scrapedcommands;

import com.tyskyworks.kakorot.commands.CommandContext;
import com.tyskyworks.kakorot.commands.ICommand;
import com.tyskyworks.kakorot.commands.music.musicassets.PlayerManager;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

//unfinished

public class BuildPlaylistCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        PlayerManager manager = PlayerManager.getInstance();
        AudioManager audioManager = ctx.getGuild().getAudioManager();

       //final List<String> args = ctx.getArgs();
        List<Message> messages = channel.getHistory().retrievePast(100).complete();
        System.out.println(messages);




        /*for(int i = 0; i < 100; i++){
            String input = messages.get(i);
            if (!isUrl(input))
        }*/
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
        return "Takes chat history looks for youtube urls and adds them to queue";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("build");
    }
}
