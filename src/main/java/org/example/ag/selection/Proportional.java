package org.example.ag.selection;

import org.example.ag.Chromosome;
import org.example.ag.Population;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Proportional implements SelcetionFun {

    SelectionType selectionType;
    @Override
    public SelectionType getSelectionType() {
        return selectionType;
    }

    public Proportional() {
        this.selectionType = SelectionType.PROPORTIONAL;
    }

    @Override
    public void setTournamentSize(int value) {
        return;
    }

    @Override
    public void setTournamentProb(double value) {
        return;
    }

    @Override
    public Population getPopulationAfterSelection(Population inPoupulation, Random random) {
        long start=System.currentTimeMillis();
        int size=inPoupulation.getPopulation().size();
        final double scoreSum=inPoupulation.getSumOfChromosomesScores();


        List<Double> chromosomesSurviveProbability=inPoupulation.getPopulation().stream().map(a->a.getScore()/scoreSum).collect(Collectors.toList());

        //if random number is in range chromosome survives
        List<Double>chromosomesRanges=getChromosomesRanges(chromosomesSurviveProbability);

        List<Chromosome>parentPopulation=new ArrayList<>();
        if(scoreSum==0)
        {
            for(Chromosome chromosome:inPoupulation.getPopulation()) {


                for (Double rangeRightBound : chromosomesRanges) {

                    parentPopulation.add(new Chromosome(chromosome, true, random));
                    break;
                }

            }
            return new Population(parentPopulation);
        }
        else {
            int i = 0;
            long time = System.currentTimeMillis() - start;

            for (Chromosome chromosome : inPoupulation.getPopulation()) {

                double randNumber = random.nextDouble();
                for (Double rangeRightBound : chromosomesRanges) {
                    if (rangeRightBound >= randNumber) {
                        parentPopulation.add(inPoupulation.getPopulation().get(chromosomesRanges.indexOf(rangeRightBound)));
                        break;
                    }

                }
                if (i >= parentPopulation.size())
                    System.out.println(randNumber);
                i++;
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
