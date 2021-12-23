package me.aarow.astatine.managers.impl;

import me.aarow.astatine.Meetup;
import me.aarow.astatine.data.Profile;
import me.aarow.astatine.leaderboard.Leaderboard;
import me.aarow.astatine.leaderboard.LeaderboardType;
import me.aarow.astatine.managers.Manager;
import me.aarow.astatine.scenario.Scenario;
import me.aarow.astatine.utilities.replace.Replace;
import me.aarow.astatine.utilities.text.StringUtility;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ReplaceManager extends Manager {

    public List<Replace> getGlobalReplace(){
        List<Replace> replaces = new ArrayList<>();
        replaces.add(new Replace("<line>", "┃"));
        replaces.add(new Replace("<bullet>", "\u2022"));
        replaces.add(new Replace("<arrowright>", "\u00BB"));
        replaces.add(new Replace("<arrowleft>", "\u00AB"));
        return replaces;
    }

    public List<Replace> getVoteReplace(){
        List<Replace> replaces = new ArrayList<>();
        replaces.add(new Replace("<timebombvotes>", String.valueOf(Scenario.TIME_BOMB.getVotes())));
        replaces.add(new Replace("<firelessvotes>", String.valueOf(Scenario.FIRELESS.getVotes())));
        replaces.add(new Replace("<webcagevotes>", String.valueOf(Scenario.WEB_CAGE.getVotes())));
        replaces.add(new Replace("<safelootvotes>", String.valueOf(Scenario.SAFE_LOOT.getVotes())));
        replaces.add(new Replace("<nocleanvotes>", String.valueOf(Scenario.NO_CLEAN.getVotes())));
        replaces.add(new Replace("<rodlessvotes>", String.valueOf(Scenario.RODLESS.getVotes())));
        replaces.add(new Replace("<bowlessvotes>", String.valueOf(Scenario.BOWLESS.getVotes())));
        replaces.add(new Replace("<nofallvotes>", String.valueOf(Scenario.NO_FALL.getVotes())));
        replaces.add(new Replace("<mostvotedscenario>", (plugin.getScenarioManager().isThereTopScenario() ? StringUtility.getScenarioName(plugin.getScenarioManager().getMostVoted().get(0)) : "&cNone")));

        replaces.addAll(getGlobalReplace());
        return replaces;
    }

    public List<Replace> getPlayerReplace(Player player){
        Profile profile = Meetup.getInstance().getProfileManager().getProfile(player);

        List<Replace> replaces = new ArrayList<>();
        replaces.add(new Replace("<kills>", String.valueOf(profile.getCurrentKills())));
        replaces.add(new Replace("<wins>", String.valueOf(profile.getWins())));

        Leaderboard leaderboard = new Leaderboard(LeaderboardType.WINS);

        replaces.add(new Replace("<place>", String.valueOf(leaderboard.getPlaceByName(player.getName()))));

        replaces.addAll(getGlobalReplace());
        return replaces;
    }
}
