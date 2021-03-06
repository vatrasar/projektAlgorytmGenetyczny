package org.example.ag;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    public static List<Double> getStdForGenerations(List<List<Double>> generationsValues) {
        List<Double>standardDeviationForGenarationsList=new ArrayList<>();
        List<Double>means=getMeanForGenerations(generationsValues);
        for(int p=0;p<generationsValues.size();p++) {
            List<Double> generation=generationsValues.get(p);
//            double[] generationArray=toDoubleArray(generation);
//            StandardDeviation standardDeviation = new StandardDeviation();
//            standardDeviationForGenarationsList.add(standardDeviation.evaluate(generationArray));
            double acum=0;
            for(int i=0;i<generation.size();i++)
            {
                double dev=Math.pow(means.get(p)-generation.get(i),2);
                acum+=dev;

            }
            acum/=generation.size();
            acum=Math.sqrt(acum);
            standardDeviationForGenarationsList.add(acum);

        }
        return standardDeviationForGenarationsList;
    }

    public static List<Double> getMeanForGenerations(List<List<Double>> valuesForGenerations) {
        List<Double> meanForGenerations=new ArrayList<>();
        for(List<Double>generationValues:valuesForGenerations)
        {
            double avg=0;
            for(double values:generationValues)
            {
                avg+=values;
            }
            avg/=generationValues.size();
            meanForGenerations.add(avg);
        }
        return meanForGenerations;
    }

    public static double[] toDoubleArray(List<Double> generation) {
        double[] array = new double[generation.size()];
        for (int i = 0; i < generation.size(); i++) {
            array[i] = generation.get(i);

        }
        return array;
    }

    public static List<List<Double>> getGenerationsValues(List<List<Double>>runsValues)
    {
        List<List<Double>> generationsValues = new ArrayList<>();

        for (int i : IntStream.range(0, runsValues.get(0).size()).boxed().collect(Collectors.toList())) {
            generationsValues.add(runsValues.stream().map(run -> run.get(i)).collect(Collectors.toList()));
        }


        return generationsValues;
    }
}
