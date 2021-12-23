package me.aarow.astatine.scenario;

public enum Scenario {
    TIME_BOMB, FIRELESS, WEB_CAGE, SAFE_LOOT, NO_CLEAN, RODLESS, BOWLESS, NO_FALL;

    private int votes = 0;

    public int getVotes() {
        return votes;
    }

    public void addVote(){
        this.votes++;
    }

    public void removeVote(){
        this.votes--;
    }
}
