package org.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.example.controllers.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowControler implements Initializable, Controller {

    @FXML
    StackPane mainPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Pane propertiesPane=new Pane(loadFXML("properties"));
            mainPane.getChildren().add(propertiesPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
