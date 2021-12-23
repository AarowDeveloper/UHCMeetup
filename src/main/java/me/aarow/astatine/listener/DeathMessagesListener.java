package me.aarow.astatine.listener;

import me.aarow.astatine.Meetup;
import me.aarow.astatine.data.Profile;
import me.aarow.astatine.utilities.text.Color;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathMessagesListener implements Listener {

    public DeathMessagesListener(){
        Bukkit.getPluginManager().registerEvents(this, Meetup.getInstance());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player player = event.getEntity();

        Profile profile = Meetup.getInstance().getProfileManager().getProfile(player);

        if(event.getEntity().getKiller() == null){
            String deathMessage = event.getDeathMessage().replace(player.getName() + " ", "");

            event.setDeathMessage(Color.translate("&c" + player.getName() + "&7[&f" + profile.getCurrentKills() + "&7] &e" + deathMessage + "."));
            return;
        }
        if(event.getEntity().getKiller().getType() == EntityType.PLAYER){
            Player killer = event.getEntity().getKiller();
            Profile killerProfile = Meetup.getInstance().getProfileManager().getProfile(killer);

            String deathMessage = event.getDeathMessage().replace(player.getName() + " ", "").replace(killer.getName(), "");
            event.setDeathMessage(Color.translate("&c" + player.getName() + "&7[&f" + profile.getCurrentKills() + "&7] &e" + deathMessage + " &c" + killer.getName() + "&7[&f" + killerProfile.getCurrentKills() + "&7]&c."));
            return;
        }

        event.setDeathMessage(Color.translate("&c" + player.getName() + "&7[&f" + profile.getCurrentKills() + "&7] &edied."));
    }

    private Entity getLastAttacker(PlayerDeathEvent event){
        return event.getEntity().getLastDamageCause().getEntity();
    }
}
