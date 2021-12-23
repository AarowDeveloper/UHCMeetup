package me.aarow.astatine.utilities.text;

import me.aarow.astatine.Meetup;

public enum Language {
    ONLY_PLAYER_COMMAND("ONLY_PLAYER_COMMAND"),
    JOIN_MESSAGE("JOIN-MESSAGE"),
    LEAVE_MESSAGE("LEAVE-MESSAGE"),
    ALREADY_VOTED_SCENARIO("SCENARIO.ALREADY-VOTED"),
    STARTING("START.MESSAGE"),
    CANCELED_START("START.CANCELED"),
    BORDER_SHRUNK("BORDER.SHRUNK"),
    BORDER_WILL_SHRINK("BORDER.WILL-SHRINK"),
    BORDER_PLAYER_TELEPORTED("BORDER.PLAYER-TELEPORTED"),
    GAME_ALREADY_STARTED("GAME.ALREADY-STARTED");

    private String message;

    Language(String message){
        this.message = message;
    }

    public String get(){
        return Color.translate(Meetup.getInstance().getMessages().getString(message));
    }
}
