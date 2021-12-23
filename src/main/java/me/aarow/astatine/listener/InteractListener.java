package me.aarow.astatine.listener;

import me.aarow.astatine.Meetup;
import me.aarow.astatine.menus.*;
import me.aarow.astatine.utilities.ItemUtility;
import me.aarow.astatine.utilities.PlayerUtility;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractListener implements Listener {

    public InteractListener(){
        Bukkit.getPluginManager().registerEvents(this, Meetup.getInstance());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();

        if(!event.getAction().name().contains("RIGHT")) return;
        if(event.getItem() == null) return;

        if(event.getItem().isSimilar(ItemUtility.getFromConfig("VOTE"))){
            new VoteMenu().open(player);
        }
        if(event.getItem().isSimilar(ItemUtility.getFromConfig("LEADERBOARDS"))){
            new LeaderboardMenu().open(player);
        }
        if(event.getItem().isSimilar(ItemUtility.getFromConfig("STATS"))){
            new ViewYourStatsMenu().open(player);
        }
        if(event.getItem().isSimilar(ItemUtility.getFromConfig("EDIT-KITS"))){
            new KitManagerMenu().open(player);
        }
        if(event.getItem().isSimilar(ItemUtility.getFromConfig("RANDOM-TELEPORT"))){
            PlayerUtility.teleportToRandomPlayer(player);
        }
        if(event.getItem().isSimilar(ItemUtility.getFromConfig("ALIVE-PLAYERS"))){
            new AlivePlayersMenu().open();
        }
    }
}
