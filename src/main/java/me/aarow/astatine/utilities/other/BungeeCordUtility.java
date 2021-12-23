package me.aarow.astatine.utilities.other;

import me.aarow.astatine.Meetup;
import me.aarow.astatine.utilities.settings.Settings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class BungeeCordUtility {

    public static void sendPlayer(Player player, String server){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeUTF("Connect");
            dataOutputStream.writeUTF(server);
        } catch (IOException e) {
            e.printStackTrace();
        }

        player.sendPluginMessage(Meetup.getInstance(), "BungeeCord", byteArrayOutputStream.toByteArray());
    }

    public static void registerBungeecord() {
        if (!Bukkit.getMessenger().isOutgoingChannelRegistered(Meetup.getInstance(), "BungeeCord") && Settings.BUNGEECORD) {
            Bukkit.getMessenger().registerOutgoingPluginChannel(Meetup.getInstance(), "BungeeCord");
        }
    }
}
