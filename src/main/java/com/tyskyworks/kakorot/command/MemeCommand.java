package com.tyskyworks.kakorot.command;

import com.fasterxml.jackson.databind.JsonNode;
import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

public class MemeCommand implements  ICommand{

    @Override
    public void handle(CommandContext ctx) {
       final TextChannel channel = ctx.getChannel();
        WebUtils.ins.getJSONObject("https://apis.duncte123.me/meme").async((json) -> {
            if (!json.get("success").asBoolean()) {
                channel.sendMessage("awwwww shit something messed up").queue();
                System.out.println(json);
            }
            final JsonNode data = json.get("data");
            final String title = data.get("title").asText();
            final String image = data.get("image").asText();
            final String url = data.get("url").asText();

            final EmbedBuilder embed = EmbedUtils.embedImageWithTitle(title,url,image);

            channel.sendMessage(embed.build()).queue();
        });

    }

    @Override
    public String getName() {
        return "meme";
    }

    @Override
    public String getHelp() {
        return "Posts a meme from /r Dankmemes";
    }
}
