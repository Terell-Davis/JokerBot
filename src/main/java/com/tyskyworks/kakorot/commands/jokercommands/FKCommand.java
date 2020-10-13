package com.tyskyworks.kakorot.commands.jokercommands;

import com.tyskyworks.kakorot.Config;
import com.tyskyworks.kakorot.commands.CommandContext;
import com.tyskyworks.kakorot.commands.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

public class FKCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final List<String> args = ctx.getArgs();

        String[] place = new String[]{"**__Fuck {name} all my homies hate {name}__**"};

            if (args.isEmpty()) {
                EmbedBuilder fk = new EmbedBuilder();
                fk.setColor(0xf51707);
                fk.setTitle("Specify who to hate");
                fk.setDescription("Usage: `" + Config.get("prefix") + "fk <who your homie hate>`");
                channel.sendMessage(fk.build()).queue();
            } else{
                try {
                   EmbedBuilder fk = new EmbedBuilder();
                   fk.setColor(0xf51707);String.join(" ", args);
                   fk.setDescription(place[0].replace("{name}",String.join(" ", args)));
                   channel.sendMessage(fk.build()).queue();
                } catch (IllegalArgumentException e) {
                    if (e.toString().startsWith("java.lang.IllegalArgumentException: Message retrieval")) {
                        EmbedBuilder error = new EmbedBuilder();
                        error.setColor(0xff3923);
                        error.setTitle("⛔ The hell did you put to get this?");
                        error.setDescription("Really what did you do??????");
                        ctx.getChannel().sendMessage(error.build()).queue();
                    }
                }
            }
    }


    @Override
    public String getName() {
        return "fk";
    }

    @Override
    public String getHelp() {
        return "Who do all you and your homies hate? `" + Config.get("prefix")  + "fk <who your homie hate>`";
    }
}
