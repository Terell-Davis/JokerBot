package com.tyskyworks.kakorot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class JokerBot {

    public static JDA jda;
    public static String use = "-";

    public static void main(String[] args) throws LoginException
    {
        JDA api = JDABuilder.createDefault(Config.get("TOKEN")).build();

        api.addEventListener(new ReadyListener());
        api.getPresence().setStatus(OnlineStatus.ONLINE);
        api.getPresence().setActivity(Activity.playing("Persona 4 Golden"));

    }
}

