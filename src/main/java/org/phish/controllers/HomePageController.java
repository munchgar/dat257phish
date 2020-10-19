package org.phish.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.phish.Main;

import java.io.IOException;

public class HomePageController {
    public void closeApplication(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void goHome(ActionEvent actionEvent) throws IOException {
        Main.showMainView();
      //  Main.loadCenter("HomePage.fxml");

    }
    public void showMainSec(ActionEvent actionEvent) throws IOException{
        Main.loadCenter("MainSections.fxml");
    }
    public void showSignUpSec (ActionEvent actionEvent) throws  IOException{
        Main.loadCenter("SignUp.fxml");
    }

    public void showLogin(ActionEvent actionEvent) throws IOException {
        Main.loadCenter("LoginPage.fxml");
    }

    public void logoutEvent(ActionEvent actionEvent) throws IOException {
        Main.setCurrentUserId(-1);
        System.out.println("USER LOGGED OUT");
        Main.loadCenter("LoginPage.fxml");
    }

}
