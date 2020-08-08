package com.tyskyworks.kakorot.command.jokecommands;

import com.tyskyworks.kakorot.command.CommandContext;
import com.tyskyworks.kakorot.command.ICommand;
import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

public class InspireCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        WebUtils.ins.getText("https://inspirobot.me/api?generate=true&oy=vey").async((text) -> {
            final EmbedBuilder embed;
            if (text != null) {
                embed = EmbedUtils.embedImage(text).setColor(0xf51707);
            }else {
                embed = EmbedUtils.embedMessage("⛔ Something went wrong! Please try again.").setColor(0xf51707);
            }
            channel.sendMessage(embed.build()).queue();


            // final EmbedBuilder embed = EmbedUtils.embedImageWithTitle(title,url,image);
        });

    }

    @Override
    public String getName() {
        return "inspire";
    }

    @Override
    public String getHelp() {
        return "In case you need to be inspired!";
    }
}
