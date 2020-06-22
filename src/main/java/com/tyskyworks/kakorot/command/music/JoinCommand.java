package com.tyskyworks.kakorot.command.music;

import com.tyskyworks.kakorot.command.CommandContext;
import com.tyskyworks.kakorot.command.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class JoinCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        AudioManager audioManager = ctx.getGuild().getAudioManager();

        if (audioManager.isConnected()) {
            channel.sendMessage("Already connected to a channel").queue();
            return;
        }

        GuildVoiceState memberVoiceState = ctx.getMember().getVoiceState();

        if (!memberVoiceState.inVoiceChannel()){
            channel.sendMessage("Get in a Channel Bitch").queue();
            return;
        }

        VoiceChannel voiceChannel = memberVoiceState.getChannel();
        Member selfMember = ctx.getGuild().getSelfMember();

        if (!selfMember.hasPermission(voiceChannel, Permission.VOICE_CONNECT)){
            channel.sendMessageFormat("I can't join %s hoe", voiceChannel).queue();
            return;
        }

        audioManager.openAudioConnection(voiceChannel);
        channel.sendMessage("Joining......").queue();

    }

    @Override
    public String getName() {
        return "join";
    }

    @Override
    public String getHelp() {
        return "Bots Joins the Channel";
    }
}
