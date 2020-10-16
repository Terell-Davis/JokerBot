package com.tyskyworks.kakorot.commands.jokercommands;

import com.tyskyworks.kakorot.Config;
import com.tyskyworks.kakorot.commands.CommandContext;
import com.tyskyworks.kakorot.commands.ICommand;
import net.dv8tion.jda.api.entities.TextChannel;

import java.io.File;

public class GunCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        TextChannel channel = ctx.getChannel();

        String path = Config.get("ASSETS");
        channel.sendFile(new File( path + "gun.png")).queue();
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
