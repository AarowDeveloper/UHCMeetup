package me.aarow.astatine.listener;

import me.aarow.astatine.Meetup;
import me.aarow.astatine.data.Profile;
import me.aarow.astatine.handlers.impl.DeathHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    public DeathListener(){
        Bukkit.getPluginManager().registerEvents(this, Meetup.getInstance());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player player = event.getEntity();

        if(event.getEntity().getKiller() != null){
            Player killer = event.getEntity().getKiller();
            Profile killerProfile = Meetup.getInstance().getProfileManager().getProfile(killer);

            killerProfile.setCurrentKills(killerProfile.getCurrentKills() + 1);
            killerProfile.setKills(killerProfile.getKills() + 1);
        }

        new DeathHandler(player);
    }
}
