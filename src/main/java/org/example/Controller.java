package org.example;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public interface Controller {
    default  Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("templatki/"+fxml + ".fxml"));
        return fxmlLoader.load();
    }
}
