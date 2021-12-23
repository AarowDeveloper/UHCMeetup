package me.aarow.astatine.utilities;

import com.mongodb.client.MongoDatabase;
import me.aarow.astatine.Meetup;
import me.aarow.astatine.backend.settings.MongoSettings;
import me.aarow.astatine.utilities.replace.Replace;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Utility {

    public static List<Player> getOnlinePlayers(){
        List<Player> onlinePlayers = new ArrayList<>();
        for(Player player : Bukkit.getServer().getOnlinePlayers()){
            onlinePlayers.add(player);
        }

        return onlinePlayers;
    }

    public static MongoSettings getMongoSettings(){
        String host = Meetup.getInstance().getSettings().getString("MONGO.HOST");
        int port = Meetup.getInstance().getSettings().getInt("MONGO.PORT");
        String database = Meetup.getInstance().getSettings().getString("MONGO.DATABASE");
        boolean authentication = Meetup.getInstance().getSettings().getBoolean("MONGO.AUTHENTICATION.ENABLED");
        String username = Meetup.getInstance().getSettings().getString("MONGO.AUTHENTICATION.USERNAME");
        String password = Meetup.getInstance().getSettings().getString("MONGO.AUTHENTICATION.PASSWORD");

        return new MongoSettings(host, port, database, authentication, username, password);
    }

    public static void createCollection(MongoDatabase mongoDatabase, String collection){
        if(!mongoDatabase.listCollectionNames().into(new ArrayList<>()).contains(collection)){
            mongoDatabase.createCollection(collection);
        }
    }

    public static List<String> replaceList(List<String> list, List<Replace> replaceList){
        List<String> replaced = new ArrayList<>();
        list.forEach(line -> {
            String replacedString = line;
            for(Replace replace : replaceList){
                replacedString = replacedString.replace(replace.getReplace(), replace.getReplaceFor());
            }
            replaced.add(replacedString);
        });
        return replaced;
    }

    public static String replace(String input, List<Replace> replaceList){
        String replaced = input;
        for(Replace replace : replaceList){
            replaced = replaced.replace(replace.getReplace(), replace.getReplaceFor());
        }
        return replaced;
    }

    public static void sound(Player player){
        player.playSound(player.getLocation(), Sound.NOTE_PLING, 2F, 2F);
    }

    public static boolean hasIndex(List<String> list, int i){
        try{
            list.get(i);
            return true;
        }catch(IndexOutOfBoundsException e){
            return false;
        }
    }

    public static String getNameFromUUID(UUID uuid){
        return Bukkit.getOfflinePlayer(uuid).getName();
    }

    public static List<Integer> shrinks(){
        return IntegerUtility.deserilize(Meetup.getInstance().getSettings().getString("BORDER.SHRINKS")).stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
    }
}
