package org.example.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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

    @FXML
    Label resultLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        WebEngine engine =chart.getEngine();
        listner= new WebListner(engine);
        engine.getLoadWorker().stateProperty().addListener(listner);
    }
    public void loadDataToChart(List<List<Double>> statistics,int prec)
    {

        ObjectMapper objectMapper=new ObjectMapper();
        WebEngine engine =chart.getEngine();
        try {
            listner.setMessage(objectMapper.writeValueAsString(statistics));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        URL url2 = this.getClass().getResource("/chart/charts.html");
        String result="";
        if(statistics.size()>1) {
            result = "wartość %." + prec+"f" + " odchylenie standardowe %." + prec+"f";
            int genNumber = statistics.get(0).size();
            double last_mean = statistics.get(0).get(genNumber - 1);
            double std = statistics.get(2).get(genNumber - 1) - last_mean;
            resultLabel.setText(String.format(result, last_mean, std));
        }
        else {
            result = "wartość %." + prec+"f";
            int genNumber = statistics.get(0).size();
            double last_mean = statistics.get(0).get(genNumber - 1);

            resultLabel.setText(String.format(result, last_mean));
        }


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
