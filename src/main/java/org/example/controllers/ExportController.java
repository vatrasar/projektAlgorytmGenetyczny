package org.example.controllers;

import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import org.example.ag.AgStatistic;

import java.io.*;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ExportController {
    MainWindowControler mainWindowControler;
    List<List<Double>> statistic;

    public void setMainWindowControler(MainWindowControler mainWindowControler) {
        this.mainWindowControler = mainWindowControler;
    }

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
            List<List<Double>>generationsValues= AgStatistic.getGenerationsValues(statistic);
            List<Double>avgForGenerationsList=AgStatistic.getMeanForGenerations(generationsValues);


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



                List<Double>stdValues= AgStatistic.getStdForGenerations(generationsValues);
                try {
                    Files.delete(new File(file.getAbsolutePath()+"/data.txt").toPath());
                    printWriter = new PrintWriter(file.getAbsolutePath()+"/data.txt");
                    for(int i=0;i<avgForGenerationsList.size();i++)
                    {
                        printWriter.write((i+1)+" "+avgForGenerationsList.get(i)+" "+stdValues.get(i)+"\n");
                    }
                    printWriter.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
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
            URL url =this.getClass().getResource("/chartGnuplot/"+plotScriptName);
//            Files.copy(new File(url.getPath()).toPath(),new File(file.getPath()+"/"+plotScriptName).toPath());
            copyFileFromResources(file, plotScriptName, url);

        }
        catch (FileAlreadyExistsException e)
        {
            try {
                URL url =this.getClass().getResource("/chartGnuplot/"+plotScriptName);

                Files.delete(new File(file.getPath()+"/"+plotScriptName).toPath());
                copyFileFromResources(file, plotScriptName, url);
                //Files.copy(new File(url.getPath()).toPath(),new File(file.getPath()+"/"+plotScriptName).toPath());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void copyFileFromResources(File file, String plotScriptName, URL url) throws IOException {
        //test
//        URL url2 = this.getClass().getResource("/chart/charts.html");


        Files.createFile(new File(file.getPath()+"/"+plotScriptName).toPath());




        String pltFile;
        if(plotScriptName.equals("gnuplotScriptMultiRun.plt"))
        {
            pltFile="set style data lines\n" +
                    "set xlabel \"Iteracje\"\n" +
                    "set ylabel \"Średnia najlepsza wartosc funkcji\"\n" +
                    "#set key at 95,80\n" +
                    "#set label \"b=1.4, k=6\" at 5,110\n" +
                    "plot 'data.txt' using 1:2:3 with yerrorbars lc 4 title \"odchylenie standardowe\",\\\n" +
                    " 'data.txt' using 1:2 with lines lc 4 lw 3 title \"Średnia najlepsza wartości\",\\\n";
        }
        else
            pltFile="set style data lines\n" +
                    "set xlabel \"Iteracje\"\n" +
                    "set ylabel \"Najlepsza wartość funkcji\"\n" +
                    "#set key at 95,80\n" +
                    "#set label \"b=1.4, k=6\" at 5,110\n" +
                    "plot  'data.txt' using 1:2 with lines lc 4 lw 3 title \"wartości funkcji dla najlepszego chromosoma\"";
        PrintWriter printWriter=new PrintWriter (new File(file.getPath()+"/"+plotScriptName).toPath().toString());
        printWriter.write(pltFile);
        printWriter.close();
    }


}
