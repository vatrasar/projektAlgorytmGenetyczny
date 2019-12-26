package org.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.util.Pair;
import lombok.Getter;
import lombok.Setter;
import org.example.controllers.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class MainWindowControler implements Initializable, Controller {

    @FXML
    StackPane mainPane;
    VBox propertiesPane,progressPane,resultsPane,exportPane;
    boolean isAfterTest;

    @FXML
    Button settingsButton;

    @FXML
    Button resultsButton;

    @FXML
    Button curvesButton;
    ResultsController resultsController;

    @Setter long seed;
    @Setter @Getter
    ExportController exportController;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            seed=new Random().nextLong();
            FXMLLoader fxmlLoaderProperties=getLoader("properties");
            FXMLLoader fxmlLoaderResults=getLoader("progress");
            FXMLLoader fxmlLoaderCharts=getLoader("results");
            FXMLLoader fxmlLoaderExport=getLoader("export");
            exportPane=new VBox((Parent)fxmlLoaderExport.load());
            progressPane=new VBox((Parent)fxmlLoaderResults.load());
            exportController=fxmlLoaderExport.getController();
            exportController.setMainWindowControler(this);
            propertiesPane=new VBox((Parent)fxmlLoaderProperties.load());
            resultsPane=new VBox((Parent)fxmlLoaderCharts.load());
            resultsController=fxmlLoaderCharts.getController();
            PropertiesController propertiesController=fxmlLoaderProperties.getController();
            ProgressController progressController=fxmlLoaderResults.getController();
            propertiesController.setMainPane(mainPane);
            propertiesController.setProgressPane(progressPane);
            propertiesController.setProgressController(progressController);
            propertiesController.setMenuButtons(Arrays.asList(settingsButton,resultsButton,curvesButton));
            propertiesController.setMainWindowController(this);
            isAfterTest=false;

            mainPane.getChildren().add(propertiesPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void resultsMenu()
    {
//        if(isAfterTest)
//            Logger.getGlobal().info("Brak widoku wynikowego");
//        else
//        {
//            mainPane.getChildren().clear();
//            mainPane.getChildren().add(progressPane);
//        }


        mainPane.getChildren().clear();
        mainPane.getChildren().add(resultsPane);

    }
    public void eksportMenu()
    {

        mainPane.getChildren().clear();
        mainPane.getChildren().add(exportPane);

    }
    public void propertiesMenu()
    {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(propertiesPane);
    }


}
