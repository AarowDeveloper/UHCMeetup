package me.aarow.astatine.utilities;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IntegerUtility {

    public static List<Integer> deserilize(String input){
        String[] args = input.split(",");
        return Stream.of(args).map(Integer::parseInt).collect(Collectors.toList());
    }
}
