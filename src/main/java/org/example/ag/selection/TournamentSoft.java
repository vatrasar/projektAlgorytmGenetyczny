package org.example.ag.selection;

import org.example.ag.Chromosome;
import org.example.ag.Population;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TournamentSoft implements SelcetionFun {
    SelectionType selectionType;
    int tournamentSize;
    double tournamentProb;
    @Override
    public SelectionType getSelectionType() {
        return selectionType;
    }

    @Override
    public Population getPopulationAfterSelection(Population inPoupulation, Random random) {


        List<Chromosome> populationList = new ArrayList<>();
        while (populationList.size()<inPoupulation.getPopulation().size())
        {
            List<Chromosome>tournamentPopulation = new ArrayList<>();
            IntStream.range(0,tournamentSize).forEach(i->{
                int number=random.nextInt(inPoupulation.getPopulation().size());
                tournamentPopulation.add(inPoupulation.getPopulation().get(number));
            });
            Chromosome winer=tournamentPopulation.stream().sorted((a,b)->{
                return Double.compare(b.getScore(),a.getScore());
            }).collect(Collectors.toList()).get(0);
            if(random.nextDouble()<tournamentProb/100)
                populationList.add(winer);
        }
        return new Population(populationList);

    }

    public TournamentSoft(int tournamentSize,double tournamentProb) {
        this.selectionType = SelectionType.TOURNAMENT_SOFT;
        this.tournamentSize=tournamentSize;
        this.tournamentProb=tournamentProb;
    }

    @Override
    public void setTournamentSize(int value) {
        this.tournamentSize=value;
    }

    @Override
    public void setTournamentProb(double value) {
        this.tournamentProb=value;
    }
}
