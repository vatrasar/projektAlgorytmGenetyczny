module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    opens org.example to javafx.fxml;
    opens org.example.controllers to javafx.fxml;
    requires java.logging;
    exports org.example;
}