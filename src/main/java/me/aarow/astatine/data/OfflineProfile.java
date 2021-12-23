package me.aarow.astatine.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.aarow.astatine.stats.StatType;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
public class OfflineProfile {

    private UUID uuid;

    private String realName;
    private int kills;
    private int highestKillstreak;
    private int deaths;
    private int wins;
    private int rerolls;
    private int elo;

    public int getStatValue(StatType type){
        switch(type){
            case KILLS:
                return kills;
            case WINS:
                return wins;
            case DEATHS:
                return deaths;
            case KILLSTREAK:
                return highestKillstreak;
            case REROLLS:
                return rerolls;
            case ELO:
                return elo;
        }
        return -1;
    }
}
