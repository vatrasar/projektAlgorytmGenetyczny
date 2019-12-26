package org.example.controllers;

import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Setter;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import java.io.*;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ExportController {
    @Setter MainWindowControler mainWindowControler;
    List<List<Double>> statistic;

   public void saveRandomSeed()
   {
       File file = getDirectory();
       if(file==null)
       {
           return;
       }

       try {
           PrintWriter printWriter=new PrintWriter(file.getAbsolutePath()+"/ziarno");

           printWriter.write(""+mainWindowControler.seed);
           printWriter.close();
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       }
       Logger.getGlobal().info("Seed saved");

   }

    private File getDirectory() {
        DirectoryChooser fileChooser=new DirectoryChooser();
        fileChooser.setTitle("Wybór lokalizacji dla ziarna");
        return fileChooser.showDialog(new Stage());
    }

    public void exportToGnuplot()
    {
        if(statistic==null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Brak danych");
            alert.setHeaderText("Brak danych");
            alert.setContentText("Przed eksportem do gnuplota musisz najpierw\nuruchomić algorytm genetyczny");
            alert.showAndWait();
        }
        else{
            File file = getDirectory();
            if(file==null)
            {
                return;
            }
            String plotScriptName="";
            List<Double>avgForGenerationsList=new ArrayList<>();

            getMeanForGenerations(avgForGenerationsList);
            PrintWriter printWriter= null;
            if(statistic.size()==1)
            {
                plotScriptName="gnuplotScriptSingleRun.plt";


                try {
                    printWriter = new PrintWriter(file.getAbsolutePath()+"/data.txt");
                    for(int i=0;i<avgForGenerationsList.size();i++)
                    {
                        printWriter.write((i+1)+" "+avgForGenerationsList.get(i)+"\n");
                    }
                    printWriter.close();



                }
                 catch (IOException e) {
                    e.printStackTrace();
                }

            }else
            {
                plotScriptName="gnuplotScriptMultiRun.plt";

                List<Double>standardDeviationForGenarationsList=new ArrayList<>();
                List<List<Double>>generationsValues=getGenerationsValues();
                getStdForGenerations(standardDeviationForGenarationsList, generationsValues);
                try {
                    printWriter = new PrintWriter(file.getAbsolutePath()+"/data.txt");
                    for(int i=0;i<avgForGenerationsList.size();i++)
                    {
                        printWriter.write((i+1)+" "+avgForGenerationsList.get(i)+" "+standardDeviationForGenarationsList.get(i)+"\n");
                    }
                    printWriter.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


            }

            Logger.getGlobal().info("Data saved");
            copyGnuPlotScript(file, plotScriptName);
            Logger.getGlobal().info("Gnuplot Exported");

        }
    }

    private void copyGnuPlotScript(File file, String plotScriptName) {
        try {
            URL url =getClass().getResource("/chartGnuplot/"+plotScriptName);
            Files.copy(new File(url.getPath()).toPath(),new File(file.getPath()+"/"+plotScriptName).toPath());


        }
        catch (FileAlreadyExistsException e)
        {
            try {
                URL url =getClass().getResource("/chartGnuplot/"+plotScriptName);
                Files.delete(new File(file.getPath()+"/"+plotScriptName).toPath());
                Files.copy(new File(url.getPath()).toPath(),new File(file.getPath()+"/"+plotScriptName).toPath());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getStdForGenerations(List<Double> standardDeviationForGenarationsList, List<List<Double>> generationsValues) {
        for(List<Double> generation:generationsValues) {
            double[] generationArray=toDoubleArray(generation);
            StandardDeviation standardDeviation = new StandardDeviation();
            standardDeviationForGenarationsList.add(standardDeviation.evaluate(generationArray));

        }
    }

    private void getMeanForGenerations(List<Double> avgForGenerationsList) {
        for(int i: IntStream.range(0,statistic.get(0).size()).boxed().collect(Collectors.toList()))
        {

            double iAvg=0;
            for (List<Double>run :statistic)
            {
                iAvg+=run.get(i);
            }
            iAvg/=statistic.size();
            avgForGenerationsList.add(iAvg);
        }
    }

    private double[] toDoubleArray(List<Double> generation) {
        double[] array = new double[generation.size()];
        for (int i = 0; i < generation.size(); i++) {
            array[i] = generation.get(i);

        }
        return array;
    }

    private List<List<Double>> getGenerationsValues()
    {
        List<List<Double>> generationsValues = new ArrayList<>();

        for (int i : IntStream.range(0, statistic.get(0).size()).boxed().collect(Collectors.toList())) {
            generationsValues.add(statistic.stream().map(run -> run.get(i)).collect(Collectors.toList()));
        }


        return generationsValues;
    }
}
