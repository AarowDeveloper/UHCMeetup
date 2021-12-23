package me.aarow.astatine.tasks;

import lombok.AllArgsConstructor;
import me.aarow.astatine.Meetup;
import me.aarow.astatine.utilities.IntegerUtility;
import me.aarow.astatine.utilities.Utility;
import me.aarow.astatine.utilities.text.Language;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

@AllArgsConstructor
public class StartingTask extends BukkitRunnable {

    private Meetup plugin;
    private int minimumPlayers;

    @Override
    public void run() {

        if((plugin.getGameManager().getStartSeconds() - 1) == 0){
            plugin.getGameManager().setStartSeconds(plugin.getGameManager().getStartSeconds() - 1);
            plugin.getGameManager().start();
            this.cancel();
            return;
        }

        plugin.getGameManager().setStartSeconds(plugin.getGameManager().getStartSeconds() - 1);

        if(minimumPlayers > Utility.getOnlinePlayers().size()){
            plugin.getGameManager().setStartSeconds(plugin.getSettings().getInt("COUNTDOWN-START"));
            plugin.getGameManager().switchBackGameState();
            Bukkit.broadcastMessage(Language.CANCELED_START.get());
            this.cancel();
            return;
        }

        if(IntegerUtility.deserilize(plugin.getMessages().getString("START.BROADCAST-ON")).contains(plugin.getGameManager().getStartSeconds())){
            Bukkit.broadcastMessage(Language.STARTING.get().replace("<seconds>", String.valueOf(plugin.getGameManager().getStartSeconds())));
        }

    }
}
