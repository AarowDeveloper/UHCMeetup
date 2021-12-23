package me.aarow.astatine.utilities.settings;

import me.aarow.astatine.Meetup;

public class Settings {

   public static int MINIMUM_PLAYERS_TO_START = Meetup.getInstance().getSettings().getInt("MINIMUM-PLAYERS-TO-START");
   public static int BORDER_SIZE = Meetup.getInstance().getSettings().getInt("BORDER.SIZE");
   public static int RESTART_TIME = Meetup.getInstance().getSettings().getInt("RESTART-TIME");
   public static int BORDER_SHRINK_EVERY = Meetup.getInstance().getSettings().getInt("BORDER.SHRINK-EVERY");
   public static boolean BUNGEECORD = Meetup.getInstance().getSettings().getBoolean("BUNGEECORD.ENABLED");
   public static String FALLBACK_SERVER = Meetup.getInstance().getSettings().getString("BUNGEECORD.FALLBACK-SERVER");

}
