package org.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
            FXMLLoader fxmlLoader=getLoader("properties");

            VBox propertiesPane=new VBox((Parent)fxmlLoader.load());
            PropertiesController propertiesController=fxmlLoader.getController();
            propertiesController.setMainPane(mainPane);

            mainPane.getChildren().add(propertiesPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
