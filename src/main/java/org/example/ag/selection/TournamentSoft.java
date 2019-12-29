package org.example.ag.selection;

import org.example.ag.Population;

import java.util.Random;

public class TournamentSoft implements SelcetionFun {
    SelectionType selectionType;
    int tournamentSize;
    double tournamentProb;
    @Override
    public SelectionType getSelectionType() {
        return selectionType;
    }

    @Override
    public Population getPopulationAfterSelection(Population inPoupulation, Random random) {
        return null;
    }

    public TournamentSoft(int tournamentSize,double tournamentProb) {
        this.selectionType = SelectionType.TOURNAMENT_SOFT;
        this.tournamentSize=tournamentSize;
        this.tournamentProb=tournamentProb;
    }

    @Override
    public void setTournamentSize(int value) {
        this.tournamentSize=value;
    }

    @Override
    public void setTournamentProb(double value) {
        this.tournamentProb=value;
    }
}
