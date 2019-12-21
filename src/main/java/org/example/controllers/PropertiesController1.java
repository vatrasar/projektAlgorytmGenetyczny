package org.example.controllers;

import eu.hansolo.tilesfx.Tile;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import java.util.List;
import java.util.Random;
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
    TextField seedDirectoryTextField;

    StackPane mainPane;
    VBox progressPane;

    @Setter VBox  perviousPage;
    AgSettings agSettings;
    private List<Button> menuButtonsList;
    private ProgressController progressController;


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
        String[] properties={probTournamentWin,precisionText,fundimensional};

        try {
            isStringsEmpty(properties);//sprawdzam czy nie ma jakis pustych pol
            agSettings.setPrecision(Integer.parseInt(precisionText));
            agSettings.setFunctionDimensional(Integer.parseInt(fundimensional));
            agSettings.setProbTournamentWin(Double.parseDouble(probTournamentWin));
            agSettings.setSeed(getRandomSeed());
            agSettings.setRunsNumber(1);
            Logger.getGlobal().info("Wprowadzono dane");

            AgThread watek=new AgThread(agSettings,progressController);


            watek.start();


            mainPane.getChildren().clear();
            mainPane.getChildren().add(progressPane);
            menuButtonsList.forEach(button->button.setDisable(true));



        } catch (Exception e) {
            showAlertMessage(e);
        }


    }

    private Tile getProgresBar() {
        for(Node node:progressPane.getChildren())
        {

            if(node.getId().equals("progresBar"))
            {
                return (Tile)node;
            }
        }
        return (Tile)progressPane.getChildren().get(0);
    }

    public void backToFirstPage()
    {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(perviousPage);
    }
    public long getRandomSeed()
    {
        Random random=new Random();
        long seed=random.nextLong();
        return seed;
    }

    public void chooseSeedDirectory()
    {
        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Wybór lokalizacji ziarna");
        File file=fileChooser.showOpenDialog(new Stage());
        seedDirectoryTextField.setText(file.getAbsolutePath());


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
    public void setProgressPane(VBox progressPane)
    {
        this.progressPane=progressPane;
    }

    public void setMenuButonsList(List<Button> menuButtonsList) {
        this.menuButtonsList=menuButtonsList;
    }

    public void setProgressController(ProgressController progressController) {
            this.progressController=progressController;
    }
}
