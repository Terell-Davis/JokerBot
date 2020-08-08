package com.tyskyworks.kakorot.command;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import com.tyskyworks.kakorot.Config;
import com.tyskyworks.kakorot.command.music.musicassets.GuildMusicManager;
import com.tyskyworks.kakorot.command.music.musicassets.PlayerManager;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SendCallingCard implements ICommand{
    @Override
    public void handle(CommandContext ctx) {
        String[] messages = new String[]{
                //Persona 5 Calling Cards
                "Sir [member], the utter bastard of lust. We know how shitty you are, and that you put your twisted desires on students that can't fight back. That's why we have decided to steal away those desires and make you confess your sins. This will be done tomorrow, so we hope you will be ready. From, \nThe Phantom Thieves of Hearts",
                "Sir [member], a great sinner of vanity whose talent has been exhausted. You are an artist who uses his authority to shamelessly steal the ideas of his pupils. We have decided to make you confess all your crimes with your own mouth. We will take your distorted desires without fail. From, \nThe Phantom Thieves.",
                "Sir [member], the money-devouring sinner of gluttony. You indulge in scamming others with horrendous methods that target minors exclusively. We have decided to make you confess all your crimes with your own mouth. We will take your distorted desires without fail. From, \nThe Phantom Thieves.",
                "[member] has committed a great sin of drowning in sloth. Thus, we will rob every last bit of those distorted desires.",
                "Sir [member] the great profiteering sinner of greed. Your success and global fame exists due to the tyranny you rain over your employees. Thus, we have decided to make you confess all your crimes with your own mouth. From, \nThe Phantom Thieves.",
                "Madame [member], a great sinner of jealousy. You have lost yourself amidst your obsession with success. For its sake, you are even willing to promote injustice as justice. From, \nThe Phantom Thieves."
        };

        final TextChannel channel = ctx.getChannel();
        AudioManager audioManager = ctx.getGuild().getAudioManager();
        final List<String> args = ctx.getArgs();

        if (args.isEmpty()) {
            // Usage
            EmbedBuilder usage = new EmbedBuilder();
            usage.setColor(0xff3923);
            usage.setTitle("Specify who to send to");
            usage.setDescription("Usage: `" + Config.get("prefix") + "SendCallingCard [member]`");
            ctx.getChannel().sendMessage(usage.build()).queue();
        } else {
            try {
                GuildVoiceState memberVoiceState = ctx.getMember().getVoiceState();
                VoiceChannel voiceChannel = memberVoiceState.getChannel();
                PlayerManager playerManager = PlayerManager.getInstance();
                GuildMusicManager musicManager = playerManager.getGuildMusicManager(ctx.getGuild());
                AudioPlayer player = musicManager.player;

                Random rand = new Random();
                int number = rand.nextInt(messages.length);
                EmbedBuilder Card = new EmbedBuilder();
                Card.setColor(0xf51707);
                Card.setDescription(messages[number].replace("[member]", String.join(" ", args)));
                channel.sendMessage(Card.build()).queue();

                if (memberVoiceState.inVoiceChannel()){
                    audioManager.openAudioConnection(voiceChannel);
                    channel.sendMessage("Sending Calling Card.").queue();

                    PlayerManager manager = PlayerManager.getInstance();

                    manager.loadAndPlay(ctx.getChannel(), "https://www.youtube.com/watch?v=uLOdiGGuoNk");
                    manager.getGuildMusicManager(ctx.getGuild()).player.setVolume(100);
                    AudioTrackInfo info = player.getPlayingTrack().getInfo();

                    channel.sendMessage(EmbedUtils.embedMessage(String.format(
                            "**__Now Playing:__** [%s](%s)\n%s %s - %s %s",
                            info.title,
                            info.uri,
                            player.isPaused() ? "\u23F8" : "🥞 ",
                            formatTime(player.getPlayingTrack().getPosition()),
                            formatTime(player.getPlayingTrack().getDuration()), " 🥞"
                    )).setColor(0xf51707).build()).queue();
                }
            } catch (IllegalArgumentException e) {
                if (e.toString().startsWith("java.lang.IllegalArgumentException: Message retrieval")) {
                    EmbedBuilder error = new EmbedBuilder();
                    error.setColor(0xff3923);
                    error.setTitle("⛔ The hell did you put to get this?");
                    error.setDescription("Really what did you do??????");
                    ctx.getChannel().sendMessage(error.build()).queue();
                }
            }
        }
    }

    private String formatTime(long timeInMillis) {
        final long hours = timeInMillis / TimeUnit.HOURS.toMillis(1);
        final long minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1);
        final long seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    @Override
    public String getName() {
        return "sendcallingcard";
    }

    @Override
    public String getHelp() {
        return "Sends a Calling Card to a user.";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("callingcard", "sendcard", "cc");
    }
}
