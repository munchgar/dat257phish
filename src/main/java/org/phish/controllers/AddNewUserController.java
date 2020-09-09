package org.phish.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;

public class AddNewUserController {

    @FXML
    public TextField fNameField;
    @FXML
    public TextField lNameField;
    @FXML
    public TextField CO2Field;



    public void addUser(ActionEvent actionEvent) {
        if(!fNameField.getText().isBlank() && !lNameField.getText().isBlank() && !CO2Field.getText().isBlank()){
            System.out.println(fNameField.getText() + " " + lNameField.getText() + " CO2: " + CO2Field.getText());

            //ADD TO DB and refresh
        }
        else {
            System.out.println("All textfields must be filled");
        }

    }

    public void closeWindow(ActionEvent actionEvent) {
   /*     FXMLLoader loader = new FXMLLoader(getClass().getResource("AddNewUser.fxml"));
        OwnController controller = loader.getController();
        controller.setStage(this.stage);
    */
    }
}
