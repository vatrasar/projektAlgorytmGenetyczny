package org.example.ag;


import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public enum  FunctionType {
    STEP_FUNCTION((values)->{
        double result=0;

        for(Double value : values)
        {
            result+=Math.floor(value);
        }
        result+=6*values.size();
        return result;
    },-5.12,5.12),
    SPHERICAL_FUNCTION((values)->{
        double result=0;
        for(Double value : values)
        {
            result+=Math.pow(value,2);
        }
        return result;
    },-5.12,5.12);



    private final Function<List<Double>,Double>function;

    public final double min,max;

    public double compute(List<Double>values)
    {
        return function.apply(values);
    }

    FunctionType(Function<List<Double>, Double> function,double min,double max) {
        this.function = function;
        this.min=min;
        this.max=max;
    }
}



