package org.example.ag.selection;

import org.example.ag.Population;

import java.util.Random;

public class TournamentHard implements SelcetionFun {
    SelectionType selectionType;

    @Override
    public SelectionType getSelectionType() {
        return selectionType;
    }

    @Override
    public Population getPopulationAfterSelection(Population inPoupulation, Random random) {
        return null;
    }

    public TournamentHard() {
        this.selectionType = SelectionType.TOURNAMENT_HARD;
    }
}
