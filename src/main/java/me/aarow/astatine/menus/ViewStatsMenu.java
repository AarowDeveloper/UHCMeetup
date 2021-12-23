package me.aarow.astatine.menus;

import lombok.AllArgsConstructor;
import me.aarow.astatine.Meetup;
import me.aarow.astatine.data.OfflineProfile;
import me.aarow.astatine.leaderboard.Leaderboard;
import me.aarow.astatine.leaderboard.LeaderboardType;
import me.aarow.astatine.stats.StatType;
import me.aarow.astatine.utilities.ItemBuilder;
import me.aarow.astatine.utilities.menu.Menu;
import me.aarow.astatine.utilities.menu.buttons.Button;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class ViewStatsMenu extends Menu {

    {
        setUpdate(true);
    }

    private String name;

    public String getTitle() {
        return Meetup.getInstance().getMenus().getString("VIEW-STATS.NAME").replace("<player>", name);
    }

    public int getSize() {
        return Meetup.getInstance().getMenus().getInt("VIEW-STATS.ROWS") * 9;
    }

    @Override
    public Map<Integer, Button> getButtons() {
        Map<Integer, Button> buttons = new HashMap<>();
        buttons.put(Meetup.getInstance().getMenus().getInt("VIEW-STATS.KILLS.SLOT"), new StatsButton("KILLS", StatType.KILLS, LeaderboardType.KILLS));
        buttons.put(Meetup.getInstance().getMenus().getInt("VIEW-STATS.WINS.SLOT"), new StatsButton("WINS", StatType.WINS, LeaderboardType.WINS));
        buttons.put(Meetup.getInstance().getMenus().getInt("VIEW-STATS.DEATHS.SLOT"), new StatsButton("DEATHS", StatType.DEATHS, LeaderboardType.DEATHS));
        buttons.put(Meetup.getInstance().getMenus().getInt("VIEW-STATS.KILLSTREAK.SLOT"), new StatsButton("KILLSTREAK", StatType.KILLSTREAK, LeaderboardType.KILLSTREAK));
        buttons.put(Meetup.getInstance().getMenus().getInt("VIEW-STATS.REROLLS.SLOT"), new StatsButton("REROLLS", StatType.REROLLS, LeaderboardType.REROLLS));
        buttons.put(Meetup.getInstance().getMenus().getInt("VIEW-STATS.ELO.SLOT"), new StatsButton("ELO", StatType.ELO, LeaderboardType.ELO));
        buttons.put(Meetup.getInstance().getMenus().getInt("VIEW-STATS.VIEW-LEADERBOARDS.SLOT"), new ViewLeaderboardsButton());

        if(Meetup.getInstance().getMenus().getBoolean("VIEW-STATS.FILL-WITH-GLASS")) {
            for (int i = 0; i < getSize(); i++) {
                buttons.putIfAbsent(i, Button.PLACEHOLDER);
            }
        }
        return buttons;
    }

    @AllArgsConstructor
    private class StatsButton extends Button {

        private String configName;
        private StatType type;
        private LeaderboardType leaderboardType;

        @Override
        public ItemStack getItem(Player player) {
            OfflineProfile profile = Meetup.getInstance().getProfileManager().getOfflineProfile(Bukkit.getOfflinePlayer(name).getUniqueId());

            ItemBuilder itemBuilder = new ItemBuilder(Material.valueOf(Meetup.getInstance().getMenus().getString("VIEW-STATS." + configName + ".MATERIAL")));
            itemBuilder.setName(Meetup.getInstance().getMenus().getString("VIEW-STATS." + configName + ".NAME").replace("<value>", String.valueOf(profile.getStatValue(type))));

            Leaderboard leaderboard = new Leaderboard(leaderboardType);

            Meetup.getInstance().getMenus().getStringList("VIEW-STATS." + configName + ".LORE").forEach(line -> {
                itemBuilder.addLoreLine(line.replace("<place>", String.valueOf(leaderboard.getPlaceByName(name))));
            });

            return itemBuilder.toItemStack();
        }
    }

    @AllArgsConstructor
    private class ViewLeaderboardsButton extends Button {
        @Override
        public ItemStack getItem(Player player) {
            ItemBuilder itemBuilder = new ItemBuilder(Material.valueOf(Meetup.getInstance().getMenus().getString("VIEW-STATS.VIEW-LEADERBOARDS.MATERIAL")));
            itemBuilder.setName(Meetup.getInstance().getMenus().getString("VIEW-STATS.VIEW-LEADERBOARDS.NAME"));
            itemBuilder.setLore(Meetup.getInstance().getMenus().getStringList("VIEW-STATS.VIEW-LEADERBOARDS.LORE"));

            return itemBuilder.toItemStack();
        }

        @Override
        public void onClick(Player player) {
            new LeaderboardMenu().open(player);
        }
    }
}
