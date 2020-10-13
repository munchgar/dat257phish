package org.phish.controllers;

import javafx.event.ActionEvent;
import org.phish.Main;

import java.io.IOException;

public class HomePageController {
    public void closeApplication(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void goHome(ActionEvent actionEvent) throws IOException {
        Main.loadCenter("MainSections.fxml");
    }
}
