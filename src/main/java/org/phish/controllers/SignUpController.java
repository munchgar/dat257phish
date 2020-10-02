package org.phish.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import org.phish.Main;
import org.phish.classes.DB;

import java.io.IOException;

public class SignUpController implements DB {

    public void goHome(ActionEvent actionEvent) throws IOException {
        Main.showMainView();
    }

   
}
