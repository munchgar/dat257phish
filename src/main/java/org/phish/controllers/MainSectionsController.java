package org.phish.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.phish.Main;

import java.io.IOException;

public class MainSectionsController {


    @FXML
    public void showUserView(ActionEvent actionEvent) throws IOException {
        Main.loadCenter("UserView.fxml");


    }

    public void showCalcScreen(ActionEvent actionEvent) throws IOException {
        Main.loadCenter("CalculatorPage.fxml");
    }
	public void showStatScreen(ActionEvent actionEvent) throws IOException {
		Main.loadCenter("StatisticsPage.fxml");
	}

    public void showVehicles(ActionEvent actionEvent)throws IOException{
        Main.loadCenter("VehiclesPage.fxml");
    }
}
