package me.aarow.astatine.handlers.impl;

import me.aarow.astatine.data.Profile;
import me.aarow.astatine.handlers.Handler;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class DeathHandler extends Handler {

    public DeathHandler(Player player){
        player.setVelocity(new Vector(0, 0, 0));
        player.setHealth(player.getMaxHealth());

        Profile profile = plugin.getProfileManager().getProfile(player);
        profile.setDeaths(profile.getDeaths() + 1);

        new SpectatorHandler(player);
    }
}
