package me.aarow.astatine.data.kits;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@AllArgsConstructor
public class Kit {

    @Getter private int id;
    @Getter private Map<Integer, ItemStack> items;
    @Getter private Map<EquipmentSlot, ItemStack> armor;

}
