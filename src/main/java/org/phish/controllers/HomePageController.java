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
<<<<<<< Updated upstream
=======
    public void showSignUpSec (ActionEvent actionEvent) throws  IOException{
        Main.loadCenter("SignUp.fxml");
    }

    public void showLogin(ActionEvent actionEvent) throws IOException {
        Main.loadCenter("LoginPage.fxml");
    }

>>>>>>> Stashed changes
}
