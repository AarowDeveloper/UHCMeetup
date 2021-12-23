package me.aarow.astatine.listener;

import me.aarow.astatine.Meetup;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class BlockedCommandsListeners implements Listener {

    public BlockedCommandsListeners(){
        Bukkit.getPluginManager().registerEvents(this, Meetup.getInstance());
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event){
        if(!event.getPlayer().isOp()) return;
        String[] args = event.getMessage().split(" ");

        if(args[0].equalsIgnoreCase("/kill")) event.setCancelled(true);
    }
}
