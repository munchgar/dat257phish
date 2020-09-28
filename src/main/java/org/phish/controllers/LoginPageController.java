package org.phish.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginPageController {

    @FXML
    public TextField idUsername;
    @FXML
    public TextField idPassword;
    @FXML
    public Button cancelBtn;

    public void login() {

    }

    public void closeWindow(ActionEvent actionEvent) {
   /*     FXMLLoader loader = new FXMLLoader(getClass().getResource("AddNewUser.fxml"));
        OwnController controller = loader.getController();
        controller.setStage(this.stage);
    */

        //Returns the stage associated with the button. In this case, the cancel button
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

}
