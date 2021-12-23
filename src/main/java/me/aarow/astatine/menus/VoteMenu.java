package me.aarow.astatine.menus;

import lombok.AllArgsConstructor;
import me.aarow.astatine.Meetup;
import me.aarow.astatine.data.Profile;
import me.aarow.astatine.scenario.Scenario;
import me.aarow.astatine.utilities.ItemBuilder;
import me.aarow.astatine.utilities.Utility;
import me.aarow.astatine.utilities.menu.Menu;
import me.aarow.astatine.utilities.menu.buttons.Button;
import me.aarow.astatine.utilities.text.Language;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class VoteMenu extends Menu {

    {
        setUpdate(true);
    }

    @Override
    public String getTitle() {
        return Utility.replace(Meetup.getInstance().getMenus().getString("VOTE.NAME"), Meetup.getInstance().getReplaceManager().getVoteReplace());
    }

    @Override
    public int getSize() {
        return Meetup.getInstance().getMenus().getInt("VOTE.ROWS") * 9;
    }

    @Override
    public Map<Integer, Button> getButtons() {
        Map<Integer, Button> buttons = new HashMap<>();

        buttons.put(Meetup.getInstance().getMenus().getInt("VOTE.TIMEBOMB.SLOT"), new VoteButton("TIMEBOMB", Scenario.TIME_BOMB));
        buttons.put(Meetup.getInstance().getMenus().getInt("VOTE.FIRELESS.SLOT"), new VoteButton("FIRELESS", Scenario.FIRELESS));
        buttons.put(Meetup.getInstance().getMenus().getInt("VOTE.WEBCAGE.SLOT"), new VoteButton("WEBCAGE", Scenario.WEB_CAGE));
        buttons.put(Meetup.getInstance().getMenus().getInt("VOTE.SAFELOOT.SLOT"), new VoteButton("SAFELOOT", Scenario.SAFE_LOOT));
        buttons.put(Meetup.getInstance().getMenus().getInt("VOTE.NOCLEAN.SLOT"), new VoteButton("NOCLEAN", Scenario.NO_CLEAN));
        buttons.put(Meetup.getInstance().getMenus().getInt("VOTE.RODLESS.SLOT"), new VoteButton("RODLESS", Scenario.RODLESS));
        buttons.put(Meetup.getInstance().getMenus().getInt("VOTE.BOWLESS.SLOT"), new VoteButton("BOWLESS", Scenario.BOWLESS));
        buttons.put(Meetup.getInstance().getMenus().getInt("VOTE.NOFALL.SLOT"), new VoteButton("NOFALL", Scenario.NO_FALL));

        if(Meetup.getInstance().getMenus().getBoolean("VOTE.FILL-WITH-GLASS")) {
            for (int i = 0; i < getSize(); i++) {
                buttons.putIfAbsent(i, Button.PLACEHOLDER);
            }
        }
        return buttons;
    }

    @AllArgsConstructor
    private class VoteButton extends Button {

        private String configName;
        private Scenario scenario;

        @Override
        public ItemStack getItem(Player player) {
            ItemBuilder itemBuilder = new ItemBuilder(Material.valueOf(Meetup.getInstance().getMenus().getString("VOTE." + configName + ".MATERIAL")));
            itemBuilder.setName(Utility.replace(Meetup.getInstance().getMenus().getString("VOTE." + configName + ".NAME"), Meetup.getInstance().getReplaceManager().getVoteReplace()));

            itemBuilder.setLore(Utility.replaceList(Meetup.getInstance().getMenus().getStringList("VOTE." + configName + ".LORE"), Meetup.getInstance().getReplaceManager().getVoteReplace()));
            return itemBuilder.toItemStack();
        }

        @Override
        public void onClick(Player player) {
            Profile profile = Meetup.getInstance().getProfileManager().getProfile(player);
            if (profile.getVotedScenario() == null) {
                profile.setVotedScenario(scenario);
                scenario.addVote();
                return;
            }
            if (profile.getVotedScenario() == scenario) {
                player.sendMessage(Language.ALREADY_VOTED_SCENARIO.get());
                return;
            } else {
                Scenario currentlyVoted = profile.getVotedScenario();
                profile.setVotedScenario(scenario);
                currentlyVoted.removeVote();
                scenario.addVote();
            }
        }
    }
}
