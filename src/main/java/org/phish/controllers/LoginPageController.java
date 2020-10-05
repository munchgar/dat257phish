package org.phish.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.phish.classes.DB;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginPageController implements DB {

    @FXML
    public TextField idUsername;
    @FXML
    public TextField idPassword;
    @FXML
    public Button cancelBtn;

    public void login() {

        try(
            ResultSet rs = DB.select("username", "userTable")) {
            while(rs.next()){
                if(rs.getString("username") == idUsername.getText()) {
                    System.out.println("USER ALREADY EXISTS");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

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
