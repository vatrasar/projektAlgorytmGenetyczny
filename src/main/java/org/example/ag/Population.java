package org.example.ag;

import javafx.util.Pair;
import lombok.Getter;
import org.example.ag.selection.SelcetionType;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        double highestScore=population.stream().mapToDouble(Chromosome::getScore).max().getAsDouble();//highest score is C in this generation
        population.forEach(chromosome->{
            chromosome.score=highestScore*1.1-chromosome.score;
        });

    }


    public double getSumOfChromosomesScores() {
        double scoreSum=0;
        for(Chromosome chromosome:population)
        {
            scoreSum+=chromosome.getScore();
        }
        return scoreSum;
    }

    public Population crossOver(double probCrossOver,Random random) {

        List<Chromosome>fertileChromosomes=getFertileChromosomes(probCrossOver,random);
        List<Chromosome>childrens=new ArrayList<>();
        List<Chromosome>parentsWithChildrens=new ArrayList<>();
        int targetSize=population.size();

        while (childrens.size()<targetSize)
        {
            Pair<Chromosome, Chromosome> parents=getParents(parentsWithChildrens,fertileChromosomes,random);
            Pair<Chromosome,Chromosome>twoChildrens=crossParents(parents,random);
            childrens.add(twoChildrens.getKey());
            childrens.add(twoChildrens.getValue());

        }
        while (childrens.size()>targetSize)
        {
            childrens.remove(0);
        }
        return new Population(childrens);
    }

    private List<Chromosome> getFertileChromosomes(double probCrossOver, Random random) {
        return population.stream().filter(a->random.nextDouble()<probCrossOver).collect(Collectors.toList());
    }

    private Pair<Chromosome, Chromosome> crossParents(Pair<Chromosome, Chromosome> parents, Random random) {
        int locus=random.nextInt(parents.getKey().chromosomeSize);

        Pair<Chromosome,Chromosome>childrens=new Pair<>(new Chromosome(parents.getKey(),true,random),new Chromosome(parents.getValue(),true,random));
        Chromosome brother=childrens.getKey();
        Chromosome sister=childrens.getValue();
        Chromosome mother=parents.getValue();
        Chromosome father=parents.getKey();
        for(int i:IntStream.range(0,locus).boxed().collect(Collectors.toList()))
        {

            brother.genotype.set(i,mother.genotype.get(i));
            sister.genotype.set(i,father.genotype.get(i));
        }
        for(int i:IntStream.range(locus,population.get(0).chromosomeSize).boxed().collect(Collectors.toList()))
        {
            brother.genotype.set(i,father.genotype.get(i));
            sister.genotype.set(i,mother.genotype.get(i));
        }
        return childrens;

    }

    private Pair<Chromosome,Chromosome> getParents(List<Chromosome> parentsWithChildrens, List<Chromosome> fertileChromosomes,Random random) {
        List<Chromosome> parents=new ArrayList<>();
        while (parents.size()!=2 && !fertileChromosomes.isEmpty())
        {
            parents.add(fertileChromosomes.get(0));
            parentsWithChildrens.add(fertileChromosomes.get(0));
            fertileChromosomes.remove(0);

        }
        while (parents.size()!=2 && !parentsWithChildrens.isEmpty())
        {
            parents.add(parentsWithChildrens.get(random.nextInt(parentsWithChildrens.size())));
        }
        while (parents.size()!=2)
        {
            parents.add(new Chromosome(population.get(0),true,random));
        }
        return new Pair<Chromosome,Chromosome>(parents.get(0),parents.get(1));
    }
}
