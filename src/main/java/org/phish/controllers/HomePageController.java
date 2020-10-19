package org.phish.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import org.phish.Main;
import java.io.IOException;

public class HomePageController {

    @FXML
    HBox hBoxGlobalNav;

    //Global navigator
    public void showScene(ActionEvent actionEvent) throws IOException {
        switch (((Button)actionEvent.getSource()).getText()) {
            case "Calculator" -> Main.loadCenter("CalculatorPage.fxml");
            case "Statistics" -> Main.loadCenter("StatisticsPage.fxml");
            case "Vehicles" -> Main.loadCenter("VehiclesPage.fxml");
            case "All Emissions" -> Main.loadCenter("AllEmissions.fxml");
            case "Sign Out" -> {Main.setCurrentUserId(-1);
                                System.out.println("USER LOGGED OUT");
                                Main.showLoginView();}
            default -> Main.showMainView();
        }
        for (Node btnSceneSelect : hBoxGlobalNav.getChildren()) {
            btnSceneSelect.setDisable(false);
            if((actionEvent.getSource().equals(btnSceneSelect))) {
                btnSceneSelect.setDisable(true);
            }
        }
    }
}
