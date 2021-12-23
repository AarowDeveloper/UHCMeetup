package me.aarow.astatine.managers.impl;

import lombok.Getter;
import me.aarow.astatine.Meetup;
import me.aarow.astatine.managers.Manager;
import me.aarow.astatine.utilities.DiscordActivity;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class DiscordManager extends Manager {

    @Getter private JDA jda;

    public DiscordManager(){
        JDABuilder jdaBuilder = new JDABuilder(AccountType.BOT);
        jdaBuilder.setToken(Meetup.getInstance().getDiscord().getString("TOKEN"));

        DiscordActivity activity = DiscordActivity.valueOf(Meetup.getInstance().getDiscord().getString("CUSTOM-STATUS.TYPE"));
        setActivity(activity, Meetup.getInstance().getDiscord().getString("CUSTOM-STATUS.NAME"), jdaBuilder);

        try{
            jda = jdaBuilder.build();
        }catch(LoginException e){
            e.printStackTrace();
        }
    }

    public void setActivity(DiscordActivity activity, String name, JDABuilder jdaBuilder){
        switch(activity){
            case PLAYING:
                jdaBuilder.setActivity(Activity.playing(name));
                break;
            case WATCHING:
                jdaBuilder.setActivity(Activity.watching(name));
                break;
        }
    }
}
