package com.tyskyworks.kakorot.command.music;

import com.tyskyworks.kakorot.command.CommandContext;
import com.tyskyworks.kakorot.command.ICommand;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

//unfinished

public class BuildPlaylist implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();

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
        return null;
    }

    @Override
    public String getHelp() {
        return null;
    }

    @Override
    public List<String> getAliases() {
        return null;
    }
}
