package me.aarow.astatine.leaderboard;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LeaderboardField {

    private String name;
    private int value;

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}
