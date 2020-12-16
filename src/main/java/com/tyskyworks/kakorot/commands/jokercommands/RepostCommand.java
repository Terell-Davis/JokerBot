package com.tyskyworks.kakorot.commands.jokercommands;

import com.tyskyworks.kakorot.Config;
import com.tyskyworks.kakorot.commands.CommandContext;
import com.tyskyworks.kakorot.commands.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.io.File;
import java.util.List;

public class RepostCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        TextChannel channel = ctx.getChannel();
        final List<String> args = ctx.getArgs();

        if (args.isEmpty()) {
            EmbedBuilder repost = new EmbedBuilder();
            repost.setColor(0xf51707);
            repost.setTitle("Specify who to hate");
            repost.setDescription("Usage: `" + Config.get("prefix") + "fk <who your homie hate>`");
            channel.sendMessage(repost.build()).queue();
        }

        String path = Config.get("ASSETS") + "/";
        channel.sendFile(new File(path + "repost.png")).queue();
    }

    @Override
    public String getName() {
        return "repost";
    }

    @Override
    public String getHelp() {
        return  "Usage:" + Config.get("PREFIX") + "repost (Use when we've seen this shit already)";
    }
}
