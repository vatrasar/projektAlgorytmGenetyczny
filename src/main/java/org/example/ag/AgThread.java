package org.example.ag;

import lombok.RequiredArgsConstructor;
import org.example.ag.AgSettings;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class AgThread extends Thread {

   final AgSettings agSettings;


    @Override
    public void run() {
        Logger.getGlobal().info("Ag starts");
        int numberOfPossibleResults=getNumberOfPossibleResults();

        final int chromosomeSize = getChromosomeSize(numberOfPossibleResults);
        int overflowSize=(int)(Math.pow(2,chromosomeSize)-numberOfPossibleResults);//how much more results can chromosome hold than we need in our case
        List<Random>radnomList=getRandomList();
        radnomList.forEach((random)->{
            AgSingleRun agSingleRun=new AgSingleRun(agSettings,1,chromosomeSize,overflowSize,random);
        });



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

}
