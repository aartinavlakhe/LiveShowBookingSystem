package com.strategy;

import java.util.List;

public interface RankingStrategy {
    List<String> rank(List<String> shows);
}
