package org.example.ag;

import lombok.Getter;
import org.example.utils.NumericalUtils;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Chromosome {
    BitSet genotype;
    @Getter double score;//score in current generation
    int chromosomeSize;
    int overFlowSize;
    public Chromosome(int size,int overFlowSize,final Random rand) {
        chromosomeSize=size;
        this.genotype =getRandomBitSet(rand,size);

        this.overFlowSize=overFlowSize;
    }

    public Chromosome(Chromosome other,boolean isRandom,Random random) {
        if(isRandom)
            genotype=getRandomBitSet(random,other.chromosomeSize);
        else
            this.genotype = other.genotype;
        this.score = other.score;
        this.chromosomeSize = other.chromosomeSize;
        this.overFlowSize = other.overFlowSize;
    }

    private BitSet getRandomBitSet(final Random random, int size) {
        BitSet bitSet=new BitSet(chromosomeSize);
        IntStream.range(0,size).forEach(i->{
            boolean value=random.nextBoolean();
            bitSet.set(i,value);
        });
        return bitSet;
    }

    public void evalute(FunctionType functionType, int funDimensional,int prec) {

        List<Double>args =convertBitSetToArgs(funDimensional,functionType, prec);
        score=functionType.compute(args);
    }

    public List<Double> convertBitSetToArgs(int funDimensional, FunctionType functionType,int prec) {
        List<Integer>argsAsSolutionsNumbers=convertBitSetToIntegerElements(funDimensional);//there can be "overflow" numbers
        List<Integer>argsAsSolutionsNoOverFlow=mapNumbersToNoOverflow(argsAsSolutionsNumbers);

        return convertToRealFunctionArgs(argsAsSolutionsNoOverFlow,functionType,prec);
    }

    private List<Double> convertToRealFunctionArgs(List<Integer> argsAsSolutionsNoOverFlow, FunctionType functionType,int prec) {
        return argsAsSolutionsNoOverFlow.stream().map(arg->{
            return functionType.min+arg/Math.pow(10,prec);
        }).collect(Collectors.toList());
    }

    public List<Integer> mapNumbersToNoOverflow(List<Integer> argsAsSolutionsNumbers) {
        return argsAsSolutionsNumbers.stream().map(number->{
            if(number<overFlowSize*2)
            {
                if(number%2==0)
                {
                    return number;
                }
                else
                {
                    return number-1;
                }
            }
            else
                return number;
        }).collect(Collectors.toList());

    }

    private List<Integer> convertBitSetToIntegerElements(int funDimensional) {
        String genotypeString=NumericalUtils.bitSetToString(genotype,chromosomeSize);
        List<String>stringsGreyArgs=new ArrayList<>();
        int step=chromosomeSize/funDimensional;
        int start=0;
        int stop=step;
        for(int i=0;i<funDimensional;i++)
        {

            stringsGreyArgs.add(genotypeString.substring(start,stop));
            start=stop;
            stop+=step;

        }
        List<String>binArgs=stringsGreyArgs.stream().map(gray->NumericalUtils.greyCodeToBinary(gray,step)).collect(Collectors.toList());
        return binArgs.stream().map(bin->Integer.parseInt(bin,2)).collect(Collectors.toList());

    }
}
