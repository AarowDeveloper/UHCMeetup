package me.aarow.astatine.handlers.impl;

import me.aarow.astatine.Meetup;
import me.aarow.astatine.data.PlayerState;
import me.aarow.astatine.data.Profile;
import me.aarow.astatine.handlers.Handler;
import me.aarow.astatine.utilities.ItemUtility;
import me.aarow.astatine.utilities.Utility;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class SpectatorHandler extends Handler {

    public SpectatorHandler(Player player){
        Profile profile = Meetup.getInstance().getProfileManager().getProfile(player);

        player.setGameMode(GameMode.CREATIVE);
        profile.setPlayerState(PlayerState.SPECTATING);
        Utility.getOnlinePlayers().stream().map(online -> Meetup.getInstance().getProfileManager().getProfile(online)).filter(online -> online.getPlayerState() != PlayerState.SPECTATING).map(online -> Bukkit.getPlayer(online.getUuid())).forEach(online -> online.hidePlayer(player));
        ItemUtility.giveSpectatorItems(player);
    }
}
