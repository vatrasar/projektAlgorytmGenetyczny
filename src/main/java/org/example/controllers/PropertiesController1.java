package org.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Setter;
import org.example.ag.AgSettings;
import org.example.ag.AgThread;
import org.example.utils.DataValidation;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class PropertiesController1 implements Controller, Initializable {
    @FXML
    TextField probTournamentWinTextField;
    @FXML
    TextField precisionTextField;
    @FXML
    TextField funcDiminsionalTextField;

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
    TextField seedDirectoryTextField;
    @FXML
    Button nextButton;
    StackPane mainPane;
    @Setter VBox  perviousPage;
    AgSettings agSettings;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDefaultValues();
        setTextFieldsForNumbers();
        Tooltip tooltip=new Tooltip("Wartości w procentach");
        Tooltip precisionTooltip=new Tooltip("Do którego miejsca po przecinku");
        probTournamentWinTextField.setTooltip(tooltip);
        precisionTextField.setTooltip(precisionTooltip);
    }

    @FXML
    public void startTest()
    {
        //get data from fields

        String probTournamentWin=probTournamentWinTextField.getText();

        String precisionText=precisionTextField.getText();
        String fundimensional=funcDiminsionalTextField.getText();

        try {
            agSettings.setPrecision(Integer.parseInt(precisionText));
            agSettings.setFunctionDimensional(Integer.parseInt(fundimensional));
            agSettings.setProbTournamentWin(Double.parseDouble(probTournamentWin));
            Logger.getGlobal().info("Wprowadzono dane");
            AgThread watek=new AgThread(agSettings);
            watek.start();



        } catch (Exception e) {
            showAlertMessage(e);
        }


    }

    public void backToFirstPage()
    {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(perviousPage);
    }

    public void chooseSeedDirectory()
    {
        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Wybór lokalizacji ziarna");
        File file=fileChooser.showOpenDialog(new Stage());
        seedDirectoryTextField.setText(file.getAbsolutePath());


    }
    private void setTextFieldsForNumbers() {

        precisionTextField.textProperty().addListener(new DataValidation(precisionTextField,5,true));

        probTournamentWinTextField.textProperty().addListener(new DataValidation(probTournamentWinTextField,5,false));
        funcDiminsionalTextField.textProperty().addListener(new DataValidation(funcDiminsionalTextField,2,true));

    }
    private void setDefaultValues() {

        precisionTextField.setText("3");

        probTournamentWinTextField.setText("80");

        funcDiminsionalTextField.setText("1");

    }

    public void setMainPane(StackPane mainPane) {
        this.mainPane = mainPane;
    }

    public StackPane getMainPane() {
        return mainPane;
    }

    public void setAgSettings(AgSettings agSettings) {
        this.agSettings=agSettings;
    }
}
