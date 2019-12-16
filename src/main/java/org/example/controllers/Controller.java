package org.example.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import org.example.App;

import java.io.IOException;
import java.util.logging.Logger;

public interface Controller {
    default  FXMLLoader getLoader(String fxml) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("templatki/"+fxml + ".fxml"));
        return fxmlLoader;
    }
    default public void showAlertMessage(Exception e) {
        Logger.getGlobal().info("Błąd danych");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Błąd danych");
        alert.setHeaderText("Błąd danych");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

}
