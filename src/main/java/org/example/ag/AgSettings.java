package org.example.ag;

import lombok.Getter;
import lombok.Setter;
import org.example.ag.selection.Proportional;
import org.example.ag.selection.SelcetionType;
import org.example.ag.selection.TournamentHard;
import org.example.ag.selection.TournamentSoft;
import org.example.controllers.MainWindowControler;


@Getter
public class AgSettings {

     int generationsNumber;
     SelcetionType selectionType;
     @Setter FunctionType functionType;
     int funDimensional;
     double probTournamentWin;
     double probMutation;
     double probCross;


     int precision;
     int populationSize;
     long seed;
     int runsNumber;
     public void setPrecision(int precision) {
          this.precision = precision;
     }

     public void setRunsNumber(int runsNumber) throws Exception {
          isBigerThen(runsNumber,0,"Liczba uruchomień powinna być wieksza od 0");
          this.runsNumber=runsNumber;
     }

     public void setSeed(Long seed, MainWindowControler mainWindowController) throws Exception {
          mainWindowController.setSeed(seed);
          this.seed=seed;
     }

     void isBigerThen(double number, int limit, String exceptionComunicat) throws Exception {
          if(number<=limit)
          {
               throw new Exception(exceptionComunicat);
          }


     }

     public void setPopulationSize(int populationSize)throws Exception {
          isBigerThen(populationSize,1,"Wilkość populacji musi być większa od 1");
          this.populationSize = populationSize;

     }

     public void setGenerationsNumber(int generationsNumber) throws Exception {
          isBigerThen(generationsNumber,0,"Liczba generacji musi być większa od 0");
          this.generationsNumber = generationsNumber;
     }

     public void setSelectionType(String selectionType) {
          this.selectionType = determineSelectionType(selectionType) ;
     }

     public void setProbTournamentWin(double probTournamentWin) throws Exception {
          isBigerThen(probTournamentWin,0,"Prawdopodobieństwo wygrania turnieju musi byc wieksze od 0");

          if (probTournamentWin<=100){
               this.probTournamentWin = probTournamentWin;
          } else {
               throw new Exception("Wartości procentowe nie mogą przekraczać 100%");
          }
     }

     public void setProbMutation(double probMutation) throws Exception {
          isBigerThen(probMutation,0,"Prawdopodobieństwo mutacji musi byc wieksze od 0");
          if (probMutation<=100) {
               this.probMutation = probMutation;
          } else {
               throw new Exception("Wartości procentowe nie mogą przekraczać 100%");
          }
     }

     public void setProbCross(double probCross) throws Exception {
          isBigerThen(probCross,0,"Prawdopodobieństwo krzyżowania musi byc wieksze od 0");
          if (probCross<=100) {
               this.probCross = probCross;
          }
          else {
               throw new Exception("Wartości procentowe nie mogą przekraczać 100% i muszą byc większe od zera");
          }
     }



     public void setFunctionType(String functionType, int funDimensional) throws Exception {
          isBigerThen(funDimensional,0,"Wymiar funkcji musi mieć wartość conajmniej 1.");
          this.functionType=determineFunctionType(functionType);

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
     public void setFunctionDimensional(int dimensional) throws Exception {
          isBigerThen(dimensional,0,"Wymiar funkcji musi mieć wartość conajmniej 1.");
          this.funDimensional=dimensional;
     }
     private FunctionType determineFunctionType(String functionType) {
          switch (functionType)
          {
               case "Funkcja schodkowa":
                    return FunctionType.STEP_FUNCTION;
               case "Funkcja sferyczna":
                    return FunctionType.SPHERICAL_FUNCTION;
          }
          return FunctionType.SPHERICAL_FUNCTION;
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
