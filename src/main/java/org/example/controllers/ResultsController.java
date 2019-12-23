package org.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class ResultsController implements Initializable {

    @FXML
    WebView chart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void loadDataToChart()
    {
        WebEngine engine =chart.getEngine();
        URL url2 = this.getClass().getResource("/chart/charts.html");
        engine.load(url2.toString());

    }
}
