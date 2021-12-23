package me.aarow.astatine.managers.impl;

import me.aarow.astatine.data.border.Shrink;
import me.aarow.astatine.managers.Manager;
import me.aarow.astatine.utilities.PlayerUtility;
import me.aarow.astatine.utilities.Utility;
import me.aarow.astatine.utilities.settings.Settings;
import me.aarow.astatine.utilities.text.Language;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BorderManager extends Manager {

    private List<Shrink> shrinks = new ArrayList<>();

    public BorderManager(){
        createBorder(currentBorder, 5, plugin.getGameManager().getMeetupWorld());

        int seconds = Settings.BORDER_SHRINK_EVERY;

        for(int blocks : Utility.shrinks()){
            shrinks.add(new Shrink(seconds, blocks));
            seconds = seconds + Settings.BORDER_SHRINK_EVERY;
        }
    }

    private int currentBorder = Settings.BORDER_SIZE;

    public void createBorder(int size, int height, World world) {
        final Location location = new Location(world, 0.0, 59.0, 0.0);
        for (int i = height; i < height + height; ++i) {
            for (int j = location.getBlockX() - size; j <= location.getBlockX() + size; ++j) {
                for (int k = 58; k <= 58; ++k) {
                    for (int l = location.getBlockZ() - size; l <= location.getBlockZ() + size; ++l) {
                        if (j == location.getBlockX() - size || j == location.getBlockX() + size || l == location.getBlockZ() - size || l == location.getBlockZ() + size) {
                            final Location locatioheight = new Location(world, (double)j, (double)k, (double)l);
                            locatioheight.setY((double)world.getHighestBlockYAt(locatioheight));
                            locatioheight.getBlock().setType(Material.BEDROCK);
                        }
                    }
                }
            }
        }
    }

    public void shrink(int size){
        Utility.getOnlinePlayers().forEach(player -> {
            PlayerUtility.scatter(player, size);
        });

        createBorder(size, 5, plugin.getGameManager().getMeetupWorld());
        setCurrentBorder(size);
    }

    public void handleMove(Player player){
        if(!player.getWorld().getName().equalsIgnoreCase(plugin.getGameManager().getMeetupWorld().getName())) return;

        boolean shrunk = false;
        int size = currentBorder;
        World w = player.getWorld();

        if (player.getLocation().getBlockX() > size) {
            player.setNoDamageTicks(59);
            player.setFallDistance(0.0f);

            player.teleport(new Location(w, size - 4, (w.getHighestBlockYAt(size - 4, player.getLocation().getBlockZ()) + 0.5), player.getLocation().getBlockZ()));
            player.setFallDistance(0.0f);

            player.getLocation().add(0, 2, 0).getBlock().setType(Material.AIR);
            player.getLocation().add(0, 3, 0).getBlock().setType(Material.AIR);
            player.getLocation().add(0, 4, 0).getBlock().setType(Material.AIR);

            shrunk = true;
            player.teleport(new Location(w, player.getLocation().getBlockX(), w.getHighestBlockAt(player.getLocation().getBlockX(), player.getLocation().getBlockZ()).getLocation().getBlockY() + 0.5, player.getLocation().getBlockZ()));
        }

        if (player.getLocation().getBlockZ() > size) {
            player.setNoDamageTicks(59);
            player.setFallDistance(0.0f);

            player.teleport(new Location(w, player.getLocation().getBlockX(), (w.getHighestBlockYAt(player.getLocation().getBlockX(), size - 4) + 0.5), size - 4));
            player.setFallDistance(0.0f);

            player.getLocation().add(0, 2, 0).getBlock().setType(Material.AIR);
            player.getLocation().add(0, 3, 0).getBlock().setType(Material.AIR);
            player.getLocation().add(0, 4, 0).getBlock().setType(Material.AIR);

            shrunk = true;
            player.teleport(new Location(w, player.getLocation().getBlockX(), w.getHighestBlockAt(player.getLocation().getBlockX(), player.getLocation().getBlockZ()).getLocation().getBlockY() + 0.5, player.getLocation().getBlockZ()));
        }

        if (player.getLocation().getBlockX() < -size) {
            player.setNoDamageTicks(59);
            player.setFallDistance(0.0f);

            player.teleport(new Location(w, -size + 4, (w.getHighestBlockYAt(-size + 4, player.getLocation().getBlockZ()) + 0.5), player.getLocation().getBlockZ()));
            player.setFallDistance(0.0f);

            player.getLocation().add(0, 2, 0).getBlock().setType(Material.AIR);
            player.getLocation().add(0, 3, 0).getBlock().setType(Material.AIR);
            player.getLocation().add(0, 4, 0).getBlock().setType(Material.AIR);

            shrunk = true;
            player.teleport(new Location(w, player.getLocation().getBlockX(), w.getHighestBlockAt(player.getLocation().getBlockX(), player.getLocation().getBlockZ()).getLocation().getBlockY() + 0.5, player.getLocation().getBlockZ()));
        }

        if (player.getLocation().getBlockZ() < -size) {
            player.setNoDamageTicks(59);
            player.setFallDistance(0.0f);

            player.teleport(new Location(w, player.getLocation().getBlockX(), (w.getHighestBlockYAt(player.getLocation().getBlockX(), -size + 4) + 0.5), -size + 4));
            player.setFallDistance(0.0f);

            player.getLocation().add(0, 2, 0).getBlock().setType(Material.AIR);
            player.getLocation().add(0, 3, 0).getBlock().setType(Material.AIR);
            player.getLocation().add(0, 4, 0).getBlock().setType(Material.AIR);

            shrunk = true;
            player.teleport(new Location(w, player.getLocation().getBlockX(), w.getHighestBlockAt(player.getLocation().getBlockX(), player.getLocation().getBlockZ()).getLocation().getBlockY() + 0.5, player.getLocation().getBlockZ()));
        }
        if(shrunk){
            player.sendMessage(Language.BORDER_PLAYER_TELEPORTED.get());
        }
    }

    public Shrink getNextShrink(){
        return shrinks.stream().filter(shrink -> !shrink.isDone()).findFirst().orElse(null);
    }

    public int getCurrentBorder() {
        return currentBorder;
    }

    public void setCurrentBorder(int currentBorder) {
        this.currentBorder = currentBorder;
    }

    public List<Shrink> getShrinks() {
        return shrinks;
    }
}
