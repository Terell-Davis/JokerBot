package com.tyskyworks.kakorot.commands.music.logging;

import com.tyskyworks.kakorot.Config;
import com.tyskyworks.kakorot.commands.CommandContext;
import com.tyskyworks.kakorot.commands.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class ClearLogCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        String path = Config.get("LOG");
        try {
            PrintWriter writer = new PrintWriter(path);
            writer.print("");
            writer.close();
            EmbedBuilder clear = new EmbedBuilder();
            clear.setColor(0xf51707);
            clear.setTitle("🥞 Clearing Music Log 🥞");
            ctx.getChannel().sendMessage(clear.build()).queue();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return "clearlog";
    }

    @Override
    public String getHelp() {
        return "Clears the entire log";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("logclear", "cl");
    }
}
