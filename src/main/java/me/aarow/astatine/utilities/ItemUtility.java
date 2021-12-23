package me.aarow.astatine.utilities;

import me.aarow.astatine.Meetup;
import me.aarow.astatine.data.Profile;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemUtility {

    public static ItemStack getFromConfig(String name){
        ItemBuilder itemBuilder = new ItemBuilder(Material.valueOf(Meetup.getInstance().getItems().getString(name.toUpperCase() + ".MATERIAL")));
        if(Meetup.getInstance().getItems().getInt(name.toUpperCase() + ".DATA") != 0){
            int data = Meetup.getInstance().getItems().getInt(name.toUpperCase() + ".DATA");
            itemBuilder.setData((short) data);
        }
        itemBuilder.setName(Meetup.getInstance().getItems().getString(name.toUpperCase() + ".NAME"));
        if(Meetup.getInstance().getItems().getStringList(name.toUpperCase() + ".LORE").size() > 0) itemBuilder.setLore(Meetup.getInstance().getItems().getStringList(name.toUpperCase() + ".LORE"));
        return itemBuilder.toItemStack();
    }

    public static int getSlotFromConfig(String name){
        return Meetup.getInstance().getItems().getInt(name.toUpperCase() + ".SLOT");
    }

    public static void giveLobbyItems(Player player){
        PlayerUtility.clearInventory(player);
        player.getInventory().setItem(getSlotFromConfig("VOTE"), getFromConfig("VOTE"));
        player.getInventory().setItem(getSlotFromConfig("LEADERBOARDS"), getFromConfig("LEADERBOARDS"));
        player.getInventory().setItem(getSlotFromConfig("STATS"), getFromConfig("STATS"));
        if(player.hasPermission("surfmeetup.command.kit")){
            player.getInventory().setItem(getSlotFromConfig("EDIT-KITS"), getFromConfig("EDIT-KITS"));
        }
    }

    public static void removeLobbyItems(Player player){
        Bukkit.getScheduler().runTaskLater(Meetup.getInstance(), () -> {
            player.getInventory().setItem(Meetup.getInstance().getItems().getInt("VOTE.SLOT"), null);
            player.getInventory().setItem(Meetup.getInstance().getItems().getInt("LEADERBOARDS.SLOT"), null);
            player.getInventory().setItem(Meetup.getInstance().getItems().getInt("STATS.SLOT"), null);
            player.getInventory().setItem(Meetup.getInstance().getItems().getInt("EDIT-KITS.SLOT"), null);
        }, 2L);
    }

    public static void giveSpectatorItems(Player player){
        PlayerUtility.clearInventory(player);
        player.getInventory().setItem(getSlotFromConfig("RANDOM-TELEPORT"), getFromConfig("RANDOM-TELEPORT"));
        player.getInventory().setItem(getSlotFromConfig("ALIVE-PLAYERS"), getFromConfig("ALIVE-PLAYERS"));
        player.updateInventory();
    }

    public static void giveStaffItems(Player player){
        Profile profile = Meetup.getInstance().getProfileManager().getProfile(player);

        PlayerUtility.clearInventory(player);

        player.getInventory().setItem(getSlotFromConfig("RANDOM-TELEPORT"), getFromConfig("RANDOM-TELEPORT"));
        player.getInventory().setItem(getSlotFromConfig("ALIVE-PLAYERS"), getFromConfig("ALIVE-PLAYERS"));
        player.getInventory().setItem(getSlotFromConfig("SEE-INVENTORY"), getFromConfig("SEE-INVENTORY"));
        player.getInventory().setItem(getSlotFromConfig("FREEZE"), getFromConfig("FREEZE"));
        if(profile.isVanished()){
            player.getInventory().setItem(getSlotFromConfig("VANISH-ON"), getFromConfig("VANISH-ON"));
        }else{
            player.getInventory().setItem(getSlotFromConfig("VANISH-OFF"), getFromConfig("VANISH-OFF"));
        }
    }
}
