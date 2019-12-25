package org.example.controllers;

import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;
import java.util.logging.Logger;

public class ExportController {
    @Setter MainWindowControler mainWindowControler;
    List<List<Double>> statistic;

   public void saveRandomSeed()
   {
       DirectoryChooser fileChooser=new DirectoryChooser();
       fileChooser.setTitle("Wybór lokalizacji dla ziarna");
       File file=fileChooser.showDialog(new Stage());

       try {
           PrintWriter printWriter=new PrintWriter(file.getAbsolutePath()+"/ziarno");

           printWriter.write(""+mainWindowControler.seed);
           printWriter.close();
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       }
       Logger.getGlobal().info("Seed saved");

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
    }

}
