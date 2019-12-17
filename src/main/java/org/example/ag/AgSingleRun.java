package org.example.ag;

import lombok.RequiredArgsConstructor;

import java.util.BitSet;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class AgSingleRun extends Thread {
    final AgSettings agSettings;
    final int runId;
    final int chromosomeSize;
    final int overFlowSize;
    final Random random;
    @Override
    public void run() {
        Logger.getGlobal().info(runId+" run has been started");
        Population population=new Population(agSettings.getPopulationSize(),chromosomeSize,overFlowSize,random);
        IntStream.range(0,agSettings.generationsNumber).forEach(i->{
            population.evaluation(agSettings.functionType,agSettings.getFunDimensional(),agSettings.precision);
            
        });




    }
}
