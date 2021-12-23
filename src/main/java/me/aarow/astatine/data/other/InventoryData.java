package me.aarow.astatine.data.other;

import lombok.Getter;
import me.aarow.astatine.data.kits.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class InventoryData {

    private Map<Integer, ItemStack> inventory = new HashMap<>();
    private Map<EquipmentSlot, ItemStack> armor = new HashMap<>();
    private List<PotionEffect> effects = new ArrayList<>();

    public InventoryData setInventory(Map<Integer, ItemStack> inventory){
        this.inventory = inventory;
        return this;
    }

    public InventoryData setEffects(List<PotionEffect> effects){
        this.effects = effects;
        return this;
    }
    public InventoryData setArmor(Map<EquipmentSlot, ItemStack> armor){
        this.armor = armor;
        return this;
    }
}
