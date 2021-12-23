package me.aarow.astatine.tasks;

import lombok.AllArgsConstructor;
import me.aarow.astatine.data.Profile;
import org.bukkit.scheduler.BukkitRunnable;

@AllArgsConstructor
public class ProfileSyncTask extends BukkitRunnable {

    private Profile profile;

    @Override
    public void run() {
        profile.load();
    }
}
