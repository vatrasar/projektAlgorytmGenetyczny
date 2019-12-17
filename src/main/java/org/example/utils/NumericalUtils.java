package org.example.utils;

import java.math.BigDecimal;
import java.util.BitSet;

public class NumericalUtils {
    static BitSet convertToGreyCode(BitSet number,int size)
    {
        BitSet resultNumber=new BitSet(size);
        resultNumber.set(0,number.get(0));
        for(int i=1;i<size;i++)
        {

          resultNumber.set(i,xor(number.get(i),number.get(i-1)));
        }
        return resultNumber;

    }
    static boolean xor(boolean a,boolean b)
    {
         return !(a==b);

    }
    static char xor(char a,char b)
    {
        if(!(a==b))
        {
            return '1';
        }
        else
            return '0';

    }
    public static BitSet convertIntToBitSet(int number, int bitSetSize)
    {
        return convertStringToBitSet(Integer.toBinaryString(number),bitSetSize);

    }
    public static BitSet convertStringToBitSet(String binary,int setSize)
    {
        if(binary.length()<setSize)
        {
            int count=setSize-binary.length();
            StringBuilder strB=new StringBuilder("0".repeat(count));
            binary=strB.append(binary).toString();
        }
        BitSet bitSet=new BitSet(setSize);
        for(int i=0;i<setSize;i++)
        {


            if(binary.charAt(i)=='1')
            {
                bitSet.set(i);
            }

        }
        return bitSet;
    }

    public static String bitSetToString(BitSet bitSet,int size)
    {
        StringBuilder result=new StringBuilder("");
        for(int i=0;i<size;i++)
        {
            if(bitSet.get(i))
                result.append("1");
            else
                result.append("0");
        }
        return result.toString();
    }
    public static BitSet greyCodeToBinary(BitSet grayCode,int size)
    {
        BitSet binary=new BitSet(size);
        binary.set(0,grayCode.get(0));
        for(int i=1;i<size;i++)
        {

            binary.set(i,xor(grayCode.get(i),binary.get(i-1)));
        }
        return binary;
    }

    public static String greyCodeToBinary(String grayCode,int size)
    {
        StringBuilder binary=new StringBuilder("0".repeat(size));
        binary.setCharAt(0,grayCode.charAt(0));
        for(int i=1;i<size;i++)
        {

            binary.setCharAt(i,xor(grayCode.charAt(i),binary.charAt(i-1)));
        }
        return binary.toString();
    }

}
