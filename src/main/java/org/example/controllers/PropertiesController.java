package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.example.ag.AgSettings;
import org.example.ag.AgThread;
import org.example.ag.functions.FunctionType;
import org.example.utils.DataValidation;

import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;
import java.util.logging.Logger;


public class PropertiesController implements Initializable,Controller {
    @FXML
    TextField generationsNumberTextField;
    @FXML
    ChoiceBox selectionTypeChoiceBox;
    @FXML
    ChoiceBox functionTypeChoiceBox;

    @FXML
    TextField probCrossTextField;
    @FXML
    TextField probMutationTextField;

    @FXML
    Button nextButton;

    @FXML
    TextField populationSizeTextField;
    @FXML
    VBox propertiesPane;

    VBox nextPage=null;
    StackPane mainPane=null;


    public void setMainPane( StackPane mainPane) {
        this.mainPane = mainPane;
    }


    @FXML
    public void nextPropertiesPage()
    {
        try {
            AgSettings agSettings=readDataFromInputs();
            if(nextPage==null) {
                FXMLLoader fxmlLoader = getLoader("properties1");
                var a = fxmlLoader.load();
                nextPage = new VBox((Parent) a);
                PropertiesController1 propertiesController = fxmlLoader.getController();
                propertiesController.setMainPane(mainPane);
                propertiesController.setAgSettings(agSettings);

                propertiesController.setPerviousPage(propertiesPane);
            }
            mainPane.getChildren().clear();
            mainPane.getChildren().add(nextPage);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        catch (Exception e)
        {
            showAlertMessage(e);
        }

    }

    private AgSettings readDataFromInputs() throws Exception {
        String generationsNumber=generationsNumberTextField.getText();
        String selectionType =(String)selectionTypeChoiceBox.getValue();
        String functionType =(String)functionTypeChoiceBox.getValue();
        String probCross=probCrossTextField.getText();
        String probMutation=probMutationTextField.getText();
        AgSettings agSettings=new AgSettings();
        agSettings.setFunctionType(functionType,1);
        agSettings.setProbCross(Double.parseDouble(probCross));
        agSettings.setGenerationsNumber(Integer.parseInt(generationsNumber));
        agSettings.setProbMutation(Double.parseDouble(probMutation));
        agSettings.setSelectionType(selectionType);
        return agSettings;
    }

    public PropertiesController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setDefaultValues();
        setTextFieldsForNumbers();
        Tooltip tooltip=new Tooltip("Wartości w procentach");
        tooltip.setShowDelay(Duration.millis(10));
        Tooltip precisionTooltip=new Tooltip("Do którego miejsca po przecinku");
        precisionTooltip.setShowDelay(Duration.millis(10));

        probMutationTextField.setTooltip(tooltip);
        probCrossTextField.setTooltip(tooltip);




    }

    private void setTextFieldsForNumbers() {
        generationsNumberTextField.textProperty().addListener(new DataValidation(generationsNumberTextField,4,true));

        probCrossTextField.textProperty().addListener(new DataValidation(probCrossTextField,5,false));
        probMutationTextField.textProperty().addListener(new DataValidation(probMutationTextField,5,false));


    }

    private void setDefaultValues() {
        selectionTypeChoiceBox.setValue(selectionTypeChoiceBox.getItems().get(0));
        functionTypeChoiceBox.setValue(functionTypeChoiceBox.getItems().get(0));

        probCrossTextField.setText("70");
        probMutationTextField.setText("0.1");

        generationsNumberTextField.setText("100");

        populationSizeTextField.setText("2");
    }
}
