package org.example.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ResultsController implements Initializable {

    @FXML
    WebView chart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void loadDataToChart(List<List<Double>> statistics)
    {

        WebEngine engine =chart.getEngine();
       URL url2 = this.getClass().getResource("/chart/charts.html");
        engine.getLoadWorker().stateProperty().addListener(
                new ChangeListener<Worker.State>() {
                    public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                        ObjectMapper objectMapper=new ObjectMapper();

                        if (newState == Worker.State.SUCCEEDED) {
                            try {
                                engine.executeScript("makeChart(\""+objectMapper.writeValueAsString(statistics)+"\")");
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
       engine.setJavaScriptEnabled(true);
        engine.load(url2.toString());




    }

//    private void saveToFile(List<List<Double>> statistics) {
//
//
//
//        try {
//          new File("./chart/tmp/data.txt").createNewFile();
//            PrintWriter file=new PrintWriter("./chart/tmp/data.txt");
//            ObjectMapper objectMapper=new ObjectMapper();
//
//            file.write(objectMapper.writeValueAsString(statistics));
//            file.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
