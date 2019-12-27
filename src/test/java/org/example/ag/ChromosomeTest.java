package org.example.ag;

import org.junit.Test;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class ChromosomeTest {


    @Test
    public void evalute() {
        Chromosome chromosome=new Chromosome(8,135,new Random());//assume that is 11 possible values


    }

    @Test
    public void mapNumbersToNoOverflow() {


        Chromosome chromosome=new Chromosome(8,5,new Random());//assume that is 11 possible values
        List<Integer>result=chromosome.mapNumbersToNoOverflow(IntStream.range(0,20).boxed().collect(Collectors.toList()));
        assertEquals((long)result.get(19),19);
        assertEquals((long)result.get(1),0);
    }
    @Test
    public void convertToRealFunctionArgs()
    {

        double a=3/Math.pow(10,1)+0.074;
        double b=(double) Math.round(a*Math.pow(10,3));
        System.out.println(a);
    }

    @Test
    public void convertBitSetToArgs() {

        Chromosome chromosome=new Chromosome(14,6144,new Random());
        chromosome.convertBitSetToArgs(1,FunctionType.SPHERICAL_FUNCTION,3);
        while (chromosome.chrmosomeArgs.get(0)<5.14)
        {
            chromosome=new Chromosome(14,6144,new Random());
            chromosome.convertBitSetToArgs(1,FunctionType.SPHERICAL_FUNCTION,3);
        }
        Logger.getGlobal().info("Jest");
        chromosome.convertBitSetToArgs(1,FunctionType.SPHERICAL_FUNCTION,3);
    }
}