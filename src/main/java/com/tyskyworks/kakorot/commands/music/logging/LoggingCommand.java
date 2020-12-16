package com.tyskyworks.kakorot.commands.music.logging;

import com.tyskyworks.kakorot.Config;
import com.tyskyworks.kakorot.commands.CommandContext;
import com.tyskyworks.kakorot.commands.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class LoggingCommand implements ICommand {
    public static boolean toggle = false;
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        try {
            File musiclog = new File("src/main/java/com/tyskyworks/kakorot/musiclog.txt");

            if (!musiclog.exists()) {
                musiclog.createNewFile();
            }

            if ((ctx.getArgs().isEmpty())) {
                EmbedBuilder usage = new EmbedBuilder();
                usage.setColor(0xf51707);
                usage.setTitle("Specify True/False");
                usage.setDescription("Usage: `" + Config.get("prefix") + "logging [True/False]`");
                ctx.getChannel().sendMessage(usage.build()).queue();
                return;
            }

            String Condition = String.join(" ", ctx.getArgs());

            if(Condition.equals("True") || Condition.equals("true")){
                toggle = true;
                EmbedBuilder condition = new EmbedBuilder();
                condition.setColor(0xf51707);
                condition.setTitle("Music logging is now __**Enabled**__");
                ctx.getChannel().sendMessage(condition.build()).queue();
            }else if (Condition.equals("False") || Condition.equals("false")){
                toggle = false;
                EmbedBuilder condition = new EmbedBuilder();
                condition.setColor(0xf51707);
                condition.setTitle("Music logging is now __**Disabled**__");
                ctx.getChannel().sendMessage(condition.build()).queue();
            } else {
                EmbedBuilder condition = new EmbedBuilder();
                condition.setColor(0xf51707);
                condition.setTitle("__" + Condition + " is not a valid condition__");
                ctx.getChannel().sendMessage(condition.build()).queue();
            }
        } catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return "logging";
    }

    @Override
    public String getHelp() {
        return "Usage: `" + Config.get("PREFIX") + getName() + " [True/False]`";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("log", "loggin");
    }
}
