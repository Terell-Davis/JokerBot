package com.tyskyworks.kakorot.command;

import com.tyskyworks.kakorot.Config;
import com.tyskyworks.kakorot.JokerBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;
import java.util.List;

public class PurgeCommand implements ICommand{

    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();

        if (ctx.getAuthor().isBot()) return;

        String[] args = ctx.getMessage().getContentRaw().split("\\s+");
        if (args[0].equalsIgnoreCase(Config.get("prefix") + "clear")) {
            if (args.length < 2) {
                // Usage
                EmbedBuilder usage = new EmbedBuilder();
                usage.setColor(0xff3923);
                usage.setTitle("Specify amount to delete");
                usage.setDescription("Usage: `" + Config.get("prefix") + "clear [# of messages]`");
                ctx.getChannel().sendMessage(usage.build()).queue();
            }
            else {
                try {
                    List<Message> messages = ctx.getChannel().getHistory().retrievePast(Integer.parseInt(args[1])).complete();
                    ctx.getChannel().deleteMessages(messages).queue();

                    // Success
                    EmbedBuilder success = new EmbedBuilder();
                    success.setColor(0x22ff2a);
                    success.setTitle("⚠ " + args[1] + " Messages Deleted.");
                    ctx.getChannel().sendMessage(success.build()).queue();

                } catch (IllegalArgumentException e) {
                    if (e.toString().startsWith("java.lang.IllegalArgumentException: Message retrieval")){
                        // Too many messages
                        EmbedBuilder error = new EmbedBuilder();
                        error.setColor(0xff3923);
                        error.setTitle("⛔ Too many messages selected");
                        error.setDescription("Between 1-100 messages can be deleted at one time.");
                        ctx.getChannel().sendMessage(error.build()).queue();
                    }
                    else {
                        // Messages too old
                        EmbedBuilder error = new EmbedBuilder();
                        error.setColor(0xff3923);
                        error.setTitle("⛔ Selected messages are older than 2 weeks");
                        error.setDescription("Messages older than 2 weeks cannot be deleted.");
                        ctx.getChannel().sendMessage(error.build()).queue();
                    }
                }
            }
        }
    }

    @Override
    public String getName() {
        return "purge";
    }

    @Override
    public String getHelp() {
        return "Usage: `" + Config.get("prefix") + "clear [# of messages]`";
    }

    public List<String> getAliases() {
        return Arrays.asList("clear", "clr");
    }
}
