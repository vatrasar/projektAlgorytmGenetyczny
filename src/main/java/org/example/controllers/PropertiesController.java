package org.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.example.ag.AgSettings;
import org.example.ag.selection.SelectionType;
import org.example.utils.DataValidation;

import java.io.IOException;
import java.net.URL;

import java.util.List;
import java.util.ResourceBundle;


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
    PropertiesController1 nextPageController;

    StackPane mainPane=null;
    VBox progressPane;
    private List<Button> menuButtonsList;
    private ProgressController progressController;
    private MainWindowControler mainWindowController;


    public void setMainPane( StackPane mainPane) {
        this.mainPane = mainPane;
    }
    public void setProgressPane(VBox progressPane)
    {
        this.progressPane=progressPane;
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
                nextPageController = fxmlLoader.getController();
                nextPageController.setMainPane(mainPane);
                nextPageController.setAgSettings(agSettings);
                nextPageController.setMenuButonsList(menuButtonsList);

                nextPageController.setProgressController(progressController);
                nextPageController.setMainWindowController(mainWindowController);


            }
            else
                nextPageController.setAgSettings(agSettings);
            nextPageController.setProgressPane(progressPane);
            mainPane.getChildren().clear();
            mainPane.getChildren().add(nextPage);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        catch (Exception e)
        {
            showAlertMessage(e);
        }

        setTournamentControls();


    }

    private void setTournamentControls() {
        switch (nextPageController.agSettings.getSelectionType().getSelectionType())
        {
            case PROPORTIONAL:
                nextPageController.tournamentSizeTextField1.setDisable(true);
                nextPageController.probTournamentWinTextField.setDisable(true);
                break;
            case TOURNAMENT_HARD:
                nextPageController.tournamentSizeTextField1.setDisable(false);
                nextPageController.probTournamentWinTextField.setDisable(true);
                break;
            case TOURNAMENT_SOFT:
                nextPageController.tournamentSizeTextField1.setDisable(false);
                nextPageController.probTournamentWinTextField.setDisable(false);
                break;
        }
    }

    private AgSettings readDataFromInputs() throws Exception {
        String generationsNumber=generationsNumberTextField.getText();
        String selectionType =(String)selectionTypeChoiceBox.getValue();
        String functionType =(String)functionTypeChoiceBox.getValue();
        String probCross=probCrossTextField.getText();
        String probMutation=probMutationTextField.getText();
        String popsize=populationSizeTextField.getText();
        String[] properties={generationsNumber,selectionType,functionType,probCross,probMutation,popsize};
        isStringsEmpty(properties);

        AgSettings agSettings=new AgSettings();
        agSettings.setFunctionType(functionType,1);
        agSettings.setProbCross(Double.parseDouble(probCross));
        agSettings.setGenerationsNumber(Integer.parseInt(generationsNumber));
        agSettings.setProbMutation(Double.parseDouble(probMutation));
        agSettings.setSelectionType(selectionType);
        agSettings.setPopulationSize(Integer.parseInt(popsize));
        return agSettings;
    }

    private void isStringsEmpty(String[] properties) throws Exception {
        for(String property:properties)
        {
            if(property.isEmpty())
            {
                throw new Exception("Wszystkie pola muszą być uzupełnione");
            }
        }
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
        populationSizeTextField.textProperty().addListener(new DataValidation(populationSizeTextField,3,true));


    }

    private void setDefaultValues() {
        selectionTypeChoiceBox.setValue(selectionTypeChoiceBox.getItems().get(0));
        functionTypeChoiceBox.setValue(functionTypeChoiceBox.getItems().get(0));

        probCrossTextField.setText("70");
        probMutationTextField.setText("0.1");

        generationsNumberTextField.setText("300");

        populationSizeTextField.setText("160");

    }


    public void setMenuButtons(List<Button> buttonList) {
        this.menuButtonsList=buttonList;
    }

    public void setProgressController(ProgressController progressController) {

        this.progressController=progressController;
    }

    public void setMainWindowController(MainWindowControler mainWindowControler) {
        this.mainWindowController=mainWindowControler;
    }
}
