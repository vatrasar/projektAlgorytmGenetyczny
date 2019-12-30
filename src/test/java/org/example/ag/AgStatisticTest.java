package org.example.ag;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class AgStatisticTest {

    @Test
    public void getStdForGenerations() {
         Double[]gen1={23.,23.,1.,6.18,9.};
        Double[]gen2={1.6,3.6,45.3,6.1,9.};
        Double[]gen3={7.,16.,14.,15.,9.};
        Double[]gen4={24.,13.,16.,9.,9.};
        List<List<Double>>datas=new ArrayList<>();
        datas.add(Arrays.asList(gen1));
        datas.add(Arrays.asList(gen2));
        datas.add(Arrays.asList(gen3));
        datas.add(Arrays.asList(gen4));
        List<Double>mean=AgStatistic.getMeanForGenerations(datas);
        List<Double>std=AgStatistic.getStdForGenerations(datas);
        assert mean.size()==4;
        assert Math.abs(12.436-mean.get(0))<0.001;
        assert Math.abs(8.999-std.get(0))<0.001;
    }

   
}