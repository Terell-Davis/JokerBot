package com.tyskyworks.kakorot;

import com.tyskyworks.kakorot.command.*;
import com.tyskyworks.kakorot.command.jokecommands.*;
import com.tyskyworks.kakorot.command.music.*;
import com.tyskyworks.kakorot.command.music.ShuffleCommand;
import com.tyskyworks.kakorot.command.removed.WhoIsThereBass;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class CommandManager {
    private final List<ICommand> commands = new ArrayList<>();

    public CommandManager() {
        //Base Commands
        addCommand(new HelpCommand(this)); addCommand(new PurgeCommand());
        addCommand(new SendCallingCard()); addCommand(new QuoteCommand());
        //Music
        addCommand(new PlayCommand()); addCommand(new EndCommand()); addCommand(new SkipCommand());
        addCommand(new NowPlayingCommand()); addCommand(new QueueCommand());  addCommand(new VolumeCommand());
        addCommand(new PauseCommand()); addCommand(new ResumeCommand()); addCommand(new ShuffleCommand());
        addCommand(new ClearQueueCommand());
        //Joker Commands
        addCommand(new BassBoost()); addCommand(new UnBassBoot()); addCommand(new WhoIsThereBass());
        addCommand(new InspireCommand()); addCommand(new FKCommand());
        //Removed Commands
        //addCommand(new MemeCommand()); addCommand(new CockandBall()); addCommand(new PingCommand());
        //addCommand(new Ronaldinho());addCommand(new WebhookCommand());
        //addCommand(new JoinCommand());addCommand(new LeaveCommand()); addCommand(new KickCommand());

        //Work in Progress Commands
        //addCommand(new BuildPlaylist());
    }

    // Dupe Command checker
    private void addCommand(ICommand cmd) {
        boolean nameFound = this.commands.stream().anyMatch((it) -> it.getName().equalsIgnoreCase(cmd.getName()));

        if (nameFound) {
            throw new IllegalArgumentException("A command with this name is already present");
        }

        commands.add(cmd);
    }

    public List<ICommand> getCommand() {
        return commands;
    }

    @Nullable
    public ICommand getCommand(String search) {
        String searchLower = search.toLowerCase();

        for (ICommand cmd : this.commands) {
            if (cmd.getName().equals(searchLower) || cmd.getAliases().contains(searchLower)) {
                return cmd;
            }
        }

        return null;
    }

    void handle(GuildMessageReceivedEvent event) {
        String[] split = event.getMessage().getContentRaw()
                .replaceFirst("(?i)" + Pattern.quote(Config.get("PREFIX")), "")
                .split("\\s+");

        String invoke = split[0].toLowerCase();
        ICommand cmd = this.getCommand(invoke);

        if (cmd != null) {
            event.getChannel().sendTyping().queue();
            List<String> args = Arrays.asList(split).subList(1, split.length);

            CommandContext ctx = new CommandContext(event, args);

            cmd.handle(ctx);
        }
        //Command Not Found
        else {
            event.getChannel().sendMessage("⛔ Command Not Found!");
        }
    }

}
