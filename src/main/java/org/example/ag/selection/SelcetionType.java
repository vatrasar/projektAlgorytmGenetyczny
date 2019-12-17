package org.example.ag.selection;

import org.example.ag.Population;

import java.util.Random;

public interface SelcetionType {
    public Population getPopulationAfterSelection(Population inPoupulation,Random random);

}
