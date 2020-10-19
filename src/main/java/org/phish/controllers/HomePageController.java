package org.phish.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.phish.Main;
import org.phish.classes.User;
import org.phish.database.DBHandler;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomePageController {

    private final DBHandler dbHandler = DBHandler.getInstance();
    @FXML
    HBox hBoxGlobalNav;
    @FXML
    Text txtWelcome;
    @FXML
    public void initialize() {
        if (dbHandler.connect()) {
            try {
                ResultSet rs = dbHandler.execQuery("SELECT * FROM userTable WHERE userID = "+Main.getCurrentUserId());
                txtWelcome.setText("Good Evening "+rs.getString("username"));
            }catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

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
            if (!((Button) actionEvent.getSource()).getText().equals("Sign Out")) {
                btnSceneSelect.setDisable(false);
                if ((actionEvent.getSource().equals(btnSceneSelect))) {
                    btnSceneSelect.setDisable(true);
                }
            }
        }
    }
}