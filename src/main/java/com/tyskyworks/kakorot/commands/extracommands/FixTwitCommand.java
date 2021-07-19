package com.tyskyworks.kakorot.commands.extracommands;

import com.tyskyworks.kakorot.commands.CommandContext;
import com.tyskyworks.kakorot.commands.ICommand;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.Arrays;
import java.util.List;

public class FixTwitCommand implements ICommand{
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        Message Twitlink = ctx.getMessage().getReferencedMessage();
        String link = Twitlink.getContentRaw();

        String fixed = link.replace("twitter.com", "fxtwitter.com");
        channel.sendMessage(fixed).queue();

    }

    @Override
    public String getName() {
        return "fixtwit";
    }

    @Override
    public String getHelp() {
        return "Fixes Twitter Embeds";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("fx", "fxtwit", "whyyoudothistwitter");
    }
}
