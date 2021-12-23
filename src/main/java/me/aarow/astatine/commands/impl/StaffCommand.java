package me.aarow.astatine.commands.impl;

import me.aarow.astatine.Meetup;
import me.aarow.astatine.commands.BaseCommand;
import me.aarow.astatine.commands.CommandInfo;
import me.aarow.astatine.data.Profile;
import me.aarow.astatine.handlers.impl.StaffHandler;
import org.bukkit.entity.Player;

@CommandInfo(name = "staff", playerOnly = true, permission = "surfmeetup.command.staff")
public class StaffCommand extends BaseCommand {

    @Override
    public void execute(Player player, String[] args) {
        Profile profile = Meetup.getInstance().getProfileManager().getProfile(player);
        new StaffHandler(player);
    }
}
