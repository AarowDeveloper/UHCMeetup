package me.aarow.astatine.menus;

import lombok.AllArgsConstructor;
import me.aarow.astatine.Meetup;
import me.aarow.astatine.data.kits.Kit;
import me.aarow.astatine.utilities.ItemBuilder;
import me.aarow.astatine.utilities.KitUtility;
import me.aarow.astatine.utilities.menu.buttons.Button;
import me.aarow.astatine.utilities.text.Color;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class KitEditInventory{

    public KitEditInventory(Player player, Kit kit){
        Inventory inventory = Bukkit.createInventory(null, 5 * 9, Color.translate(Meetup.getInstance().getMenus().getString("EDIT-KIT.NAME").replace("<id>", String.valueOf(kit.getId()))));

        kit.getItems().keySet().forEach(slot -> {
            inventory.setItem(KitUtility.getUntranslatedSlot(slot), kit.getItems().get(slot));
        });
        kit.getArmor().keySet().forEach(equipmentSlot -> {
            switch(equipmentSlot){
                case BOOTS:
                    inventory.setItem(41, kit.getArmor().get(equipmentSlot));
                    break;
                case LEGGINGS:
                    inventory.setItem(42, kit.getArmor().get(equipmentSlot));
                    break;
                case CHESTPLATE:
                    inventory.setItem(43, kit.getArmor().get(equipmentSlot));
                    break;
                case HELMET:
                    inventory.setItem(44, kit.getArmor().get(equipmentSlot));
                    break;
            }
        });

        inventory.setItem(36, new ConfirmEdit().getItem(player));

        for(int i = 37; i < 41; i++){
            if(inventory.getItem(i) == null){
                inventory.setItem(i, Button.PLACEHOLDER.getItem(player));
            }
        }

        Meetup.getInstance().getProfileManager().getProfile(player).getKitEditData().setEditingKit(true);
        Meetup.getInstance().getProfileManager().getProfile(player).getKitEditData().setCurrentKit(kit);

        player.openInventory(inventory);
    }

    private class ConfirmEdit extends Button {

        @Override
        public ItemStack getItem(Player player) {
            ItemBuilder itemBuilder = new ItemBuilder(Material.valueOf(Meetup.getInstance().getMenus().getString("EDIT-KIT.CONFIRM.MATERIAL")));
            itemBuilder.setName(Meetup.getInstance().getMenus().getString("EDIT-KIT.CONFIRM.NAME"));
            itemBuilder.setLore(Meetup.getInstance().getMenus().getStringList("EDIT-KIT.CONFIRM.LORE"));

            return itemBuilder.toItemStack();
        }
    }
}
