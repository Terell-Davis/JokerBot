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

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class SoundBoardCommand implements ICommand {

    @Override
    public void handle(CommandContext ctx) {
        AudioManager audioManager = ctx.getGuild().getAudioManager();
        final TextChannel channel = ctx.getChannel();
        PlayerManager manager = PlayerManager.getInstance();
        final List<String> args = ctx.getArgs();
        final String path = Config.get("SOUNDS");

        if (args.isEmpty()) {
            EmbedBuilder fk = new EmbedBuilder();
            fk.setColor(0xf51707);
            fk.setTitle("Specify who to hate");
            fk.setDescription("Usage: `" + Config.get("prefix") + "fk <who your homie hate>`");
            channel.sendMessage(fk.build()).queue();
        }

        if (!audioManager.isConnected()) {
            JoinCommand join = new JoinCommand();
            join.handle(ctx);
        }

        File sound = new File(path);
        String[] sounds = sound.list();

        if (args.get(0).equals("list")){
            EmbedBuilder list = new EmbedBuilder();
            list.setColor(0xf51707);
            list.setTitle("🥞 **__Sounds__** 🥞");
            int counter = 0;
            for (String soundname : sounds){
                 counter++;
                list.setColor(0xf51707);
                list.appendDescription((counter + ". " + soundname.replace(".mp3", "") + "\n"));
            }

            ctx.getChannel().sendMessage(list.build()).queue();
            return;
        }

        String play = path + args.get(0) + ".mp3";

        GuildVoiceState memberVoiceState = ctx.getMember().getVoiceState();
        assert memberVoiceState != null;
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
