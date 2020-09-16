module org.phish {
   // requires kotlin.stdlib;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires java.sql;


    opens org.phish to javafx.fxml;
    opens org.phish.controllers to javafx.fxml;
    exports org.phish;
    exports org.phish.classes;
    exports org.phish.controllers;
}