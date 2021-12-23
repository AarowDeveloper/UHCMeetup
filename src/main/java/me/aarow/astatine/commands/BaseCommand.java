package me.aarow.astatine.commands;

import me.aarow.astatine.Meetup;
import me.aarow.astatine.utilities.text.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class BaseCommand implements CommandExecutor {

    private final CommandInfo commandInfo;

    public BaseCommand(){
        commandInfo = getClass().getDeclaredAnnotation(CommandInfo.class);

        Meetup.getInstance().getCommand(commandInfo.name()).setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!commandInfo.permission().isEmpty() && !commandSender.hasPermission(commandInfo.permission())){
            commandSender.sendMessage(Color.translate("&cNo permission."));
            return true;
        }
        if(commandInfo.playerOnly()){
            if(commandSender instanceof ConsoleCommandSender){
                commandSender.sendMessage(Color.translate("&cOnly players can execute this command!"));
                return true;
            }
            execute((Player) commandSender, strings);
            return true;
        }
        execute(commandSender, strings);
        return true;
    }

    public void execute(CommandSender sender, String args[]){}
    public void execute(Player player, String args[]){}
}
