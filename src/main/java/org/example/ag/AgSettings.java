package org.example.ag;

import javafx.css.Match;
import org.example.ag.functions.FunctionType;
import org.example.ag.functions.SphericalFunction;
import org.example.ag.functions.StepFunction;
import org.example.ag.selection.Proportional;
import org.example.ag.selection.SelcetionType;
import org.example.ag.selection.TournamentHard;
import org.example.ag.selection.TournamentSoft;


public class AgSettings {

     int generationsNumber;
     SelcetionType selectionType;
     FunctionType functionType;
     double probTournamentWin;
     double probMutation;
     double probCross;
     int precision;


     public AgSettings(int generationsNumber, String selectionType, String functionType, double probTournamentWin, double probMutation, double probCross, int precision,int funDimensional) throws Exception  {
          this.generationsNumber = generationsNumber;
          this.selectionType = determineSelectionType(selectionType);
          this.functionType = determineFunctionType(functionType,funDimensional);
          this.probTournamentWin = probTournamentWin/100;
          this.probMutation = probMutation/100;
          this.probCross = probCross/100;
          this.precision = precision;
          if(this.probCross>1 || this.probTournamentWin>1 || this.probMutation>1)
          {
               throw new Exception("Wartości procentowe nie mogą przekraczać 100%");
          }
          if(funDimensional==0)
          {
               throw new Exception("Wymiar funkcji musi mieć wartość conajmniej 1.");
          }
     }

     private FunctionType determineFunctionType(String functionType,int dimensional ) {
          switch (functionType)
          {
               case "Funkcja schodkowa":
                    return new StepFunction(dimensional);
               case "Funkcja sferyczna":
                    return new SphericalFunction(dimensional);
          }
          return new SphericalFunction(dimensional);
     }

     private SelcetionType determineSelectionType(String selectionType) {
          switch (selectionType)
          {
               case"Proporcjonalna":
                    return new Proportional();
               case"Turniejowa miękka":
                    return new TournamentSoft();
               case "Turniejowa twarda":
                    return new TournamentHard();
          }
          return new Proportional();
     }
}
