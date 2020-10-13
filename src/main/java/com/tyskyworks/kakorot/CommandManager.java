package com.tyskyworks.kakorot;

import com.tyskyworks.kakorot.commands.*;
import com.tyskyworks.kakorot.commands.extracommands.MemeCommand;
import com.tyskyworks.kakorot.commands.jokercommands.*;
import com.tyskyworks.kakorot.commands.music.*;
import com.tyskyworks.kakorot.commands.music.control.*;
import com.tyskyworks.kakorot.commands.music.musicassets.JoinCommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class CommandManager {
    private final List<ICommand> commands = new ArrayList<>();

    public CommandManager() {
        addCommand(new HelpCommand(this));
        addCommand(new PurgeCommand());
        addCommand(new CallingCardCommand());

        //Music Control
        addCommand(new PlayCommand()); addCommand(new PauseCommand()); addCommand(new EndCommand());
        addCommand(new SkipCommand()); addCommand(new VolumeCommand());addCommand(new ResumeCommand());
        addCommand(new BassBoostCommand()); addCommand(new UnBassBootCommand());

        //Music Other
        addCommand(new DeleteTrackCommand()); addCommand(new NowPlayingCommand()); addCommand(new ShuffleCommand());
        addCommand(new QueueCommand()); addCommand(new JoinCommand());

        //Joke(r) Commands
        addCommand(new QuoteCommand()); addCommand(new FKCommand()); addCommand(new CockandBallCommand());
        addCommand(new InspireCommand()); addCommand(new MemeCommand());

        //New Commands
        addCommand(new SoundBoardCommand()); addCommand(new RepostCommand()); addCommand(new GunCommand());

        /* To Add Later
         */

    }
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
    }
}
