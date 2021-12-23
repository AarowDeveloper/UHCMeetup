package me.aarow.astatine.managers.impl;

import me.aarow.astatine.managers.Manager;
import me.aarow.astatine.utilities.Utility;
import me.aarow.astatine.utilities.other.BungeeCordUtility;
import me.aarow.astatine.utilities.settings.Settings;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class RestartManager extends Manager {

    private int seconds = Settings.RESTART_TIME;

    public void restart(){
        if(seconds != Settings.RESTART_TIME) return;

        new RestartTask().runTaskTimer(plugin, 20L, 20L);
    }

    private class RestartTask extends BukkitRunnable{

        @Override
        public void run() {
            if((seconds - 1) == 0){
                seconds = 0;
                if(Settings.BUNGEECORD) {
                    Utility.getOnlinePlayers().forEach(player -> {
                        BungeeCordUtility.sendPlayer(player, Settings.FALLBACK_SERVER);
                    });
                }
                Bukkit.shutdown();
                this.cancel();
                return;
            }

            seconds--;
        }
    }
}
