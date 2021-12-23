package me.aarow.astatine.managers.impl;

import lombok.Getter;
import me.aarow.astatine.data.OfflineProfile;
import me.aarow.astatine.data.PlayerState;
import me.aarow.astatine.data.Profile;
import me.aarow.astatine.managers.Manager;
import org.bson.Document;
import org.bukkit.entity.Player;
import java.util.*;

public class ProfileManager extends Manager {

    @Getter private Map<UUID, Profile> profiles = new HashMap<>();
    private Map<UUID, OfflineProfile> offlineProfiles = new HashMap();

    public Profile getProfile(Object object){
        if(object instanceof Player){
            Player player = (Player) object;

            return profiles.get(player.getUniqueId());
        }
        if(object instanceof UUID){
            UUID uuid = (UUID) object;

            return profiles.get(uuid);
        }

        return null;
    }

    public void create(Object object, PlayerState playerState){
        if(object instanceof Player){
            Player player = (Player) object;

            profiles.put(player.getUniqueId(), new Profile(player.getUniqueId(), playerState));
        }
        if(object instanceof UUID){
            UUID uuid = (UUID) object;

            profiles.put(uuid, new Profile(uuid, playerState));
        }
    }

    public boolean exists(Object object) {
        if (object instanceof Player) {
            Player player = (Player) object;

            return profiles.containsKey(player.getUniqueId());
        }
        if (object instanceof UUID) {
            UUID uuid = (UUID) object;

            return profiles.containsKey(uuid);
        }
        return false;
    }

    public OfflineProfile getOfflineProfile(Object object){
        if(object instanceof Player){
            Player player = (Player) object;

            return offlineProfiles.get(player.getUniqueId());
        }
        if(object instanceof UUID){
            UUID uuid = (UUID) object;

            return offlineProfiles.get(uuid);
        }
        if(object instanceof String){
            return offlineProfiles.get(UUID.fromString((String) object));
        }

        return null;
    }

    public Map<UUID, OfflineProfile> getOfflineProfiles() {
        return offlineProfiles;
    }

    public void load(){
        plugin.getMongoManager().getProfiles().find().into(new ArrayList<>()).forEach(document -> {
            Iterator<UUID> offlineProfileIterator = offlineProfiles.keySet().iterator();
            while(offlineProfileIterator.hasNext()){
                UUID uuid = offlineProfileIterator.next();
                List<Document> documents = plugin.getMongoManager().getProfiles().find().into(new ArrayList<>());

                if(!documents.stream().anyMatch(checkDocument -> checkDocument.getString("UUID").equalsIgnoreCase(uuid.toString()))){
                    offlineProfileIterator.remove();
                }
            }
            OfflineProfile offlineProfile = new OfflineProfile(UUID.fromString(document.getString("UUID")), document.getString("REALNAME"), document.getInteger("KILLS"), document.getInteger("KILLSTREAK"), document.getInteger("DEATHS"), document.getInteger("WINS"), document.getInteger("REROLLS"), document.getInteger("ELO"));
            offlineProfiles.put(UUID.fromString(document.getString("UUID")), offlineProfile);
        });
    }
}
