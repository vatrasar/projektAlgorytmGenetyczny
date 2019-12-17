package org.example.utils;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class NumericalUtilsTest {

    @Test
    public void convertToGreyCode() {
        List<BitSet>testSet=new ArrayList<>();
        for(int i=0;i<8;i++){

            testSet.add(NumericalUtils.convertIntToBitSet(i,3));
        }



//        for(BitSet bitSet:testSet)
//        {
//           System.out.println(NumericalUtils.bitSetToString(bitSet,3));
//        }
//        System.out.println("GreyCode");
//        for(BitSet bitSet:testSet)
//        {
//            System.out.println(NumericalUtils.bitSetToString(NumericalUtils.convertToGreyCode(bitSet,3),3));
//        }

        BitSet number=NumericalUtils.convertToGreyCode(testSet.get(0),3);
        String numberAsString=NumericalUtils.bitSetToString(number,3);
        assertEquals(numberAsString,"000");
        number=NumericalUtils.convertToGreyCode(testSet.get(7),3);
        numberAsString=NumericalUtils.bitSetToString(number,3);
        assertEquals(numberAsString,"100");

        number=NumericalUtils.greyCodeToBinary(number,3);
        numberAsString=NumericalUtils.bitSetToString(number,3);
        assertEquals(numberAsString,"111");


    }

    @Test
    public void greyCodeToBinary() {
        List<BitSet>binary=new ArrayList<>();
        IntStream.range(0,8).forEach(i->{
            binary.add(NumericalUtils.convertIntToBitSet(i,3));
        });

        List<BitSet>bitGrey=binary.stream().map(a->NumericalUtils.convertToGreyCode(a,3)).collect(Collectors.toList());
        List<String>greyStrings=bitGrey.stream().map(a->NumericalUtils.bitSetToString(a,3)).collect(Collectors.toList());
        List<String>bin2Strings=greyStrings.stream().map(a->NumericalUtils.greyCodeToBinary(a,3)).collect(Collectors.toList());

        System.out.println(bin2Strings);
        assertEquals(bin2Strings.get(2),"010");
    }
}