package org.example.ag;



import org.example.controllers.ProgressController;

import java.util.BitSet;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.logging.Logger;
import java.util.stream.IntStream;


public class AgSingleRun extends Thread {
    final AgSettings agSettings;
    final int runId;
    final int chromosomeSize;
    final int overFlowSize;
    final Random random;
    final AgStatistic agStatistic;
    final AgThread parentThread;

    public AgSingleRun(AgSettings agSettings, int runId, int chromosomeSize, int overFlowSize, Random random, AgStatistic agStatistic, AgThread parentThread) {
        this.agSettings = agSettings;
        this.runId = runId;
        this.chromosomeSize = chromosomeSize;
        this.overFlowSize = overFlowSize;
        this.random = random;
        this.agStatistic = agStatistic;
        this.parentThread = parentThread;
    }

    @Override
    public void run() {
        Logger.getGlobal().info(runId + " run has been started");
        Population population = new Population(agSettings.getPopulationSize(), chromosomeSize, overFlowSize, random);
        for (int i = 0; i < agSettings.generationsNumber; i++) {

            population.evaluation(agSettings.functionType, agSettings.getFunDimensional(), agSettings.precision);

            upadteStatistics(population, i);
            double mean=population.getMeanScore();
            population = agSettings.selectionType.getPopulationAfterSelection(population, random);
            mean=population.getMeanScore();

            population=population.crossOver(agSettings.probCross,random);


            population.mutation(agSettings.probMutation,random);

        }

        Logger.getGlobal().info("run "+runId+" end");


    }

    private void upadteStatistics(Population population, int i) {
        Chromosome bestChromosome=population.getBestChromosome();
        List<Double> bestArgs=bestChromosome.convertBitSetToArgs(agSettings.funDimensional,agSettings.functionType,agSettings.precision);
        Double bestValue=agSettings.functionType.compute(bestArgs);
//        if(bestValue>30)
//        {
//            Logger.getGlobal().info("biger than 30");
//        }
        System.out.println("best in "+i+" generation "+bestValue+" for");
        for(Double arg:bestArgs)
        {
            System.out.println("arg "+arg);
        }
        agStatistic.uptdateStatistic(bestValue);
        parentThread.updateProgressBar();
    }

}
