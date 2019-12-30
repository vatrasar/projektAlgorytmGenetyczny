package org.example.ag;


import javafx.concurrent.Task;


import java.util.ArrayList;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class AgThread extends Task<List<List<Double>>> {

   final AgSettings agSettings;

    List<AgStatistic>agStatisticList;

    public AgThread(AgSettings agSettings) {
        this.agSettings = agSettings;
    }

    @Override
    public List<List<Double>> call() {
        Logger.getGlobal().info("Ag starts");
        double numberOfPossibleResults=getNumberOfPossibleResults();

        final int chromosomeSize = getChromosomeSize(numberOfPossibleResults);


        int overflowSize=(int)(Math.pow(2,chromosomeSize/agSettings.funDimensional)-numberOfPossibleResults);//how much more results can chromosome hold than we need in our case
        List<Random>radnomList=getRandomList();
        agStatisticList=getStatisticList();
        IntStream.range(0,radnomList.size()).forEach(i->{
            AgSingleRun agSingleRun=new AgSingleRun(agSettings,i+1,chromosomeSize,overflowSize,radnomList.get(i),agStatisticList.get(i),this);

            agSingleRun.start();
        });
        int sumOfGenerations= getTargetSumOfGenerations();

        int finishedGenerations = getFinishedGenerations();
        while (sumOfGenerations!=finishedGenerations)
        {
            updateProgress(((double) finishedGenerations)/sumOfGenerations,1);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sumOfGenerations= getTargetSumOfGenerations();

            finishedGenerations = getFinishedGenerations();

            updateProgress(((double)finishedGenerations)/sumOfGenerations,1.0);
        }

        
        return agStatisticList.stream().map(stat->stat.bestScoreList).collect(Collectors.toList());


    }

    private List<AgStatistic> getStatisticList() {
        List<AgStatistic>statisticList=new ArrayList<>();
        IntStream.range(0,agSettings.runsNumber).forEach(a->{
                statisticList.add(new AgStatistic());
        });
        return statisticList;
    }

    private List<Random> getRandomList() {
        List<Random>randomList=new ArrayList<>();
        Random randomMother = new Random(agSettings.seed);

        IntStream.range(0,agSettings.runsNumber).forEach(i->{
            randomList.add(new Random(randomMother.nextInt()));
        });

        return randomList;

    }

    /**
     * dimensionals concerned here
     * @return
     */
    private double getNumberOfPossibleResults() {
        double range=agSettings.functionType.max-agSettings.functionType.min;
        double prec =Math.pow(10,agSettings.precision);
        return prec*range;

    }

    private int getChromosomeSize(double numberOfPossibleResults) {

        int chromosomeSize=1;
        while(Math.pow(2,chromosomeSize)<Math.ceil(numberOfPossibleResults))
        {
            chromosomeSize+=1;
        }

        return chromosomeSize*agSettings.funDimensional;


    }


    public  void updateProgressBar() {

    }

    private int getFinishedGenerations() {
        int finishedGenerations=0;
        for(AgStatistic stat:agStatisticList)
        {
            finishedGenerations+=stat.generationNumber;
        }
        return finishedGenerations;
    }

    private int getTargetSumOfGenerations() {
        return agSettings.runsNumber*agSettings.generationsNumber;
    }
}
