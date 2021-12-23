package me.aarow.astatine.data;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import lombok.Getter;
import lombok.Setter;
import me.aarow.astatine.Meetup;
import me.aarow.astatine.data.kits.EquipmentSlot;
import me.aarow.astatine.data.kits.KitEditData;
import me.aarow.astatine.data.other.InventoryData;
import me.aarow.astatine.scenario.Scenario;
import me.aarow.astatine.stats.StatType;
import me.aarow.astatine.tasks.ProfileSyncTask;
import me.aarow.astatine.utilities.PlayerUtility;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter @Setter
public class Profile {

    private UUID uuid;

    private String realName;

    private PlayerState playerState;

    private int currentKills = 0;
    private int kills;
    private int highestKillstreak;
    private int deaths;
    private int wins;
    private int rerolls;
    private int elo;

    private boolean buildMode;

    private boolean staffMode;
    private boolean vanished;

    private KitEditData kitEditData;

    private Scenario votedScenario;

    private InventoryData inventoryData;

    public Profile(UUID uuid, PlayerState playerState){
        this.uuid = uuid;
        this.playerState = playerState;
        this.buildMode = false;
        this.staffMode = false;
        this.vanished = false;
        this.kitEditData = new KitEditData();

        this.load();
        new ProfileSyncTask(this).runTaskTimer(Meetup.getInstance(), 10L, 10L);
    }

    public void load(){
        Document profileDocument = Meetup.getInstance().getMongoManager().getProfiles().find(new Document("UUID", uuid.toString())).first();

        if(profileDocument != null){
            realName = profileDocument.getString("REALNAME");
            kills = profileDocument.getInteger("KILLS");
            wins = profileDocument.getInteger("WINS");
            deaths = profileDocument.getInteger("DEATHS");
            highestKillstreak = profileDocument.getInteger("KILLSTREAK");
            rerolls = profileDocument.getInteger("REROLLS");
            elo = profileDocument.getInteger("ELO");
        }else{
            Document document = new Document();
            document.put("UUID", uuid.toString());
            document.put("REALNAME", Bukkit.getOfflinePlayer(uuid).getName());
            document.put("KILLS", 0);
            document.put("KILLSTREAK", 0);
            document.put("DEATHS", 0);
            document.put("WINS", 0);
            document.put("REROLLS", 0);
            document.put("ELO", 0);
            Meetup.getInstance().getMongoManager().getProfiles().insertOne(document);
        }
    }

    public void save(){
        Document profileDocument = Meetup.getInstance().getMongoManager().getProfiles().find(Filters.eq("UUID", uuid.toString())).first();
        Document document = new Document();
        document.put("UUID", uuid.toString());
        document.put("REALNAME", Bukkit.getPlayer(uuid).getName());
        document.put("KILLS", kills);
        document.put("KILLSTREAK", (currentKills > highestKillstreak ? currentKills : highestKillstreak));
        document.put("DEATHS", deaths);
        document.put("WINS", wins);
        document.put("REROLLS", rerolls);
        document.put("ELO", elo);
        Meetup.getInstance().getMongoManager().getProfiles().replaceOne(profileDocument, document, new UpdateOptions().upsert(true));
    }

    public int getStatValue(StatType type){
        switch(type){
            case KILLS:
                return kills;
            case WINS:
                return wins;
            case DEATHS:
                return deaths;
            case KILLSTREAK:
                return highestKillstreak;
            case REROLLS:
                return rerolls;
            case ELO:
                return elo;
        }
        return -1;
    }

    public void updateInventoryData(){
        Player player = Bukkit.getPlayer(uuid);

        Map<Integer, ItemStack> inventory = new HashMap<>();
        Map<EquipmentSlot, ItemStack> armor = new HashMap<>();

        for(int j = 0; j < 35; j++){
            if(player.getInventory().getItem(j) != null){
                inventory.put(j, player.getInventory().getItem(j));
            }
        }

        if(player.getInventory().getHelmet() != null){
            armor.put(EquipmentSlot.HELMET, player.getInventory().getHelmet());
        }
        if(player.getInventory().getChestplate() != null){
            armor.put(EquipmentSlot.CHESTPLATE, player.getInventory().getChestplate());
        }
        if(player.getInventory().getLeggings() != null){
            armor.put(EquipmentSlot.LEGGINGS, player.getInventory().getLeggings());
        }
        if(player.getInventory().getBoots() != null){
            armor.put(EquipmentSlot.BOOTS, player.getInventory().getBoots());
        }

        InventoryData inventoryData = new InventoryData();
        inventoryData.setInventory(inventory);
        inventoryData.setArmor(armor);
        inventoryData.setEffects(new ArrayList<>(player.getActivePotionEffects()));

        this.inventoryData = inventoryData;
    }

    public void restoreInventory(){
        Player player = Bukkit.getPlayer(uuid);

        PlayerUtility.clearInventory(player);

        inventoryData.getInventory().keySet().forEach(slot -> {
            player.getInventory().setItem(slot, inventoryData.getInventory().get(slot));
        });

        inventoryData.getArmor().keySet().forEach(equipmentSlot -> {
            if(equipmentSlot == EquipmentSlot.HELMET){
                player.getInventory().setHelmet(inventoryData.getArmor().get(equipmentSlot));
            }
            if(equipmentSlot == EquipmentSlot.CHESTPLATE){
                player.getInventory().setChestplate(inventoryData.getArmor().get(equipmentSlot));
            }
            if(equipmentSlot == EquipmentSlot.LEGGINGS){
                player.getInventory().setLeggings(inventoryData.getArmor().get(equipmentSlot));
            }
            if(equipmentSlot == EquipmentSlot.BOOTS){
                player.getInventory().setBoots(inventoryData.getArmor().get(equipmentSlot));
            }
        });

        inventoryData.getEffects().forEach(effect -> {
            player.addPotionEffect(effect);
        });

        player.updateInventory();
    }

    public Scenario getVotedScenario() {
        return votedScenario;
    }

    public void setVotedScenario(Scenario votedScenario) {
        this.votedScenario = votedScenario;
    }
}
