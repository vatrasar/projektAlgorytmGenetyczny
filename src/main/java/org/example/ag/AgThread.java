package org.example.ag;

import lombok.RequiredArgsConstructor;
import org.example.ag.AgSettings;

import java.util.BitSet;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class AgThread extends Thread {

   final AgSettings agSettings;

    @Override
    public void run() {
        Logger.getGlobal().info("Ag starts");
        int numberOfPossibleResults=getNumberOfPossibleResults();
        int chromosomeSize= 0;
        try {
            chromosomeSize = getChromosomeSize(numberOfPossibleResults);
        } catch (Exception e) {
            e.printStackTrace();
        }
        AgSingleRun agSingleRun=new AgSingleRun(agSettings,1,chromosomeSize);
        agSingleRun.start();

    }

    private int getNumberOfPossibleResults() {
        return (int)Math.ceil((agSettings.functionType.max-agSettings.functionType.min)*(Math.pow(10,agSettings.precision)));
    }

    private int getChromosomeSize(int numberOfPossibleResults) throws Exception {
        throw new Exception();

    }
}
