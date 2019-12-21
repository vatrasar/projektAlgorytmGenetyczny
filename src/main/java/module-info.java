module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    opens org.example to javafx.fxml,org.kordamp.ikonli.javafx,eu.hansolo.tilesfx;
    opens org.example.controllers to javafx.fxml;
    requires java.logging;
    exports org.example;
    requires org.kordamp.ikonli.fontawesome;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;


}