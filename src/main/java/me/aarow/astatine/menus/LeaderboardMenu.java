package me.aarow.astatine.menus;

import lombok.AllArgsConstructor;
import me.aarow.astatine.Meetup;
import me.aarow.astatine.leaderboard.Leaderboard;
import me.aarow.astatine.leaderboard.LeaderboardField;
import me.aarow.astatine.leaderboard.LeaderboardType;
import me.aarow.astatine.utilities.ItemBuilder;
import me.aarow.astatine.utilities.Utility;
import me.aarow.astatine.utilities.menu.Menu;
import me.aarow.astatine.utilities.menu.buttons.Button;
import me.aarow.astatine.utilities.text.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LeaderboardMenu extends Menu {

    {
        setUpdate(true);
    }

    @Override
    public String getTitle() {
        return Meetup.getInstance().getMenus().getString("LEADERBOARDS.NAME");
    }

    @Override
    public int getSize() {
        return Meetup.getInstance().getMenus().getInt("LEADERBOARDS.ROWS") * 9;
    }

    @Override
    public Map<Integer, Button> getButtons() {
        Map<Integer, Button> buttons = new HashMap<>();
        buttons.put(Meetup.getInstance().getMenus().getInt("LEADERBOARDS.KILLS.SLOT"), new LeaderboardButton("KILLS", LeaderboardType.KILLS));
        buttons.put(Meetup.getInstance().getMenus().getInt("LEADERBOARDS.WINS.SLOT"), new LeaderboardButton("WINS", LeaderboardType.WINS));
        buttons.put(Meetup.getInstance().getMenus().getInt("LEADERBOARDS.DEATHS.SLOT"), new LeaderboardButton("DEATHS", LeaderboardType.DEATHS));
        buttons.put(Meetup.getInstance().getMenus().getInt("LEADERBOARDS.KILLSTREAK.SLOT"), new LeaderboardButton("KILLSTREAK", LeaderboardType.KILLSTREAK));
        buttons.put(Meetup.getInstance().getMenus().getInt("LEADERBOARDS.REROLLS.SLOT"), new LeaderboardButton("REROLLS", LeaderboardType.REROLLS));
        buttons.put(Meetup.getInstance().getMenus().getInt("LEADERBOARDS.ELO.SLOT"), new LeaderboardButton("ELO", LeaderboardType.ELO));
        buttons.put(Meetup.getInstance().getMenus().getInt("LEADERBOARDS.VIEW-STATS.SLOT"), new ViewStatsButton());

        if(Meetup.getInstance().getMenus().getBoolean("LEADERBOARDS.FILL-WITH-GLASS")) {
            for (int i = 0; i < getSize(); i++) {
                buttons.putIfAbsent(i, Button.PLACEHOLDER);
            }
        }
        return buttons;
    }

    @AllArgsConstructor
    private class LeaderboardButton extends Button {

        private String configName;
        private LeaderboardType leaderboardType;

        @Override
        public ItemStack getItem(Player player) {
            ItemBuilder itemBuilder = new ItemBuilder(Material.valueOf(Meetup.getInstance().getMenus().getString("LEADERBOARDS." + configName + ".MATERIAL")));
            itemBuilder.setName(Meetup.getInstance().getMenus().getString("LEADERBOARDS." + configName + ".NAME"));
            List<String> lore = new ArrayList<>();
            Leaderboard leaderboard = new Leaderboard(leaderboardType);

            List<String> names = leaderboard.getLeaderboardFields().stream().map(LeaderboardField::getName).collect(Collectors.toList());

            Meetup.getInstance().getMenus().getStringList("LEADERBOARDS." + configName + ".LORE").forEach(line -> {
                if(line.equalsIgnoreCase("<leaderboard>")){
                    for(int i = 0; i < 10; i++){
                        if(!Utility.hasIndex(names, i)){
                            lore.add(Meetup.getInstance().getMenus().getString("LEADERBOARDS." + configName + ".FORMAT-NOT-SET").replace("<place>", String.valueOf(i + 1)));
                        }else{
                            String name = names.get(i);
                            int value = leaderboard.getValueByName(name);

                            lore.add(Meetup.getInstance().getMenus().getString("LEADERBOARDS." + configName + ".FORMAT").replace("<place>", String.valueOf(i + 1)).replace("<player>", name).replace("<value>", String.valueOf(value)));
                        }
                    }
                }else{
                    lore.add(line);
                }

            });

            itemBuilder.setLore(Color.translate(lore));

            return itemBuilder.toItemStack();
        }
    }

    @AllArgsConstructor
    private class ViewStatsButton extends Button {
        @Override
        public ItemStack getItem(Player player) {
            ItemBuilder itemBuilder = new ItemBuilder(Material.valueOf(Meetup.getInstance().getMenus().getString("LEADERBOARDS.VIEW-STATS.MATERIAL")));
            itemBuilder.setName(Meetup.getInstance().getMenus().getString("LEADERBOARDS.VIEW-STATS.NAME"));
            itemBuilder.setLore(Meetup.getInstance().getMenus().getStringList("LEADERBOARDS.VIEW-STATS.LORE"));

            return itemBuilder.toItemStack();
        }

        @Override
        public void onClick(Player player) {
            new ViewYourStatsMenu().open(player);
        }
    }
}
