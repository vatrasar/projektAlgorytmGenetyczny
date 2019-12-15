package org.example.ag;

import javafx.css.Match;
import lombok.Setter;
import org.example.ag.functions.FunctionType;
import org.example.ag.functions.SphericalFunction;
import org.example.ag.functions.StepFunction;
import org.example.ag.selection.Proportional;
import org.example.ag.selection.SelcetionType;
import org.example.ag.selection.TournamentHard;
import org.example.ag.selection.TournamentSoft;


public class AgSettings {

     @Setter int generationsNumber;
     SelcetionType selectionType;
     @Setter FunctionType functionType;
     double probTournamentWin;
     double probMutation;
     double probCross;
     @Setter int precision;

     public void setSelectionType(String selectionType) {
          this.selectionType = determineSelectionType(selectionType) ;
     }

     public void setProbTournamentWin(double probTournamentWin) throws Exception {
          if (probTournamentWin<=100) {
               this.probTournamentWin = probTournamentWin;
          } else {
               throw new Exception("Wartości procentowe nie mogą przekraczać 100%");
          }
     }

     public void setProbMutation(double probMutation) throws Exception {
          if (probMutation<=100) {
               this.probMutation = probMutation;
          } else {
               throw new Exception("Wartości procentowe nie mogą przekraczać 100%");
          }
     }

     public void setProbCross(double probCross) throws Exception {

          if (probCross<=100) {
               this.probCross = probCross;
          }
          else {
               throw new Exception("Wartości procentowe nie mogą przekraczać 100%");
          }
     }



     public void setFunctionType(String functionType, int funDimensional) throws Exception {
          if (funDimensional!=0) {
               this.functionType=determineFunctionType(functionType);
          } else {
               throw new Exception("Wymiar funkcji musi mieć wartość conajmniej 1.");
          }
     }

     public AgSettings(int generationsNumber, String selectionType, String functionType, double probTournamentWin, double probMutation, double probCross, int precision, int funDimensional) throws Exception  {
          this.generationsNumber = generationsNumber;
          this.selectionType = determineSelectionType(selectionType);
          setFunctionType(functionType,funDimensional);
          setProbTournamentWin(probTournamentWin);
          setProbMutation(probMutation);
          setProbCross(probCross);
          this.precision = precision;


     }

     public AgSettings() {
     }
     public void setFunctionDimensional(int dimensional)
     {
          functionType.setFuncDimensional(dimensional);
     }
     private FunctionType determineFunctionType(String functionType) {
          switch (functionType)
          {
               case "Funkcja schodkowa":
                    return new StepFunction();
               case "Funkcja sferyczna":
                    return new SphericalFunction();
          }
          return new SphericalFunction();
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
