package com.tyskyworks.kakorot.commands.jokercommands;

import com.tyskyworks.kakorot.commands.CommandContext;
import com.tyskyworks.kakorot.commands.ICommand;
import net.dv8tion.jda.api.entities.TextChannel;

import java.io.File;

public class GunCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        TextChannel channel = ctx.getChannel();
        channel.sendFile(new File("src/main/java/com/tyskyworks/kakorot/commands/jokercommands/assets/gun.png")).queue();
    }

    @Override
    public String getName() {
        return "gun";
    }

    @Override
    public String getHelp() {
        return "Delete this";
    }
}
