package org.example.controllers;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Setter;
import org.example.ag.AgSettings;
import org.example.ag.AgStatistic;
import org.example.ag.AgThread;

import org.example.ag.selection.SelectionType;
import org.example.utils.DataValidation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class PropertiesController1 implements Controller, Initializable {
    @FXML
    TextField probTournamentWinTextField;
    @FXML
    TextField precisionTextField;
    @FXML
    TextField funcDiminsionalTextField;
    @FXML
    TextField runsNumberTextField1;

    @FXML
    TextField seedDirectoryTextField;
    @FXML
    CheckBox seedCheckBox;

    @FXML
    TextField tournamentSizeTextField1;
    StackPane mainPane;
    VBox progressPane;

    @Setter VBox  perviousPage;
    AgSettings agSettings;
    private List<Button> menuButtonsList;
    private ProgressController progressController;
    private MainWindowControler mainWindowController;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDefaultValues();
        setTextFieldsForNumbers();
        Tooltip tooltip=new Tooltip("Wartości w procentach");
        Tooltip precisionTooltip=new Tooltip("Do którego miejsca po przecinku. Maksymalnie 8");
        Tooltip runsTooltip=new Tooltip("Maksymalnie 100");
        precisionTooltip.setShowDelay(Duration.millis(10));
        runsTooltip.setShowDelay(Duration.millis(10));
        probTournamentWinTextField.setTooltip(tooltip);
        precisionTextField.setTooltip(precisionTooltip);
        runsNumberTextField1.setTooltip(runsTooltip);

    }

    @FXML
    public void startTest()
    {
        //get data from fields

        String probTournamentWin=probTournamentWinTextField.getText();

        String precisionText=precisionTextField.getText();
        String fundimensional=funcDiminsionalTextField.getText();
        String[] properties={probTournamentWin,precisionText,fundimensional};
        String runsNumber=runsNumberTextField1.getText();
        String tournamentSize=tournamentSizeTextField1.getText();

        try {
            loadPropertiesToSettings(probTournamentWin, precisionText, fundimensional, properties,runsNumber,tournamentSize);
            Logger.getGlobal().info("Wprowadzono dane");
            AgThread agThread=new AgThread(agSettings);
            prepareThread(agThread);


            mainPane.getChildren().clear();
            mainPane.getChildren().add(progressPane);
            menuButtonsList.forEach(button->button.setDisable(true));
            new Thread(agThread).start();


        } catch (Exception e) {
            showAlertMessage(e);
        }


    }

    private void prepareThread(AgThread agThread) {
        progressController.progress.progressProperty().bind(agThread.progressProperty());
        agThread.setOnSucceeded(workerStateEvent ->
        {
            List<List<Double>> statistics=(List<List<Double>>)workerStateEvent.getSource().getValue();
            mainWindowController.getExportController().statistic=statistics;
            mainWindowController.resultsMenu();
            List<List<Double>>dataToPlot=new ArrayList<>();//0-runs mean 1 mean-std 2 mean+std
            if(statistics.size()>1)
            {

                List<Double>runsMean=getRunsMean(statistics);
                dataToPlot.add(runsMean);

                dataToPlot.add(getStd(runsMean,statistics,true));
                dataToPlot.add(getStd(runsMean, statistics, false));


            }
            else
                dataToPlot=statistics;
           mainWindowController.resultsController.loadDataToChart(dataToPlot);
           menuButtonsList.forEach(button -> button.setDisable(false));
           menuButtonsList.get(1).requestFocus();
        });
    }

    private List<Double> getStd(List<Double> runsMean, List<List<Double>> statistics, boolean isUnderLine) {
        List<List<Double>>genValues= AgStatistic.getGenerationsValues(statistics);
        List<Double>stdList=AgStatistic.getStdForGenerations(genValues);
        List<Double>result = new ArrayList<>();
        if(isUnderLine)
        {
            IntStream.range(0,stdList.size()).forEach(i->{
                result.add(runsMean.get(i)-stdList.get(i));
                    }
            );
        }
        else {
            IntStream.range(0,stdList.size()).forEach(i->{
                        result.add(runsMean.get(i)+stdList.get(i));
                    }
            );
        }
        return result;

    }

    private List<Double> getRunsMean(List<List<Double>> statistics) {
        List<List<Double>>genValues= AgStatistic.getGenerationsValues(statistics);
        return AgStatistic.getMeanForGenerations(genValues);
    }


    private void loadPropertiesToSettings(String probTournamentWin, String precisionText, String fundimensional, String[] properties, String runsNumber, String tournamentSize) throws Exception {

        isStringsEmpty(properties);//sprawdzam czy nie ma jakis pustych pol
        agSettings.setPrecision(Integer.parseInt(precisionText));
        agSettings.setFunctionDimensional(Integer.parseInt(fundimensional));
        if(agSettings.getSelectionType().getSelectionType()!= SelectionType.PROPORTIONAL)
            agSettings.setTounamentSize(Integer.parseInt(tournamentSize));
        agSettings.setProbTournamentWin(Double.parseDouble(probTournamentWin));

        if(seedCheckBox.isSelected())
        {
           long seed=getSeedFromFile();
           agSettings.setSeed(seed,mainWindowController);
        }
        else
            agSettings.setSeed(getRandomSeed(),mainWindowController);
        agSettings.setRunsNumber(Integer.parseInt(runsNumber));

    }

    private long getSeedFromFile() throws Exception {
        Scanner in = null;
        try {
            in = new Scanner(new File(seedDirectoryTextField.getText()));
            String seed= in.nextLine();
            return Long.parseLong(seed);
        } catch (Exception e) {
            throw new Exception("Plik ziarna nie odpowiada");
        }



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
        Logger.getGlobal().info("ziarno:"+seed);

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
        funcDiminsionalTextField.textProperty().addListener(new DataValidation(runsNumberTextField1,3,true));
        tournamentSizeTextField1.textProperty().addListener(new DataValidation(runsNumberTextField1,3,true));
    }
    private void setDefaultValues() {

        precisionTextField.setText("3");

        probTournamentWinTextField.setText("80");

        funcDiminsionalTextField.setText("1");
        runsNumberTextField1.setText("1");
        tournamentSizeTextField1.setText("3");

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

    public void setMainWindowController(MainWindowControler mainWindowController) {
        this.mainWindowController=mainWindowController;
    }
}
