package org.example.ag.selection;

import org.example.ag.Chromosome;
import org.example.ag.Population;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TournamentHard implements SelcetionFun {
    SelectionType selectionType;
    int tournamentSize;


    @Override
    public SelectionType getSelectionType() {
        return selectionType;
    }

    @Override
    public void setTournamentSize(int value) {
        tournamentSize=value;
    }

    @Override
    public void setTournamentProb(double value) {
        return;

    }

    @Override
    public Population getPopulationAfterSelection(Population inPoupulation, Random random) {
        List<Chromosome>populationList = new ArrayList<>();
        while (populationList.size()<inPoupulation.getPopulation().size())
        {
            List<Chromosome>tournamentPopulation = new ArrayList<>();
            IntStream.range(0,tournamentSize).forEach(i->{
                    int number=random.nextInt(inPoupulation.getPopulation().size());
                    tournamentPopulation.add(inPoupulation.getPopulation().get(number));
            });

           populationList.add(tournamentPopulation.stream().sorted((a,b)->{
               return Double.compare(b.getScore(),a.getScore());
            }).collect(Collectors.toList()).get(0));
        }
        return new Population(populationList);
    }

    public TournamentHard(int tournamentSize) {
        this.selectionType = SelectionType.TOURNAMENT_HARD;
        this.tournamentSize=tournamentSize;
    }
}
