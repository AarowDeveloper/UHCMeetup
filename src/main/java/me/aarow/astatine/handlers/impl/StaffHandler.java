package me.aarow.astatine.handlers.impl;

import me.aarow.astatine.Meetup;
import me.aarow.astatine.data.PlayerState;
import me.aarow.astatine.data.Profile;
import me.aarow.astatine.handlers.Handler;
import me.aarow.astatine.utilities.ItemUtility;
import me.aarow.astatine.utilities.PlayerUtility;
import me.aarow.astatine.utilities.Utility;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class StaffHandler extends Handler {

    public StaffHandler(Player player){
        Profile profile = Meetup.getInstance().getProfileManager().getProfile(player);

        if(profile.isStaffMode()) {
            profile.setStaffMode(false);
            profile.setVanished(false);

            if(profile.getPlayerState() == PlayerState.SPECTATING){
                new SpectatorHandler(player);

                return;
            }else if(profile.getPlayerState() == PlayerState.PLAYING){
                player.setGameMode(GameMode.SURVIVAL);
                Utility.getOnlinePlayers().forEach(online -> online.showPlayer(player));

                profile.restoreInventory();
                return;
            }else{

            }
            return;
        }else{
            profile.updateInventoryData();
            ItemUtility.giveStaffItems(player);
            player.setGameMode(GameMode.CREATIVE);
            profile.setStaffMode(true);
            profile.setVanished(true);
            Utility.getOnlinePlayers().forEach(online -> online.hidePlayer(player));
        }
    }
}
