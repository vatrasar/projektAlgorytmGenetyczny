package org.example.ag;

import lombok.RequiredArgsConstructor;

import java.util.BitSet;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class AgSingleRun extends Thread {
    final AgSettings agSettings;
    final int runId;
    final int chromosomeSize;
    @Override
    public void run() {
        Logger.getGlobal().info(runId+" run has been started");
        Population population=new Population(agSettings.getPopulationSize(),chromosomeSize);


        for(int i=0;i<agSettings.generationsNumber;i++)
        {

        }

    }
}
