package com.tyskyworks.kakorot.command.jokecommands;

import com.fasterxml.jackson.databind.JsonNode;
import com.tyskyworks.kakorot.command.CommandContext;
import com.tyskyworks.kakorot.command.ICommand;
import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

public class CockandBall implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        WebUtils.ins.getJSONObject("https://en.wikipedia.org/api/rest_v1/page/summary/Cock_and_ball_torture").async((json) -> {

            final JsonNode data = json.get("titles");
            final String name = data.get("display").asText();
            final String text = json.get("extract").asText();


            final EmbedBuilder embed = EmbedUtils.embedMessageWithTitle(name, text + " 🍆").setColor(0xbf0208);

            channel.sendMessage(embed.build()).queue();
        });

    }

    @Override
    public String getName() {
        return "cbt";
    }

    @Override
    public String getHelp() {
        return "Run it and see what happens";
    }
}