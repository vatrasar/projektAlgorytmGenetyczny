package org.example.controllers;

import eu.hansolo.tilesfx.Tile;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;

import java.net.URL;
import java.nio.file.FileSystemAlreadyExistsException;

import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ProgressController implements Initializable {

    @FXML
    ProgressBar progress;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        progress.setProgress(0);

    }

    public void updateProgresBar(double procentOfFinished) {


    }
}
