package org.phish.controllers;

import javafx.event.ActionEvent;
import org.phish.Main;

import java.io.IOException;

public class TransportActivitiesController {
    public void openAddActivitiesWindow(ActionEvent actionEvent) throws IOException {
        Main.showModalWindow("AddActivity.fxml", "Add new Activity");
    }
}
