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
import org.example.utils.WebListner;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ResultsController implements Initializable {

    @FXML
    WebView chart;

    WebListner listner;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        WebEngine engine =chart.getEngine();
        listner= new WebListner(engine);
        engine.getLoadWorker().stateProperty().addListener(listner);
    }
    public void loadDataToChart(List<List<Double>> statistics)
    {

        ObjectMapper objectMapper=new ObjectMapper();
        WebEngine engine =chart.getEngine();
        try {
            listner.setMessage(objectMapper.writeValueAsString(statistics));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        URL url2 = this.getClass().getResource("/chart/charts.html");



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
