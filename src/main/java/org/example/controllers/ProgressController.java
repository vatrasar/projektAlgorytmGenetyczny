package org.example.controllers;

import eu.hansolo.tilesfx.Tile;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;

import java.net.URL;
import java.nio.file.FileSystemAlreadyExistsException;

import java.util.ResourceBundle;

public class ProgressController implements Initializable {

    @FXML
    Tile progresBar;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        progresBar.setRoundedCorners(false);
        progresBar.setValue(100);

    }

    public synchronized void updateProgresBar(double procentOfFinished) {
        progresBar.setValue(procentOfFinished*100);
    }
}
