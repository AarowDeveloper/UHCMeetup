package me.aarow.astatine.scenario;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ScenarioManager {

    public List<Scenario> getMostVoted(){
        List<Scenario> mostVoted = Stream.of(Scenario.values()).sorted(Comparator.comparingInt(Scenario::getVotes).reversed()).collect(Collectors.toList());
        return mostVoted;
    }


    public boolean isThereTopScenario(){
        for(Scenario scenario : Scenario.values()){
            if(scenario.getVotes() > 0){
                return true;
            }
        }
        return false;
    }
}
