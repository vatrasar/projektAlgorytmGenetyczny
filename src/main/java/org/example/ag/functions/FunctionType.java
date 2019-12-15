package org.example.ag.functions;

import lombok.Setter;

public abstract class FunctionType {
    @Setter int funcDimensional;

    public FunctionType(int funcDimensional) {
        this.funcDimensional = funcDimensional;
    }

}



