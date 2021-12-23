package me.aarow.astatine.utilities;

import me.aarow.astatine.Meetup;
import me.aarow.astatine.data.kits.EquipmentSlot;
import me.aarow.astatine.data.kits.Kit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KitUtility {

    public static List<Kit> loadKits(){
        ConfigFile kits = Meetup.getInstance().getKits();
        List<Kit> serilizedKits = new ArrayList<>();
        if(kits.getConfigurationSection("KITS") == null) return new ArrayList<>();
        kits.getConfigurationSection("KITS").getKeys(false).stream().map(Integer::parseInt).forEach(id -> serilizedKits.add(loadKit(id)));

        return serilizedKits;
    }

    public static boolean hasIndex(List<Kit> list, int i){
        try{
            list.get(i);
            return true;
        }catch(IndexOutOfBoundsException e){
            return false;
        }
    }

    public static Kit loadKit(int id){
        ConfigFile kits = Meetup.getInstance().getKits();
        String path = "KITS." + id + ".";

        Map<Integer, ItemStack> inventory = new HashMap<>();
        Map<EquipmentSlot, ItemStack> armor = new HashMap<>();

        if(kits.getConfigurationSection("KITS." + id + ".INVENTORY") != null){
            kits.getConfigurationSection(path + "INVENTORY").getKeys(false).forEach(slot -> {
                String sPath = path + "INVENTORY." + slot + ".";
                Material material = Material.valueOf(kits.getString(sPath + "MATERIAL"));
                int amount = kits.getInt(sPath + "AMOUNT");
                int data = kits.getInt(sPath + "DATA");

                ItemStack itemStack = new ItemStack(material, amount, (short) data);

                if(kits.getConfigurationSection(sPath + "ENCHANTMENTS") != null){
                    kits.getConfigurationSection(sPath + "ENCHANTMENTS").getKeys(false).forEach(key -> {
                        Enchantment enchantment = Enchantment.getByName(key);
                        int level = kits.getInt(sPath + "ENCHANTMENTS." + key + ".LEVEL");

                        ItemMeta itemMeta = itemStack.getItemMeta();
                        itemMeta.addEnchant(enchantment, level, true);
                        itemStack.setItemMeta(itemMeta);
                    });
                }
                inventory.put(Integer.parseInt(slot), itemStack);
            });
        }

        if(kits.getConfigurationSection("KITS." + id + ".ARMOR") != null){
            kits.getConfigurationSection(path + "ARMOR").getKeys(false).forEach(equipmentSlot -> {
                String sPath = path + "ARMOR." + equipmentSlot + ".";
                Material material = Material.valueOf(kits.getString(sPath + "MATERIAL"));
                int amount = kits.getInt(sPath + "AMOUNT");
                int data = kits.getInt(sPath + "DATA");

                ItemStack itemStack = new ItemStack(material, amount, (short) data);

                if(kits.getConfigurationSection(sPath + "ENCHANTMENTS") != null){
                    kits.getConfigurationSection(sPath + "ENCHANTMENTS").getKeys(false).forEach(key -> {
                        Enchantment enchantment = Enchantment.getByName(key);
                        int level = kits.getInt(sPath + "ENCHANTMENTS." + key + ".LEVEL");

                        ItemMeta itemMeta = itemStack.getItemMeta();
                        itemMeta.addEnchant(enchantment, level, true);
                        itemStack.setItemMeta(itemMeta);
                    });
                }
                armor.put(EquipmentSlot.valueOf(equipmentSlot), itemStack);

            });
        }



        return new Kit(id, inventory, armor);
    }

    public static void saveKits(){
        Meetup.getInstance().getKitManager().getKits().forEach(KitUtility::saveKit);
    }

    public static void saveKit(Kit kit){
        ConfigFile kits = Meetup.getInstance().getKits();
        kits.set("KITS." + kit.getId(), null);
        String path = "KITS." + kit.getId() + ".";
        String iPath = path + "INVENTORY.";
        String aPath = path + "ARMOR.";

        kits.set("KITS." + kit.getId(), new ArrayList<>());

        if(kit.getItems().size() != 0 && kit.getArmor().size() != 0){
            kits.set("KITS." + kit.getId(), null);
        }

        kit.getItems().keySet().forEach(slot -> {
            ItemStack item = kit.getItems().get(slot);
            String material = item.getType().name();
            int amount = item.getAmount();
            int data = item.getData().getData();
            kits.set(iPath + slot + ".MATERIAL", material);
            kits.set(iPath + slot + ".AMOUNT", amount);
            kits.set(iPath + slot + ".DATA", data);

            if(item.hasItemMeta()){
                item.getItemMeta().getEnchants().keySet().forEach(enchantment -> {
                    kits.set(iPath + slot + ".ENCHANTMENTS." + enchantment.getName() + ".LEVEL", item.getItemMeta().getEnchants().get(enchantment));
                });
            }
        });
        kit.getArmor().keySet().forEach(equipmentSlot -> {
            ItemStack item = kit.getArmor().get(equipmentSlot);
            String material = item.getType().name();
            int amount = item.getAmount();
            int data = item.getData().getData();
            kits.set(aPath + equipmentSlot.name() + ".MATERIAL", material);
            kits.set(aPath + equipmentSlot.name() + ".AMOUNT", amount);
            kits.set(aPath + equipmentSlot.name() + ".DATA", data);

            if(item.hasItemMeta()){
                item.getItemMeta().getEnchants().keySet().forEach(enchantment -> {
                    kits.set(aPath + equipmentSlot.name() + ".ENCHANTMENTS." + enchantment.getName() + ".LEVEL", item.getItemMeta().getEnchants().get(enchantment));
                });
            }
        });

        kits.save();
    }

    public static int getTranslatedSlot(int slot){
        switch(slot){
            case 9:
                return 27;
            case 10:
                return 28;
            case 11:
                return 29;
            case 12:
                return 30;
            case 13:
                return 31;
            case 14:
                return 32;
            case 15:
                return 33;
            case 16:
                return 34;
            case 17:
                return 35;
            case 18:
                return 18;
            case 19:
                return 19;
            case 20:
                return 20;
            case 21:
                return 21;
            case 22:
                return 22;
            case 23:
                return 23;
            case 24:
                return 24;
            case 25:
                return 25;
            case 26:
                return 26;
            case 27:
                return 9;
            case 28:
                return 10;
            case 29:
                return 11;
            case 30:
                return 12;
            case 31:
                return 13;
            case 32:
                return 14;
            case 33:
                return 15;
            case 34:
                return 16;
            case 35:
                return 17;
        }
        return slot;
    }

    public static int getUntranslatedSlot(int slot){
            switch(slot){
                case 27:
                    return 9;
                case 28:
                    return 10;
                case 29:
                    return 11;
                case 30:
                    return 12;
                case 31:
                    return 13;
                case 32:
                    return 14;
                case 33:
                    return 15;
                case 34:
                    return 16;
                case 35:
                    return 17;
                case 18:
                    return 18;
                case 19:
                    return 19;
                case 20:
                    return 20;
                case 21:
                    return 21;
                case 22:
                    return 22;
                case 23:
                    return 23;
                case 24:
                    return 24;
                case 25:
                    return 25;
                case 26:
                    return 26;
                case 9:
                    return 27;
                case 10:
                    return 28;
                case 11:
                    return 29;
                case 12:
                    return 30;
                case 13:
                    return 31;
                case 14:
                    return 32;
                case 15:
                    return 33;
                case 16:
                    return 34;
                case 17:
                    return 35;
            }
            return slot;
        }
    }
