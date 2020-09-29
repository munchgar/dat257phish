package org.phish.controllers;

import javafx.event.ActionEvent;
import org.phish.Main;

import java.io.IOException;

public class VehiclesPageController {

    public void openAddVehicle(ActionEvent actionEvent) throws IOException {
        //todo open add new vehicle window
        Main.showModalWindow("AddVehicleWindow.fxml", "Add Vehcile");
    }
}
