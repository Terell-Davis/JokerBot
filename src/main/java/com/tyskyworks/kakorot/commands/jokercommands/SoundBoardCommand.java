package com.tyskyworks.kakorot.commands.jokercommands;

import com.tyskyworks.kakorot.Config;
import com.tyskyworks.kakorot.commands.CommandContext;
import com.tyskyworks.kakorot.commands.ICommand;
import com.tyskyworks.kakorot.commands.music.musicassets.JoinCommand;
import com.tyskyworks.kakorot.commands.music.musicassets.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.Arrays;
import java.util.List;

public class SoundBoardCommand implements ICommand {

    @Override
    public void handle(CommandContext ctx) {
        AudioManager audioManager = ctx.getGuild().getAudioManager();
        final TextChannel channel = ctx.getChannel();
        PlayerManager manager = PlayerManager.getInstance();
        final List<String> args = ctx.getArgs();

        if ((ctx.getArgs().isEmpty())) {
            channel.sendMessage("Please enter what sound to play!").queue();
            return;
        }

        if (!audioManager.isConnected()) {
            JoinCommand join = new JoinCommand();
            join.handle(ctx);
        }
        String[] messages = new String[]{"amiibo", "ascending", "augh", "awww", "babababa", "bababooey", "bruh",
                "cockroach", "crit", "cutg", "cutg2", "death", "dialup", "dominating", "e", "flowey",
                "fortnite", "foxcomeon", "foxfiryah", "foxhah", "foxmission", "fridaysailer", "game",
                "hitmaker", "inception", "loudnight", "marioscream", "minedrink", "mineeat", "moof",
                "myfinalmessage", "pan", "pegging", "pelo", "ping", "root", "sans1", "sans2",
                "sans4", "screemstar", "sjyoo", "speen", "superspeen", "tacobell", "tidus", "tingle",
                "tobecon", "utintro", "winxperror", "yodacbt", "yodapenetrating", "yoo"
        };

        String play;

        if (args.get(0).equals("list")){
            EmbedBuilder list = new EmbedBuilder();
            list.setColor(0xf51707);
            list.setTitle("🥞 **__Sounds__** 🥞");
            for (int i = 0; i < messages.length; i++){
                list.setColor(0xf51707);
                list.appendDescription((i + 1) + ". " + messages[i] + " \n");
            }
            ctx.getChannel().sendMessage(list.build()).queue();
            return;
        }else{
            String path = Config.get("JOKERSOUNDSPATH");
            System.out.print(path);
            play = path + args.get(0) + ".mp3";
        }

        GuildVoiceState memberVoiceState = ctx.getMember().getVoiceState();
        if(memberVoiceState.inVoiceChannel()) {
            manager.loadAndPlay(ctx.getChannel(), play);
            manager.getGuildMusicManager(ctx.getGuild()).player.setVolume(105);
        }
    }

    @Override
    public String getName() {
        return "SoundBoard";
    }

    @Override
    public String getHelp() {
        return "Usage:" + Config.get("PREFIX") + "soundboard [soundname] (Use 'list' to see available sounds)";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("sb", "sound");
    }
}
