package me.aarow.astatine.listener;

import me.aarow.astatine.Meetup;
import me.aarow.astatine.data.Profile;
import me.aarow.astatine.data.kits.EquipmentSlot;
import me.aarow.astatine.data.kits.Kit;
import me.aarow.astatine.menus.KitManagerMenu;
import me.aarow.astatine.utilities.ItemUtility;
import me.aarow.astatine.utilities.KitUtility;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class KitMenuListener implements Listener {

    public KitMenuListener(){
        Bukkit.getPluginManager().registerEvents(this, Meetup.getInstance());
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Profile profile = Meetup.getInstance().getProfileManager().getProfile(player);
        if(!profile.getKitEditData().isEditingKit()) return;

        Kit kit = profile.getKitEditData().getCurrentKit();

        ItemStack itemStack = event.getCurrentItem();
        if(Arrays.asList(37, 38, 39, 40).contains(event.getSlot())){
            event.setCancelled(true);
        }
        if(event.getSlot() == 36){
            event.setCancelled(true);
            Map<Integer, ItemStack> items = new HashMap<>();
            for(int i = 0; i < 36; i++){
                if(event.getInventory().getItem(i) != null){
                    items.put(i, event.getInventory().getItem(i));
                }
            }
            Map<EquipmentSlot, ItemStack> armor = new HashMap<>();

            if(event.getInventory().getItem(41) != null){
                armor.put(EquipmentSlot.BOOTS, event.getInventory().getItem(41));
            }
            if(event.getInventory().getItem(42) != null){
                armor.put(EquipmentSlot.LEGGINGS, event.getInventory().getItem(42));
            }
            if(event.getInventory().getItem(43) != null){
                armor.put(EquipmentSlot.CHESTPLATE, event.getInventory().getItem(43));
            }
            if(event.getInventory().getItem(44) != null){
                armor.put(EquipmentSlot.HELMET, event.getInventory().getItem(44));
            }

            kit.getItems().clear();
            kit.getArmor().clear();
            kit.getItems().putAll(items);
            kit.getArmor().putAll(armor);

            if(!Meetup.getInstance().getKitManager().getKits().contains(kit)){
                Meetup.getInstance().getKitManager().getKits().add(kit);
            }

            Bukkit.getScheduler().runTaskLater(Meetup.getInstance(), () -> {
                new KitManagerMenu().open(player);
            }, 1L);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();
        Profile profile = Meetup.getInstance().getProfileManager().getProfile(player);

        if(profile.getKitEditData().isEditingKit()){
            KitUtility.saveKits();
            profile.getKitEditData().setEditingKit(false);
            profile.getKitEditData().setCurrentKit(null);
            ItemUtility.giveLobbyItems(player);
        }
    }
}
