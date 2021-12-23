package me.aarow.astatine.utilities;

import me.aarow.astatine.Meetup;
import me.aarow.astatine.data.kits.Kit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PlayerUtility {

    public static void removePotionEffects(Player player){}

    public static void clearInventory(Player player){
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        removePotionEffects(player);
        player.updateInventory();
    }

    public static void scatter(Player player, int border){
        ThreadLocalRandom random = ThreadLocalRandom.current();

        int x = random.nextInt(-border, border);
        int z = random.nextInt(-border, border);
        int y = Meetup.getInstance().getGameManager().getMeetupWorld().getHighestBlockYAt(x, z);

        player.setFallDistance(0.0F);

        player.teleport(new Location(Meetup.getInstance().getGameManager().getMeetupWorld(), x, y, z));
    }

    public static void giveRandomKit(Player player){
        ThreadLocalRandom random = ThreadLocalRandom.current();
        clearInventory(player);
        if(Meetup.getInstance().getKitManager().getKits().size() == 0) return;

        if(Meetup.getInstance().getKitManager().getKits().size() == 1){
            Kit kit = Meetup.getInstance().getKitManager().getKits().get(0);
            kit.getItems().keySet().forEach(slot -> player.getInventory().setItem(slot, kit.getItems().get(slot)));
            kit.getArmor().keySet().forEach(equipmentSlot -> {
              switch(equipmentSlot){
                  case BOOTS:
                      player.getInventory().setBoots(kit.getArmor().get(equipmentSlot));
                      break;
                  case LEGGINGS:
                      player.getInventory().setLeggings(kit.getArmor().get(equipmentSlot));
                      break;
                  case CHESTPLATE:
                      player.getInventory().setChestplate(kit.getArmor().get(equipmentSlot));
                      break;
                  case HELMET:
                      player.getInventory().setHelmet(kit.getArmor().get(equipmentSlot));
                      break;
              }
            });

            return;
        }
        int index = random.nextInt(0, Meetup.getInstance().getKitManager().getKits().size() - 1);
        Kit kit = Meetup.getInstance().getKitManager().getKits().get(index);

        kit.getItems().keySet().forEach(slot -> player.getInventory().setItem(slot, kit.getItems().get(slot)));
        kit.getArmor().keySet().forEach(equipmentSlot -> {
            switch(equipmentSlot){
                case BOOTS:
                    player.getInventory().setBoots(kit.getArmor().get(equipmentSlot));
                    break;
                case LEGGINGS:
                    player.getInventory().setLeggings(kit.getArmor().get(equipmentSlot));
                    break;
                case CHESTPLATE:
                    player.getInventory().setChestplate(kit.getArmor().get(equipmentSlot));
                    break;
                case HELMET:
                    player.getInventory().setHelmet(kit.getArmor().get(equipmentSlot));
                    break;
            }
        });
    }

    public static void teleportToRandomPlayer(Player player){
        if(Meetup.getInstance().getGameManager().getAlivePlayers().size() == 0) return;
        List<Player> players = Meetup.getInstance().getGameManager().getAlivePlayers();
        if(players.size() == 1){
            player.teleport(players.get(0));
            return;
        }
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int index = random.nextInt(0, players.size() - 1);

        player.teleport(players.get(index));
    }
}
