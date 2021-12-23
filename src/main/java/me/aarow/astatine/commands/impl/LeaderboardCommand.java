package me.aarow.astatine.commands.impl;

import me.aarow.astatine.commands.BaseCommand;
import me.aarow.astatine.commands.CommandInfo;
import me.aarow.astatine.menus.LeaderboardMenu;
import org.bukkit.entity.Player;

@CommandInfo(name = "leaderboard", playerOnly = true)
public class LeaderboardCommand extends BaseCommand {

    @Override
    public void execute(Player player, String[] args) {
        new LeaderboardMenu().open(player);
    }
}
