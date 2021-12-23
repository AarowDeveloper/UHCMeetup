package me.aarow.astatine.listener;

import me.aarow.astatine.Meetup;
import me.aarow.astatine.data.PlayerState;
import me.aarow.astatine.data.Profile;
import me.aarow.astatine.game.GameState;
import me.aarow.astatine.tasks.StartingTask;
import me.aarow.astatine.utilities.ItemUtility;
import me.aarow.astatine.utilities.Utility;
import me.aarow.astatine.utilities.settings.Settings;
import me.aarow.astatine.utilities.text.Language;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.*;

public class PlayerListeners implements Listener {

    public PlayerListeners(){
        Bukkit.getPluginManager().registerEvents(this, Meetup.getInstance());
    }

    @EventHandler
    public void onASyncLogin(AsyncPlayerPreLoginEvent event){
        if(!Meetup.getInstance().getProfileManager().exists(event.getUniqueId())) {
            switch(Meetup.getInstance().getGameManager().getGameState()){
                case LOBBY: case STARTING:
                    Meetup.getInstance().getProfileManager().create(event.getUniqueId(), PlayerState.WAITING);
                    break;
                case INGAME: case FINISHING:
                    Meetup.getInstance().getProfileManager().create(event.getUniqueId(), PlayerState.SPECTATING);
                    break;
            }
        }

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (Meetup.getInstance().getGameManager().getGameState() == GameState.LOBBY || Meetup.getInstance().getGameManager().getGameState() == GameState.STARTING) {
            event.setJoinMessage(Language.JOIN_MESSAGE.get()
                    .replace("<player>", player.getName())
                    .replace("<players>", String.valueOf(Utility.getOnlinePlayers().size()))
                    .replace("<maxPlayers>", String.valueOf(Bukkit.getMaxPlayers())));

            ItemUtility.giveLobbyItems(player);

            if(Meetup.getInstance().getGameManager().getGameState() == GameState.LOBBY) {
                if (Utility.getOnlinePlayers().size() >= Settings.MINIMUM_PLAYERS_TO_START) {
                    Meetup.getInstance().getGameManager().switchGameState();
                    new StartingTask(Meetup.getInstance(), Settings.MINIMUM_PLAYERS_TO_START).runTaskTimer(Meetup.getInstance(), 20L, 20L);
                }
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        if (Meetup.getInstance().getGameManager().getGameState() == GameState.LOBBY || Meetup.getInstance().getGameManager().getGameState() == GameState.STARTING) {
            event.setQuitMessage(Language.LEAVE_MESSAGE.get()
                    .replace("<player>", player.getName())
                    .replace("<players>", String.valueOf(Utility.getOnlinePlayers().size()))
                    .replace("<maxPlayers>", String.valueOf(Bukkit.getMaxPlayers())));
        }

        Meetup.getInstance().getProfileManager().getProfiles().remove(player.getUniqueId());
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event){
        Profile profile = Meetup.getInstance().getProfileManager().getProfile(event.getPlayer());
        if(profile.isBuildMode()) return;
        if(profile.getPlayerState() != PlayerState.PLAYING || profile.isStaffMode()){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent event){
        Profile profile = Meetup.getInstance().getProfileManager().getProfile(event.getPlayer());
        if(profile.isBuildMode()) return;
        if(profile.getPlayerState() != PlayerState.PLAYING || profile.isStaffMode()){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        if(!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        Profile profile = Meetup.getInstance().getProfileManager().getProfile(player);
        if(profile.getPlayerState() != PlayerState.PLAYING || profile.isStaffMode()){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamageEntity(EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof Player)) return;

        Player damager = (Player) event.getDamager();
        Profile profile = Meetup.getInstance().getProfileManager().getProfile(damager);

        if(profile.getPlayerState() != PlayerState.PLAYING || profile.isStaffMode()){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        Profile profile = Meetup.getInstance().getProfileManager().getProfile(event.getPlayer());
        if(profile.isBuildMode()) return;
        if(profile.getPlayerState() != PlayerState.PLAYING || profile.isStaffMode()){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event){
        Profile profile = Meetup.getInstance().getProfileManager().getProfile(event.getPlayer());
        if(profile.isBuildMode()) return;
        if(profile.getPlayerState() != PlayerState.PLAYING || profile.isStaffMode()){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        if(Meetup.getInstance().getGameManager().getGameState() != GameState.INGAME && Meetup.getInstance().getGameManager().getGameState() != GameState.FINISHING) return;

        Meetup.getInstance().getBorderManager().handleMove(player);
    }

    @EventHandler
    public void onFramePlace(HangingPlaceEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void onFrameBreak(HangingBreakEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onFood(FoodLevelChangeEvent event){
        ((Player) event.getEntity()).setFoodLevel(20);
    }
}
