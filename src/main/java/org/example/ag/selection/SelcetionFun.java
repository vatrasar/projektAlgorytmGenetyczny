package org.example.ag.selection;

import org.example.ag.Population;

import java.util.Random;

public interface SelcetionFun {
    public Population getPopulationAfterSelection(Population inPoupulation,Random random);
    public SelectionType getSelectionType();
}
