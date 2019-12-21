package org.example.ag;

import java.util.ArrayList;
import java.util.List;

public class AgStatistic {
    List<Double>bestScoreList;
    int generationNumber=0;

    public AgStatistic() {
        this.bestScoreList = new ArrayList<>();
    }

    public void uptdateStatistic(Double bestValue) {
        bestScoreList.add(bestValue);
        generationNumber++;
    }
}
