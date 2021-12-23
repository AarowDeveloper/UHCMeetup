package me.aarow.astatine.tasks;

import me.aarow.astatine.Meetup;
import me.aarow.astatine.data.border.Shrink;
import me.aarow.astatine.game.GameState;
import me.aarow.astatine.utilities.IntegerUtility;
import me.aarow.astatine.utilities.text.Language;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTask extends BukkitRunnable {

    private Meetup plugin = Meetup.getInstance();

    @Override
    public void run() {
        plugin.getGameManager().addSecond();

        if(plugin.getGameManager().getGameState() != GameState.INGAME) {
            this.cancel();
            return;
        }


        if(plugin.getBorderManager().getNextShrink() != null) {
            Shrink shrink = plugin.getBorderManager().getNextShrink();
            if (shrink.getSeconds() == plugin.getGameManager().getSeconds()) {
                plugin.getBorderManager().shrink(shrink.getBlocks());
                shrink.setDone(true);

                Bukkit.broadcastMessage(Language.BORDER_SHRUNK.get().replace("<radius>", String.valueOf(shrink.getBlocks())));
            }

            int timeLeftBeforeShrink = shrink.getSeconds() - plugin.getGameManager().getSeconds();

            if (IntegerUtility.deserilize(Meetup.getInstance().getMessages().getString("BORDER.WILL-SHRINK-ON")).contains(timeLeftBeforeShrink)) {
                if (timeLeftBeforeShrink < 60) {
                    Bukkit.broadcastMessage(Language.BORDER_WILL_SHRINK.get().replace("<radius>", String.valueOf(shrink.getBlocks())).replace("<time>", String.valueOf(timeLeftBeforeShrink)).replace("<timeType>", "seconds"));
                } else {
                    Bukkit.broadcastMessage(Language.BORDER_WILL_SHRINK.get().replace("<radius>", String.valueOf(shrink.getBlocks())).replace("<time>", String.valueOf(timeLeftBeforeShrink / 60)).replace("<timeType>", "minutes"));
                }
            }
        }
    }
}
