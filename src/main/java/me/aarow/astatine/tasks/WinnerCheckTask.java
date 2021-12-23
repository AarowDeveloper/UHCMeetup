package me.aarow.astatine.tasks;

import me.aarow.astatine.Meetup;
import me.aarow.astatine.game.GameState;
import me.aarow.astatine.utilities.Utility;
import me.aarow.astatine.utilities.text.Color;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class WinnerCheckTask extends BukkitRunnable {

    private Meetup plugin = Meetup.getInstance();

    @Override
    public void run() {
        if(plugin.getGameManager().getGameState() != GameState.INGAME) {
            this.cancel();
            return;
        }
        if(plugin.getGameManager().getAlivePlayers().size() == 1){

            Player winner = plugin.getGameManager().getGameWinner();
            plugin.getProfileManager().getProfile(winner).setWins(plugin.getProfileManager().getProfile(winner).getWins() + 1);
            int kills = plugin.getProfileManager().getProfile(winner).getCurrentKills();
            int wins = plugin.getProfileManager().getProfile(winner).getWins();

            Utility.getOnlinePlayers().forEach(player -> {
                plugin.getMessages().getStringList("WINNER-ANNOUNCE").forEach(line -> {
                    player.sendMessage(Color.translate(line.replace("<winner>", winner.getName()).replace("<winnerkills>", String.valueOf(kills)).replace("<winnerwins>", String.valueOf(wins))));
                });
            });


            plugin.getRestartManager().restart();
            this.cancel();
            return;
        }
    }
}
