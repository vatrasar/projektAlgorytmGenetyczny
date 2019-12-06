package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.util.logging.Logger;

public class PropertiesController {

    ChoiceBox choiceBox;
    @FXML
    public void startTest()
    {
        Logger.getGlobal().info("Test starts");

    }
}
