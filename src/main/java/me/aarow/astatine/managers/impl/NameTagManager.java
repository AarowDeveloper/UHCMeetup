package me.aarow.astatine.managers.impl;

import me.aarow.astatine.managers.Manager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Scoreboard;

public class NameTagManager extends Manager {

    public NameTagManager(){
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        scoreboard.registerNewTeam("enemy").setPrefix(ChatColor.GREEN + "");
        scoreboard.registerNewTeam("team").setPrefix(ChatColor.RED + "");
        scoreboard.registerNewTeam("spectator").setPrefix(ChatColor.GRAY + "");
    }
}
