package me.aarow.astatine.menus;

import lombok.AllArgsConstructor;
import me.aarow.astatine.Meetup;
import me.aarow.astatine.data.kits.Kit;
import me.aarow.astatine.utilities.ItemBuilder;
import me.aarow.astatine.utilities.ItemUtility;
import me.aarow.astatine.utilities.menu.Menu;
import me.aarow.astatine.utilities.menu.buttons.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KitManagerMenu extends Menu {

    @Override
    public String getTitle() {
        return Meetup.getInstance().getMenus().getString("EDIT-KITS.NAME").replace("<kitamount>", String.valueOf(Meetup.getInstance().getKitManager().getKits().size()));
    }

    @Override
    public int getSize() {
        return Meetup.getInstance().getMenus().getInt("EDIT-KITS.ROWS") * 9;
    }

    @Override
    public Map<Integer, Button> getButtons() {
        Map<Integer, Button> buttons = new HashMap<>();

        List<Kit> sorted = Meetup.getInstance().getKitManager().getKits().stream().sorted(Comparator.comparingInt(Kit::getId)).collect(Collectors.toList());

        int index = 0;

        for(Kit kit : sorted){
            buttons.put(index, new KitButton(kit.getId()));
            index++;
        }

        buttons.put(getSize() - 1, new CreateKitButton());

        if(Meetup.getInstance().getMenus().getBoolean("EDIT-KITS.FILL-WITH-GLASS")) {
            for (int i = 0; i < getSize(); i++) {
                buttons.putIfAbsent(i, Button.PLACEHOLDER);
            }
        }
        return buttons;
    }

    @AllArgsConstructor
    private class KitButton extends Button {
        private int id;

        @Override
        public ItemStack getItem(Player player) {
            ItemBuilder itemBuilder = new ItemBuilder(Material.valueOf(Meetup.getInstance().getMenus().getString("EDIT-KITS.BUTTON.MATERIAL")));
            itemBuilder.setName(Meetup.getInstance().getMenus().getString("EDIT-KITS.BUTTON.NAME").replace("<id>", String.valueOf(id)));
            itemBuilder.setLore(Meetup.getInstance().getMenus().getStringList("EDIT-KITS.BUTTON.LORE"));

            return itemBuilder.toItemStack();
        }

        @Override
        public void onClick(Player player) {
            ItemUtility.removeLobbyItems(player);
            new KitEditInventory(player, Meetup.getInstance().getKitManager().getByID(id));
        }
    }

    @AllArgsConstructor
    private class CreateKitButton extends Button {
        @Override
        public ItemStack getItem(Player player) {
            ItemBuilder itemBuilder = new ItemBuilder(Material.valueOf(Meetup.getInstance().getMenus().getString("EDIT-KITS.CREATE.MATERIAL")));
            itemBuilder.setName(Meetup.getInstance().getMenus().getString("EDIT-KITS.CREATE.NAME"));
            itemBuilder.setLore(Meetup.getInstance().getMenus().getStringList("EDIT-KITS.CREATE.LORE"));

            return itemBuilder.toItemStack();
        }

        @Override
        public void onClick(Player player) {
            ItemUtility.removeLobbyItems(player);

            Kit kit = new Kit(Meetup.getInstance().getKitManager().getNextID(), new HashMap<>(), new HashMap<>());

            new KitEditInventory(player, kit);
            Meetup.getInstance().getProfileManager().getProfile(player).getKitEditData().setEditingKit(true);
            Meetup.getInstance().getProfileManager().getProfile(player).getKitEditData().setCurrentKit(kit);
        }
    }
}
