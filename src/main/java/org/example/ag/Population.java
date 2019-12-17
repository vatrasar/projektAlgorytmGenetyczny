package org.example.ag;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;

public class Population {
    List<Chromosome> population;


    public Population(int populationSize,int chomosomeSize,int overFlowSize,Random random) {
        this.population=new ArrayList<>();


        for (int i = 0; i < populationSize; i++)
        {
            population.add(new Chromosome(chomosomeSize,overFlowSize,random));
        }

    }

    public Population(List<Chromosome> population) {
        this.population = population;

    }

    public void evaluation(FunctionType functionType, int funDimensional,int prec) {
        for(Chromosome chromosome:population)
        {
            chromosome.evalute(functionType,funDimensional,prec);
        }
    }
}
