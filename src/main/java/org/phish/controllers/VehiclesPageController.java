package org.phish.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import org.phish.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VehiclesPageController implements Initializable {


    public void openAddVehicle(ActionEvent actionEvent) throws IOException {
        //todo open add new vehicle window
        Main.showModalWindow("AddVehicleWindow.fxml", "Add Vehcile");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
