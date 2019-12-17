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
        double scoreSum=0;
        for(Chromosome chromosome:inPoupulation.getPopulation())
        {
            scoreSum+=chromosome.getScore();
        }
        final double scoreSumFinal=scoreSum;
        List<Double> chromosomesSurviveProbability=inPoupulation.getPopulation().stream().map(a->a.getScore()/scoreSumFinal).collect(Collectors.toList());
        List<Chromosome>parentPopulation=new ArrayList<>();
        for(Chromosome chromosome:inPoupulation.getPopulation())
        {
            double sum=0;
            double randNumber=random.nextDouble();
            for (int i=0;i<chromosomesSurviveProbability.size();i++)
            {
                sum+=chromosomesSurviveProbability.get(i);
                if(sum>=randNumber)
                {
                    parentPopulation.add(inPoupulation.getPopulation().get(i));
                    break;
                }

            }
        }
        return new Population(parentPopulation);
    }
}
