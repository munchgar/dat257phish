package org.phish.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.phish.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TransportActivitiesController implements Initializable {


    public void openAddActivitiesWindow(ActionEvent actionEvent) throws IOException {
        Main.showModalWindow("AddActivity.fxml", "Add new Activity");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
}
