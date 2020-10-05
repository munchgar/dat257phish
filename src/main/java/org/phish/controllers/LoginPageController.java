package org.phish.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.phish.classes.DB;
import org.phish.database.DBHandler;


import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginPageController implements DB {

    @FXML
    public TextField idUsername;
    @FXML
    public TextField idPassword;
    @FXML
    public Button cancelBtn;

    public void login() throws SQLException {
        DBHandler dbHandler = new DBHandler();

        if(dbHandler.connect()) { // Attempt to connect to database.
            ResultSet rs = dbHandler.execQuery("SELECT * FROM userTable"); // Execute query
            while (rs.next()) {
                System.out.println(rs.getString("username"));
                if (rs.getString("username").equals(idUsername.getText()) && (rs.getString("password").equals(idPassword.getText()))) {
                    System.out.println("USER LOGGED IN");
                }
            }
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
