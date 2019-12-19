package org.example.ag.selection;

import org.example.ag.Chromosome;
import org.example.ag.Population;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Proportional implements SelcetionType {
    @Override
    public Population getPopulationAfterSelection(Population inPoupulation, Random random) {
        int size=inPoupulation.getPopulation().size();
        final double scoreSum=inPoupulation.getSumOfChromosomesScores();


        List<Double> chromosomesSurviveProbability=inPoupulation.getPopulation().stream().map(a->a.getScore()/scoreSum).collect(Collectors.toList());

        //if random number is in range chromosome survives
        List<Double>chromosomesRanges=getChromosomesRanges(chromosomesSurviveProbability);

        List<Chromosome>parentPopulation=new ArrayList<>();

        for(Chromosome chromosome:inPoupulation.getPopulation())
        {

            double randNumber=random.nextDouble();
            for(Double rangeRightBound:chromosomesRanges)
            {
                if(rangeRightBound>=randNumber)
                {
                    parentPopulation.add(inPoupulation.getPopulation().get(chromosomesRanges.indexOf(rangeRightBound)));
                    break;
                }
            }

        }
        return new Population(parentPopulation);
    }

    private List<Double> getChromosomesRanges(List<Double>chromosomesSurviveProbability) {
        double sum=0;
        List<Double>rangesRightBounds=new ArrayList<>();
        for (Double aDouble : chromosomesSurviveProbability) {
            sum += aDouble;
            rangesRightBounds.add(sum);
        }
        return rangesRightBounds;

    }
}
