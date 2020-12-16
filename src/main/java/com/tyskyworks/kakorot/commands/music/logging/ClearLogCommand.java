package com.tyskyworks.kakorot.commands.music.logging;

import com.tyskyworks.kakorot.commands.CommandContext;
import com.tyskyworks.kakorot.commands.ICommand;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class ClearLogCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        try {
            PrintWriter writer = new PrintWriter("src/main/java/com/tyskyworks/kakorot/musiclog.txt");
            writer.print("");
            writer.close();
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
