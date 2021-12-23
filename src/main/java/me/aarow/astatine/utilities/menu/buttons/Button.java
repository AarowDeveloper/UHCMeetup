package me.aarow.astatine.utilities.menu.buttons;

import me.aarow.astatine.utilities.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public abstract class Button {

    public static Button PLACEHOLDER = getPlaceholder();


    public ItemStack getItem(Player player) {
        return null;
    }

    public void onClick(Player player){}
    public void onClick(InventoryClickEvent event){}

    private static Button getPlaceholder() {
        return new Button() {
            @Override
            public ItemStack getItem(Player player) {
                ItemBuilder itemBuilder = new ItemBuilder(Material.STAINED_GLASS_PANE);
                itemBuilder.setData(7);
                itemBuilder.setName(" ");
                return itemBuilder.toItemStack();
            }
        };
    }

    public static Button getFromItemStack(ItemStack itemStack){
        return new Button() {
            @Override
            public ItemStack getItem(Player player) {
                return itemStack;
            }
        };
    }
}
