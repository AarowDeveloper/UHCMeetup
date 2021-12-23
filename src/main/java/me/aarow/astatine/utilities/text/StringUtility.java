package me.aarow.astatine.utilities.text;

import me.aarow.astatine.Meetup;
import me.aarow.astatine.scenario.Scenario;
import org.bukkit.entity.Entity;

public class StringUtility {

    public static String getScenarioName(Scenario scenario){
        String normal = scenario.name();
        String args[] = normal.split("_");

        int index = 0;

        StringBuilder stringBuilder = new StringBuilder();
        for(String argument : args){
            index++;
            stringBuilder.append(argument.toUpperCase().charAt(0) + argument.substring(1).toLowerCase());
            if(args.length != index) {
                stringBuilder.append(" ");
            }
        }
        return stringBuilder.toString();
    }

    public static String formatNumber(int number) {
        int millis = number * 1000;
        int seconds = millis / 1000 % 60;
        int minutes = millis / 60000 % 60;
        int hours = millis / 3600000 % 24;

        return (hours > 0 ? hours + ":" : "") + (minutes < 10 ? "0" + minutes : minutes) + ":" + (seconds < 10 ? "0" + seconds : seconds);
    }

    public static String getBorderInfo(){
        if(Meetup.getInstance().getBorderManager().getNextShrink() == null){
            return "&7[&cLast&7]";
        }else{
            int timeLeft = Meetup.getInstance().getBorderManager().getNextShrink().getSeconds() - Meetup.getInstance().getGameManager().getSeconds();
            return "&7[&c" + (timeLeft > 59 ? "" + ((timeLeft / 60) + 1) + "m" : "" + timeLeft + "s") + "&7]";
        }
    }

    public static String formatEntity(Entity entity){
        String entityName = entity.getType().name();
        String[] args = entityName.split("_");

        int index = 0;

        StringBuilder stringBuilder = new StringBuilder();
        for(String argument : args){
            index++;
            if(args.length == index) {
                stringBuilder.append(argument.toUpperCase().charAt(0) + argument.substring(1).toLowerCase());
            }else{
                stringBuilder.append(argument.toUpperCase().charAt(0) + argument.substring(1).toLowerCase()).append(" ");
            }
        }
        return stringBuilder.toString();
    }
}
