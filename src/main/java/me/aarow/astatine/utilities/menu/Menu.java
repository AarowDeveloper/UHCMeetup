package me.aarow.astatine.utilities.menu;

import me.aarow.astatine.Meetup;
import me.aarow.astatine.utilities.menu.buttons.Button;
import me.aarow.astatine.utilities.text.Color;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

public class Menu {

    boolean update = false;

    public String getTitle() {
        return "";
    }

    public int getSize() {
        return 27;
    }

    public Map<Integer, Button> getButtons() {
        return null;
    }

    public void open(Player player){
        Inventory inventory = Bukkit.createInventory(player, getSize(), Color.translate(getTitle()));

        getButtons().keySet().forEach(slot -> {
            inventory.setItem(slot, getButtons().get(slot).getItem(player));
        });

        player.openInventory(inventory);
        Meetup.getInstance().getMenuManager().getCurrentOpenedMenu().put(player.getUniqueId(), this);

        if(update){
            update(player, inventory);
        }
    }

    public void addButton(Map<Integer, Button> buttons, Button button){
        for(int i = 0; i < getSize(); i++){
            if(!getButtons().containsKey(i)){
                buttons.put(i, button);
            }
        }
    }

    public void update(Player player, Inventory inventory){
        new BukkitRunnable(){
            public void run(){
                if(!Meetup.getInstance().getMenuManager().getCurrentOpenedMenu().containsKey(player.getUniqueId())){
                    this.cancel();
                    return;
                }
                getButtons().keySet().forEach(slot -> {
                    inventory.setItem(slot, getButtons().get(slot).getItem(player));
                });
            }
        }.runTaskTimer(Meetup.getInstance(), 1L, 1L);
    }

    public void setUpdate(boolean update){
        this.update = update;
    }
}
