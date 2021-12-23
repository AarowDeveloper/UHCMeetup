package me.aarow.astatine.tasks;

import me.aarow.astatine.Meetup;
import org.bukkit.scheduler.BukkitRunnable;

public class GlobalDataSyncTask extends BukkitRunnable {

    @Override
    public void run() {
        Meetup.getInstance().getProfileManager().load();
    }
}
