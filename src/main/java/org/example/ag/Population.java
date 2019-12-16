package org.example.ag;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class Population {
    List<Chromosome> population;

    public Population(int populationSize,int chomosomeSize) {
        this.population=new ArrayList<>();
        for (int i = 0; i < populationSize; i++)
        {


        }

    }

    public Population(List<Chromosome> population) {
        this.population = population;

    }
}
