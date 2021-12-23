package me.aarow.astatine.leaderboard;

import me.aarow.astatine.Meetup;
import me.aarow.astatine.data.OfflineProfile;

import java.util.*;
import java.util.stream.Collectors;

public class Leaderboard {

    private List<LeaderboardField> leaderboardFields = new ArrayList<>();

    public Leaderboard(LeaderboardType leaderboardType){
        if(leaderboardType == LeaderboardType.KILLS){
                List<OfflineProfile> profiles = new ArrayList<>(Meetup.getInstance().getProfileManager().getOfflineProfiles().values());
                List<OfflineProfile> sortedProfiles = profiles.stream().sorted(Comparator.comparingInt(OfflineProfile::getKills).reversed()).collect(Collectors.toList());

                sortedProfiles.forEach(offlineProfile -> {
                    leaderboardFields.add(new LeaderboardField(offlineProfile.getRealName(), offlineProfile.getKills()));
                });
        }
        if(leaderboardType == LeaderboardType.WINS){
            List<OfflineProfile> profiles = new ArrayList<>(Meetup.getInstance().getProfileManager().getOfflineProfiles().values());
            List<OfflineProfile> sortedProfiles = profiles.stream().sorted(Comparator.comparingInt(OfflineProfile::getWins).reversed()).collect(Collectors.toList());

            sortedProfiles.forEach(offlineProfile -> {
                leaderboardFields.add(new LeaderboardField(offlineProfile.getRealName(), offlineProfile.getWins()));
            });
        }
        if(leaderboardType == LeaderboardType.DEATHS){
            List<OfflineProfile> profiles = new ArrayList<>(Meetup.getInstance().getProfileManager().getOfflineProfiles().values());
            List<OfflineProfile> sortedProfiles = profiles.stream().sorted(Comparator.comparingInt(OfflineProfile::getDeaths).reversed()).collect(Collectors.toList());

            sortedProfiles.forEach(offlineProfile -> {
                leaderboardFields.add(new LeaderboardField(offlineProfile.getRealName(), offlineProfile.getDeaths()));
            });
        }
        if(leaderboardType == LeaderboardType.KILLSTREAK){
            List<OfflineProfile> profiles = new ArrayList<>(Meetup.getInstance().getProfileManager().getOfflineProfiles().values());
            List<OfflineProfile> sortedProfiles = profiles.stream().sorted(Comparator.comparingInt(OfflineProfile::getHighestKillstreak).reversed()).collect(Collectors.toList());

            sortedProfiles.forEach(offlineProfile -> {
                leaderboardFields.add(new LeaderboardField(offlineProfile.getRealName(), offlineProfile.getHighestKillstreak()));
            });
        }
        if(leaderboardType == LeaderboardType.REROLLS){
            List<OfflineProfile> profiles = new ArrayList<>(Meetup.getInstance().getProfileManager().getOfflineProfiles().values());
            List<OfflineProfile> sortedProfiles = profiles.stream().sorted(Comparator.comparingInt(OfflineProfile::getRerolls).reversed()).collect(Collectors.toList());

            sortedProfiles.forEach(offlineProfile -> {
                leaderboardFields.add(new LeaderboardField(offlineProfile.getRealName(), offlineProfile.getRerolls()));
            });
        }
        if(leaderboardType == LeaderboardType.ELO){
            List<OfflineProfile> profiles = new ArrayList<>(Meetup.getInstance().getProfileManager().getOfflineProfiles().values());
            List<OfflineProfile> sortedProfiles = profiles.stream().sorted(Comparator.comparingInt(OfflineProfile::getElo).reversed()).collect(Collectors.toList());

            sortedProfiles.forEach(offlineProfile -> {
                leaderboardFields.add(new LeaderboardField(offlineProfile.getRealName(), offlineProfile.getElo()));
            });
        }
    }

    public int getValueByName(String name){
        return leaderboardFields.stream().filter(leaderboardField -> leaderboardField.getName().equalsIgnoreCase(name)).findFirst().orElse(null).getValue();
    }

    public int getPlaceByName(String name){
        return leaderboardFields.indexOf(leaderboardFields.stream().filter(leaderboardField -> leaderboardField.getName().equalsIgnoreCase(name)).findFirst().orElse(null)) + 1;
    }

    public List<LeaderboardField> getLeaderboardFields() {
        return leaderboardFields;
    }
}
