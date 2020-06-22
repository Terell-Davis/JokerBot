package com.tyskyworks.kakorot.command;

import com.tyskyworks.kakorot.CommandManager;
import com.tyskyworks.kakorot.Config;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HelpCommand implements  ICommand {

    private final CommandManager manager;

    public HelpCommand(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(CommandContext ctx) {
        List<String> args = ctx.getArgs();
        TextChannel channel = ctx.getChannel();

        if (args.isEmpty()){
            StringBuilder builder = new StringBuilder();
            EmbedBuilder builder2 = EmbedUtils.defaultEmbed()
                    .setTitle("List of Commands");
                    builder2.setColor(0xfdfcff);

            builder.append("List of commands\n");
            manager.getCommand().stream().map(ICommand::getName).forEach(
                    (it) -> builder2.appendDescription("").appendDescription(Config.get("prefix")).appendDescription(it).appendDescription("\n")
            );

            channel.sendMessage(builder2.build()).queue();
            return;
        }

        String search = args.get(0);
        ICommand command = manager.getCommand(search);

        if (command == null){
            channel.sendMessage("No Command found for `-" + search + "`").queue();
        }

        channel.sendMessage(command.getHelp()).queue();

    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getHelp() {
        return "Show the list with commands in the bot\n" + "Usage: `-help [command]`";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("commands", "cmds", "commandlist","h");
    }
}
