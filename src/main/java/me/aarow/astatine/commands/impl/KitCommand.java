package me.aarow.astatine.commands.impl;

import me.aarow.astatine.Meetup;
import me.aarow.astatine.commands.BaseCommand;
import me.aarow.astatine.commands.CommandInfo;
import me.aarow.astatine.game.GameState;
import me.aarow.astatine.menus.KitManagerMenu;
import me.aarow.astatine.utilities.text.Language;
import org.bukkit.entity.Player;

@CommandInfo(name = "kit", playerOnly = true, permission = "surfmeetup.command.kit")
public class KitCommand extends BaseCommand {

    @Override
    public void execute(Player player, String[] args) {
        if(Meetup.getInstance().getGameManager().getGameState() != GameState.STARTING && Meetup.getInstance().getGameManager().getGameState() != GameState.LOBBY){
            player.sendMessage(Language.GAME_ALREADY_STARTED.get());
            return;
        }
        new KitManagerMenu().open(player);
    }
}
