package com.strategy.strategyImpl;

import com.strategy.RankingStrategy;

import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StartTimeRankingStrategy implements RankingStrategy {
    @Override
    public List<String> rank(List<String> shows) {
        shows.sort(Comparator.comparing(StartTimeRankingStrategy::extractStartTime));

        return shows;
    }

    private static String extractStartTime(String event) {
        // Use regex to find the start time in the format (HH:mm-HH:mm)
        Pattern pattern = Pattern.compile("\\((\\d{2}:\\d{2})-\\d{2}:\\d{2}\\)");
        Matcher matcher = pattern.matcher(event);
        if (matcher.find()) {
            return matcher.group(1); // Return the start time
        }
        return "";
    }
}
