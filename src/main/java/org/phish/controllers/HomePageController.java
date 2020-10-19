package org.phish.controllers;

import javafx.event.ActionEvent;
import org.phish.Main;
import java.io.IOException;

public class HomePageController {

    public void showCalcScreen(ActionEvent actionEvent) throws IOException {
        Main.loadCenter("CalculatorPage.fxml");
    }
    public void showStatScreen(ActionEvent actionEvent) throws IOException {
        Main.loadCenter("StatisticsPage.fxml");
    }

    public void showVehicles(ActionEvent actionEvent)throws IOException{
        Main.loadCenter("VehiclesPage.fxml");
    }

    /*public void showActivities(ActionEvent actionEvent) throws IOException {
        Main.loadCenter("TransportActivities.fxml");
    }*/

    public void goHome(ActionEvent actionEvent) throws IOException {
        Main.showMainView();

    }

    public void logoutEvent(ActionEvent actionEvent) throws IOException {
        Main.setCurrentUserId(-1);
        System.out.println("USER LOGGED OUT");
        Main.showLoginView();
    }

    public void showAllEmissions(ActionEvent actionEvent) throws IOException {
        Main.loadCenter("AllEmissions.fxml");
    }
}
