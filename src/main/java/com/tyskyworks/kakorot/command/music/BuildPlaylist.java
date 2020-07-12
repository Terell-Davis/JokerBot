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
// Test Code from filter listener


        String[] LIST_OF_BAD_WORDS = {"anal", "anus", "arse", "ass", "motherfucker", "balls", "bastard", "bitch", "blowjob", "blow job", "buttplug","cock","coon","cunt","dildo","fag","dyke","fuck","fucking","nigger","Goddamn","jizz","nigga","pussy","shit","whore"};

        String[] message = ctx.getMessage().getContentRaw().split(" ");

        for(int i = 0;i < message.length;i++){

            boolean badWord = false;

            //Check each message for each bad word

            for(int b = 0; b < LIST_OF_BAD_WORDS.length;b++){

                if (message[i].equalsIgnoreCase(LIST_OF_BAD_WORDS[b])){

                    ctx.getMessage().delete().queue();

                    badWord = true;

                    if(message != message){ //Prints a message saying watch your language IF enabled by $filtermessage

                        ctx.getChannel().sendMessage("Watch yo vernacular " + ctx.getMember().getUser().getName()).queue();

                    }

                }

            }}
        List<Message> messages = ctx.getChannel().getHistory().retrievePast(50).complete();

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
