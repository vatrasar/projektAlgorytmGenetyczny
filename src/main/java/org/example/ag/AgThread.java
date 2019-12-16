package org.example.ag;

import lombok.RequiredArgsConstructor;
import org.example.ag.AgSettings;

import java.util.logging.Logger;

@RequiredArgsConstructor
public class AgThread extends Thread {

   final AgSettings agSettings;

    @Override
    public void run() {
        Logger.getGlobal().info("Ag starts");
        int chromosomeSize=computeChromosomeSize();
        AgSingleRun agSingleRun=new AgSingleRun(agSettings,1);
        agSingleRun.start();

    }

    private int computeChromosomeSize() {
        agSettings.
    }
}
