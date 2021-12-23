package me.aarow.astatine.commands.impl;

import me.aarow.astatine.Meetup;
import me.aarow.astatine.commands.BaseCommand;
import me.aarow.astatine.commands.CommandInfo;
import me.aarow.astatine.menus.ViewStatsMenu;
import me.aarow.astatine.menus.ViewYourStatsMenu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandInfo(name = "stats", playerOnly = true)
public class StatsCommand extends BaseCommand {

    @Override
    public void execute(Player player, String[] args) {
        if(args.length != 0){
            if(Meetup.getInstance().getProfileManager().getOfflineProfile(Bukkit.getOfflinePlayer(args[0]).getUniqueId()) == null){
                new ViewYourStatsMenu().open(player);
                return;
            }

            new ViewStatsMenu(args[0]).open(player);
            return;
        }
        new ViewYourStatsMenu().open(player);
        return;
    }
}
