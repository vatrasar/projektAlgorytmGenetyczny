package org.example.ag;

import lombok.Getter;
import org.example.ag.selection.SelcetionType;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
@Getter
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
    public Population() {
        this.population = new ArrayList<>();

    }

    public void evaluation(FunctionType functionType, int funDimensional,int prec) {
        for(Chromosome chromosome:population)
        {
            chromosome.evalute(functionType,funDimensional,prec);
        }
    }


}
