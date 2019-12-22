package org.example.ag;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.tools.Statistics;
import javafx.concurrent.Task;
import lombok.RequiredArgsConstructor;
import org.example.ag.AgSettings;
import org.example.controllers.ProgressController;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class AgThread extends Task<Double> {

   final AgSettings agSettings;

    List<AgStatistic>agStatisticList;

    @Override
    public Double call() {
        Logger.getGlobal().info("Ag starts");
        int numberOfPossibleResults=getNumberOfPossibleResults();

        final int chromosomeSize = getChromosomeSize(numberOfPossibleResults);
        int overflowSize=(int)(Math.pow(2,chromosomeSize)-numberOfPossibleResults);//how much more results can chromosome hold than we need in our case
        List<Random>radnomList=getRandomList();
        agStatisticList=getStatisticList();
        IntStream.range(0,radnomList.size()).forEach(i->{
            AgSingleRun agSingleRun=new AgSingleRun(agSettings,1,chromosomeSize,overflowSize,radnomList.get(i),agStatisticList.get(i),this);

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

        
        return 2.2;


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

    private int getNumberOfPossibleResults() {
        return (int)(Math.ceil((agSettings.functionType.max-agSettings.functionType.min)*(Math.pow(10,agSettings.precision))))*agSettings.funDimensional;
    }

    private int getChromosomeSize(int numberOfPossibleResults) {

        int chromosomeSize=1;
        while(Math.pow(2,chromosomeSize)<numberOfPossibleResults)
        {
            chromosomeSize+=1;
        }

        return chromosomeSize;


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
