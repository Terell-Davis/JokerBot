package com.tyskyworks.kakorot;

import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class JokerBot {

    public static JDA jda;

    private JokerBot() throws LoginException{
        EmbedUtils.setEmbedBuilder(
                () -> new EmbedBuilder().setColor(0xfc0328).setFooter("Test")
        );
    }

    public static void main(String[] args) throws LoginException
    {
        JDA api = JDABuilder.createDefault(Config.get("TOKEN")).build();

        api.addEventListener(new ReadyListener());
        api.getPresence().setStatus(OnlineStatus.ONLINE);
        api.getPresence().setActivity(Activity.playing("Persona 4 Golden j-h"));

    }
}

