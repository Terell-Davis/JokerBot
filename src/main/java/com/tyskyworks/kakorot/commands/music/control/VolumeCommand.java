package com.tyskyworks.kakorot.commands.music.control;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.tyskyworks.kakorot.Config;
import com.tyskyworks.kakorot.commands.CommandContext;
import com.tyskyworks.kakorot.commands.ICommand;
import com.tyskyworks.kakorot.commands.music.musicassets.GuildMusicManager;
import com.tyskyworks.kakorot.commands.music.musicassets.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.Arrays;
import java.util.List;

public class VolumeCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final List<String> args = ctx.getArgs();
        PlayerManager manager = PlayerManager.getInstance();
        GuildMusicManager music = manager.getGuildMusicManager(ctx.getGuild());
        GuildVoiceState memberVoiceState = ctx.getMember().getVoiceState();
        AudioPlayer player = music.player;

        if (ctx.getAuthor().isBot()) return;

        if (player.getPlayingTrack() == null) {
            EmbedBuilder empty = new EmbedBuilder();
            empty.setColor(0xf51707);
            empty.setDescription("The Player isn't playing anything");
            channel.sendMessage(empty.build()).queue();
            return;
        }

        if (args.isEmpty()) {
            EmbedBuilder usage = new EmbedBuilder();
            usage.setColor(0xff3923);
            usage.setTitle("Specify Volume");
            usage.setDescription("Usage: `" + Config.get("prefix") + "Volume [volume]`");
            ctx.getChannel().sendMessage(usage.build()).queue();
            return;
        }

        if(memberVoiceState.inVoiceChannel()) {
            try {
                music.player.setVolume(Integer.parseInt(args.get(0)));
                EmbedBuilder success = new EmbedBuilder();
                success.setColor(0xf51707);
                success.setTitle("🔊 Volume set to:" + args.get(0));
                ctx.getChannel().sendMessage(success.build()).queue();
            } catch (IllegalArgumentException e) {
                if (e.toString().startsWith("java.lang.IllegalArgumentException: Message retrieval")) {
                    EmbedBuilder error = new EmbedBuilder();
                    error.setColor(0xf51707);
                    error.setTitle("⛔ The hell did you put to get this?");
                    error.setDescription("Really what did you do??????");
                    ctx.getChannel().sendMessage(error.build()).queue();
                }
            }
        }else{
            EmbedBuilder other = new EmbedBuilder();
            other.setColor(0xf51707);
            other.setDescription("Please join a voice channel to use this command!");
            channel.sendMessage(other.build()).queue();
        }
    }
    @Override
    public String getName(){
        return "volume";
    }

    @Override
    public String getHelp(){
        return "Usage:" + Config.get("PREFIX") + "volume [# Percent]";
    }

    @Override
    public List<String> getAliases () {
        return Arrays.asList("vol", "v");
    }
}
