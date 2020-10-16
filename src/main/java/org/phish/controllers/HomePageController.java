package org.phish.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.phish.Main;
import org.phish.database.DBHandler;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HomePageController {
    public void closeApplication(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void signOut (ActionEvent actionEvent) throws IOException {
        System.out.println("User signed out");

        Main.showMainView();

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
}
