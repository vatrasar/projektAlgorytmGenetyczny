package org.example.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.web.WebEngine;

public class WebListner implements ChangeListener<Worker.State> {

    WebEngine engine;
    String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public WebListner(WebEngine engine) {
        this.engine = engine;
    }

    public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {

        ObjectMapper objectMapper=new ObjectMapper();

        if (newState == Worker.State.SUCCEEDED) {

            engine.executeScript("makeChart(\""+message+"\")");

        }
    }
}
