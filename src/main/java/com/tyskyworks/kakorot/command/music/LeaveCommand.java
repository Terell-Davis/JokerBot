package com.tyskyworks.kakorot.command.music;

import com.tyskyworks.kakorot.command.CommandContext;
import com.tyskyworks.kakorot.command.ICommand;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class LeaveCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        AudioManager audioManager = ctx.getGuild().getAudioManager();

        if (!audioManager.isConnected()) {
            channel.sendMessage("Ain't even here bro!").queue();
            return;
        }
        VoiceChannel voiceChannel = audioManager.getConnectedChannel();

        if (!voiceChannel.getMembers().contains(ctx.getMember())) {
            channel.sendMessage("You have to be in the same voice channel as me to use this").queue();
            return;
        }

        audioManager.closeAudioConnection();
        channel.sendMessage("Disconnected from your channel").queue();

    }

    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public String getHelp() {
        return "Bot will leave the channel";
    }
}
