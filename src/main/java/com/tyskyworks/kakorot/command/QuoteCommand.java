package com.tyskyworks.kakorot.command;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import com.tyskyworks.kakorot.Config;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.Arrays;
import java.util.List;

public class QuoteCommand implements ICommand {
    private final WebhookClient client;

    public QuoteCommand() {
        WebhookClientBuilder builder = new WebhookClientBuilder(Config.get("webhook_url")); // or id, token
        builder.setThreadFactory((job) -> {
            Thread thread = new Thread(job);
            thread.setName("Quotes");
            thread.setDaemon(true);
            return thread;
        });
        this.client = builder.build();
    }

    public void handle(CommandContext ctx) {
        final List<String> args = ctx.getArgs();
        final TextChannel channel = ctx.getChannel();
        final Message message = ctx.getMessage();
        final Member member = ctx.getMember();

        final Member target = message.getMentionedMembers().get(0);

        if (args.size() < 2 || message.getMentionedMembers().isEmpty()) {
            channel.sendMessage("Missing arguments; `j-quote [member] <message>`").queue();
            return;
        }
        //if (!member.canInteract(target) || !member.hasPermission(Permission.MESSAGE_WRITE)) {
        //    channel.sendMessage("You do not have permission for this command!").queue();
         //   return;
       // }


        final User user = ctx.getAuthor();
        final String quote = String.join(" ", args.subList(1, args.size()));

        WebhookMessageBuilder builder = new WebhookMessageBuilder()
                .setUsername(target.getEffectiveName())
                .setAvatarUrl(target.getUser().getEffectiveAvatarUrl().replaceFirst("gif", "png") + "?size=512")
                .setContent(quote);

        client.send(builder.build());

    }

    @Override
    public String getName() {
        return "quote";
    }

    @Override
    public String getHelp() {
        return "Usage: `j-quote [member] <message>`  [Warning this command works across servers and will only post to Ghost & Goblins Quote Channel]";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("qt", "qot");
    }
}
