module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    opens org.example to javafx.fxml,org.kordamp.ikonli.javafx,javafx.web,com.fasterxml.jackson.core;
    opens org.example.controllers to javafx.fxml;
    requires java.logging;
    exports org.example;
    requires org.kordamp.ikonli.fontawesome;
    requires org.kordamp.ikonli.javafx;
    requires com.fasterxml.jackson.databind;
    requires javaplot;
    requires commons.math3;
    requires javafx.web;


}