package me.aarow.astatine.utilities.text;

import me.aarow.astatine.Meetup;
import me.aarow.astatine.utilities.Utility;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

public class Color {

    public static String translate(String input){
        return Utility.replace(ChatColor.translateAlternateColorCodes('&', input), Meetup.getInstance().getReplaceManager().getGlobalReplace());
    }

    public static List<String> translate(List<String> input){
        return Utility.replaceList(input.stream().map(Color::translate).collect(Collectors.toList()), Meetup.getInstance().getReplaceManager().getGlobalReplace());
    }
}
