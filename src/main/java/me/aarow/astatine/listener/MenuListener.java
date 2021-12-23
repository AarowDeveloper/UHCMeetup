package me.aarow.astatine.listener;

import me.aarow.astatine.Meetup;
import me.aarow.astatine.utilities.menu.Menu;
import me.aarow.astatine.utilities.menu.buttons.Button;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MenuListener implements Listener {

    public MenuListener(){
        Bukkit.getPluginManager().registerEvents(this, Meetup.getInstance());
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();

        if(!Meetup.getInstance().getMenuManager().getCurrentOpenedMenu().containsKey(player.getUniqueId())) return;

        Menu menu = Meetup.getInstance().getMenuManager().getCurrentOpenedMenu().get(player.getUniqueId());
        if(!menu.getButtons().containsKey(event.getSlot())) return;
        Button button = menu.getButtons().get(event.getSlot());

        event.setCancelled(true);

        button.onClick(player);
        button.onClick(event);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();
        if(!Meetup.getInstance().getMenuManager().getCurrentOpenedMenu().containsKey(player.getUniqueId())) return;
        Meetup.getInstance().getMenuManager().getCurrentOpenedMenu().remove(player.getUniqueId());
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        Player player = (Player) event.getPlayer();
        if(!Meetup.getInstance().getMenuManager().getCurrentOpenedMenu().containsKey(player.getUniqueId())) return;
        Meetup.getInstance().getMenuManager().getCurrentOpenedMenu().remove(player.getUniqueId());
    }
}
