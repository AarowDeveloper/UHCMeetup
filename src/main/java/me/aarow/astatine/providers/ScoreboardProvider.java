package me.aarow.astatine.providers;

import me.aarow.astatine.Meetup;
import me.aarow.astatine.adapters.scoreboard.AssembleAdapter;
import me.aarow.astatine.data.Profile;
import me.aarow.astatine.game.GameState;
import me.aarow.astatine.utilities.Utility;
import me.aarow.astatine.utilities.text.StringUtility;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardProvider implements AssembleAdapter {

    @Override
    public String getTitle(Player player) {
        GameState gameState = Meetup.getInstance().getGameManager().getGameState();

        switch(Meetup.getInstance().getGameManager().getGameState()){
            case LOBBY:
                return Meetup.getInstance().getScoreboard().getString("LOBBY.TITLE").replace("<line>", "\u2503");
            case STARTING:
                return Meetup.getInstance().getScoreboard().getString("STARTING.TITLE").replace("<line>", "\u2503");
            case INGAME:
                return Meetup.getInstance().getScoreboard().getString("INGAME.TITLE").replace("<line>", "\u2503");
            case FINISHING:
                return Meetup.getInstance().getScoreboard().getString("FINISHING.TITLE").replace("<line>", "\u2503");
        }

        return null;
    }

    @Override
    public List<String> getLines(Player player) {
        List<String> lines = new ArrayList<>();

        Profile profile = Meetup.getInstance().getProfileManager().getProfile(player);

        switch(Meetup.getInstance().getGameManager().getGameState()) {
            case LOBBY:
                Meetup.getInstance().getScoreboard().getStringList("LOBBY.LINES").forEach(line -> {
                    if(line.equalsIgnoreCase("<scenarios>")){
                        Meetup.getInstance().getScenarioManager().getMostVoted().forEach(scenario -> {
                            int index = Meetup.getInstance().getScenarioManager().getMostVoted().indexOf(scenario);
                            lines.add(Meetup.getInstance().getScoreboard().getString("LOBBY.SCENARIO-FORMAT").replace("<place>", String.valueOf(index + 1)).replace("<scenario>", StringUtility.getScenarioName(scenario)).replace("<votes>", String.valueOf(scenario.getVotes())));
                        });
                    }else{
                        int neededPlayers = Meetup.getInstance().getSettings().getInt("MINIMUM-PLAYERS-TO-START") - Utility.getOnlinePlayers().size();
                        lines.add(line.replace("<players>", String.valueOf(Utility.getOnlinePlayers().size())).replace("<neededPlayers>", String.valueOf(neededPlayers)).replace("<seconds>", StringUtility.formatNumber(Meetup.getInstance().getGameManager().getStartSeconds())));
                    }
                });

                break;
            case STARTING:
                Meetup.getInstance().getScoreboard().getStringList("STARTING.LINES").forEach(line -> {
                    if(line.equalsIgnoreCase("<scenarios>")){
                        Meetup.getInstance().getScenarioManager().getMostVoted().forEach(scenario -> {
                            int index = Meetup.getInstance().getScenarioManager().getMostVoted().indexOf(scenario);
                            lines.add(Meetup.getInstance().getScoreboard().getString("LOBBY.SCENARIO-FORMAT").replace("<place>", String.valueOf(index + 1)).replace("<scenario>", StringUtility.getScenarioName(scenario)).replace("<votes>", String.valueOf(scenario.getVotes())));
                        });
                    }else{
                        int neededPlayers = Meetup.getInstance().getSettings().getInt("MINIMUM-PLAYERS-TO-START") - Utility.getOnlinePlayers().size();
                        lines.add(line.replace("<players>", String.valueOf(Utility.getOnlinePlayers().size())).replace("<neededPlayers>", String.valueOf(neededPlayers)).replace("<seconds>", StringUtility.formatNumber(Meetup.getInstance().getGameManager().getStartSeconds())));
                    }
                });

                break;
            case INGAME:
                Meetup.getInstance().getScoreboard().getStringList("INGAME.LINES").forEach(line -> {
                    lines.add(line.replace("<seconds>", StringUtility.formatNumber(Meetup.getInstance().getGameManager().getSeconds())).replace("<alive>", String.valueOf(Meetup.getInstance().getGameManager().getAlivePlayers().size())).replace("<initial>", String.valueOf(Meetup.getInstance().getGameManager().getInitialPlayers())).replace("<spectators>", String.valueOf(Meetup.getInstance().getGameManager().getSpectators().size())).replace("<bordersize>", String.valueOf(Meetup.getInstance().getBorderManager().getCurrentBorder())).replace("@borderInfo", StringUtility.getBorderInfo()).replace("<kills>", String.valueOf(profile.getCurrentKills())));
                });

                break;
        }
        return lines;
    }
}
