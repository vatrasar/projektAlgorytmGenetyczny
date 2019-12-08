package org.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Duration;
import org.example.ag.AgSettings;
import org.example.ag.functions.FunctionType;
import org.example.utils.DataValidation;

import java.net.URL;

import java.util.ResourceBundle;
import java.util.logging.Logger;


public class PropertiesController implements Initializable {
    @FXML
    TextField generationsNumberTextField;
    @FXML
    ChoiceBox selectionTypeChoiceBox;
    @FXML
    ChoiceBox functionTypeChoiceBox;
    @FXML
    TextField probTournamentWinTextField;

    @FXML
    TextField probCrossTextField;
    @FXML
    TextField probMutationTextField;
    @FXML
    TextField precisionTextField;
    @FXML
    Button startButton;
    @FXML
    public void startTest()
    {
        //get data from fields
        String generationsNumber=generationsNumberTextField.getText();
        String selectionType =(String)selectionTypeChoiceBox.getValue();
        String functionType =(String)functionTypeChoiceBox.getValue();
        String probTournamentWin=probTournamentWinTextField.getText();
        String probCross=probCrossTextField.getText();
        String probMutation=probMutationTextField.getText();
        String precisionText=precisionTextField.getText();
        try {
            AgSettings agSettings=new AgSettings(Integer.parseInt(generationsNumber),selectionType, functionType,Double.parseDouble(probTournamentWin),Double.parseDouble(probMutation),Double.parseDouble(probCross),Double.parseDouble(precisionText));
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Błąd danych");
            alert.setHeaderText("Błąd danych");
            alert.setContentText("Wartości procentowe nie mogą przekraczać 100%");

            alert.showAndWait();
        }
        Logger.getGlobal().info("Wprowadzono dane");


    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setDefaultValues();
        setTextFieldsForNumbers();
        Tooltip tooltip=new Tooltip("Wartości w procentach");
        tooltip.setShowDelay(Duration.millis(10));

        probTournamentWinTextField.setTooltip(tooltip);
        probMutationTextField.setTooltip(tooltip);
        probCrossTextField.setTooltip(tooltip);

    }

    private void setTextFieldsForNumbers() {
        generationsNumberTextField.textProperty().addListener(new DataValidation(generationsNumberTextField,4,true));
        precisionTextField.textProperty().addListener(new DataValidation(precisionTextField,5,false));
        probCrossTextField.textProperty().addListener(new DataValidation(probCrossTextField,5,false));
        probMutationTextField.textProperty().addListener(new DataValidation(probMutationTextField,5,false));
        probTournamentWinTextField.textProperty().addListener(new DataValidation(probTournamentWinTextField,5,false));
    }

    private void setDefaultValues() {
        selectionTypeChoiceBox.setValue(selectionTypeChoiceBox.getItems().get(0));
        functionTypeChoiceBox.setValue(functionTypeChoiceBox.getItems().get(0));
        precisionTextField.setText("0.1");
        probCrossTextField.setText("70");
        probMutationTextField.setText("0.1");
        probTournamentWinTextField.setText("80");
        generationsNumberTextField.setText("100");
    }
}
