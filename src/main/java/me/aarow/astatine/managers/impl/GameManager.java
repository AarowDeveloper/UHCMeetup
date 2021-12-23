package me.aarow.astatine.managers.impl;

import lombok.Getter;
import lombok.Setter;
import me.aarow.astatine.data.PlayerState;
import me.aarow.astatine.data.Profile;
import me.aarow.astatine.game.GameState;
import me.aarow.astatine.managers.Manager;
import me.aarow.astatine.scenario.Scenario;
import me.aarow.astatine.tasks.GameTask;
import me.aarow.astatine.utilities.PlayerUtility;
import me.aarow.astatine.utilities.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameManager extends Manager {

    @Getter private GameState gameState;

    @Getter private List<Scenario> activatedScenarios = new ArrayList<>();

    @Getter @Setter private int startSeconds = plugin.getSettings().getInt("COUNTDOWN-START");

    @Getter @Setter private int initialPlayers;

    private World meetupWorld;
    private World lobbyWorld;

    private int seconds;

    public GameManager(){
        this.gameState = GameState.WORLD_GENERATION;

        meetupWorld = Bukkit.getWorld(plugin.getSettings().getString("WORLD"));
        lobbyWorld = Bukkit.getWorld(plugin.getSettings().getString("LOBBY-WORLD"));
    }

    public void switchGameState(){
        switch(gameState){
            case WORLD_GENERATION:
                this.gameState = GameState.LOBBY;
                break;
            case LOBBY:
                this.gameState = GameState.STARTING;
                break;
            case STARTING:
                this.gameState = GameState.INGAME;
                break;
            case INGAME:
                this.gameState = GameState.FINISHING;
                break;
        }
    }

    public void switchBackGameState(){
        switch(gameState){
            case LOBBY:
                this.gameState = GameState.WORLD_GENERATION;
                break;
            case STARTING:
                this.gameState = GameState.LOBBY;
                break;
            case INGAME:
                this.gameState = GameState.STARTING;
                break;
            case FINISHING:
                this.gameState = GameState.INGAME;
                break;
        }
    }

    public void start(){
        switchGameState();

        Utility.getOnlinePlayers().forEach(player -> {
            Profile profile = plugin.getProfileManager().getProfile(player);

            profile.setPlayerState(PlayerState.PLAYING);

            PlayerUtility.scatter(player, plugin.getBorderManager().getCurrentBorder());
            PlayerUtility.giveRandomKit(player);
            player.setHealth(20.00);
            player.setFoodLevel(20);
            player.setFallDistance(0.0F);
            player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 1F, 1F);
        });

        new GameTask().runTaskTimer(plugin, 20L, 20L);

        setInitialPlayers(Utility.getOnlinePlayers().size());
    }

    public World getMeetupWorld(){
        return meetupWorld;
    }

    public World getLobbyWorld() {
        return lobbyWorld;
    }

    public List<Scenario> getActivatedScenarios() {
        return activatedScenarios;
    }

    public int getSeconds() {
        return seconds;
    }

    public void addSecond(){
        seconds++;
    }

    public List<Profile> getAliveProfiles(){
        return plugin.getProfileManager().getProfiles().values().stream().filter(profile -> profile.getPlayerState() == PlayerState.PLAYING).filter(profile -> Bukkit.getPlayer(profile.getUuid()) != null).collect(Collectors.toList());
    }

    public List<Player> getAlivePlayers(){
        return plugin.getProfileManager().getProfiles().values().stream().filter(profile -> profile.getPlayerState() == PlayerState.PLAYING).filter(profile -> Bukkit.getPlayer(profile.getUuid()) != null).map(profile -> Bukkit.getPlayer(profile.getUuid())).collect(Collectors.toList());
    }

    public List<Player> getSpectators(){
        return Utility.getOnlinePlayers().stream().map(player -> plugin.getProfileManager().getProfile(player)).filter(profile -> profile.getPlayerState() == PlayerState.SPECTATING).map(profile -> Bukkit.getPlayer(profile.getUuid())).collect(Collectors.toList());
    }

    public List<Profile> getSpectatorProfiles(){
        return Utility.getOnlinePlayers().stream().map(player -> plugin.getProfileManager().getProfile(player)).filter(profile -> profile.getPlayerState() == PlayerState.SPECTATING).collect(Collectors.toList());
    }

    public List<Player> getStaffPlayers(){
        return Utility.getOnlinePlayers().stream().map(player -> plugin.getProfileManager().getProfile(player)).filter(profile -> profile.isStaffMode()).map(profile -> Bukkit.getPlayer(profile.getUuid())).collect(Collectors.toList());
    }

    public List<Profile> getStaffProfiles(){
        return Utility.getOnlinePlayers().stream().map(player -> plugin.getProfileManager().getProfile(player)).filter(profile -> profile.isStaffMode()).collect(Collectors.toList());
    }

    public Player getGameWinner(){
        return getAlivePlayers().get(0);
    }
}
