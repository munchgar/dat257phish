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
        //Main.loadCenter("MainSections.fxml");
        Main.loadCenter("HomePage.fxml");
        //Finns ett litet bugg där ifall man trycker på "Home" när man är i Homepage då blire dublleter
    }
    public void showMainSec(ActionEvent actionEvent) throws IOException{
        Main.loadCenter("MainSections.fxml");
    }
    @FXML
    private ImageView Img1;


    public void init(){
        Img1.setImage(new Image("@../../../../../../../Downloads/unnamed.png"));
    }
}
