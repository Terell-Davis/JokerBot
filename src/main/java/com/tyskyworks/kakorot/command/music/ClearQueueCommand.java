package com.tyskyworks.kakorot.command.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import com.tyskyworks.kakorot.Config;
import com.tyskyworks.kakorot.command.CommandContext;
import com.tyskyworks.kakorot.command.ICommand;
import com.tyskyworks.kakorot.command.music.musicassets.GuildMusicManager;
import com.tyskyworks.kakorot.command.music.musicassets.PlayerManager;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class ClearQueueCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        TextChannel channel = ctx.getChannel();
        PlayerManager playerManager = PlayerManager.getInstance();
        GuildMusicManager musicManager = playerManager.getGuildMusicManager(ctx.getGuild());
        BlockingQueue<AudioTrack> queue = musicManager.scheduler.getQueue();

        if (ctx.getAuthor().isBot()) return;

        String[] args = ctx.getMessage().getContentRaw().split("\\s+");
        if (args[0].equalsIgnoreCase(Config.get("prefix") + "clear")) {
            if (args.length < 2) {
                // Usage
                musicManager.scheduler.getQueue().clear();
                EmbedBuilder usage = new EmbedBuilder();
                usage.setColor(0xf51707);
                usage.setTitle("Clearing entire queue!");
               // usage.setDescription("Usage: `" + Config.get("prefix") + "clear [Track #]`");
                ctx.getChannel().sendMessage(usage.build()).queue();
            } else {
                List<AudioTrack> tracks = new ArrayList<>(queue);
                AudioTrack track = tracks.get(Integer.parseInt(args[1]) - 1);
                AudioTrackInfo info = track.getInfo();
                List<AudioTrack> list = new ArrayList<>();
                musicManager.scheduler.getQueue().drainTo(list);
                list.remove(Integer.parseInt(args[1]) - 1);
                musicManager.scheduler.getQueue().addAll(list);


                channel.sendMessage(EmbedUtils.embedMessage(String.format(
                        "**__Removed:__** [%s](%s)",
                        info.title,
                        info.uri
                )).setColor(0xf51707).build()).queue();
            }
        }
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getHelp() {
        return "Usage: `" + Config.get("prefix") + "clear [Track #]`";
    }
}
