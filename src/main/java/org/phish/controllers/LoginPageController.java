package org.phish.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.phish.classes.DB;
import org.phish.database.DBHandler;
import org.phish.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


<<<<<<< Updated upstream
public class LoginPageController implements DB {
=======
public class LoginPageController implements Initializable {
>>>>>>> Stashed changes

    @FXML
    public TextField idUsername;
    @FXML
    public TextField idPassword;
    @FXML
    public Button cancelBtn;
    @FXML
    public Text fieldsFilledCheckText;
    @FXML
    public Text wrongInputCheckText;

    public int login() throws SQLException, IOException {
        DBHandler dbHandler = new DBHandler();

        if(!idUsername.getText().isBlank() && !idPassword.getText().isBlank()){
            //System.out.println(fNameField.getText() + " " + lNameField.getText());
            if(fieldsFilledCheckText.isVisible()){
                fieldsFilledCheckText.setVisible(false);
            }
        }
        else {
            // System.out.println("All textfields must be filled");
            fieldsFilledCheckText.setVisible(true);
            fieldsFilledCheckText.setFill(Color.RED);
        }

        if(dbHandler.connect()) { // Attempt to connect to database.
            ResultSet rs = dbHandler.execQuery("SELECT * FROM userTable"); // Execute query
            while (rs.next()) {
                if (rs.getString("username").equals(idUsername.getText()) && (rs.getString("password").equals(idPassword.getText()))) {
<<<<<<< Updated upstream
                    System.out.println("USER LOGGED IN");
=======
                    System.out.println("USER LOGGED IN"); // Implement login for user ID PRIMARY KEY IN DB
                    System.out.println(rs.getString("username"));
                    if (dbHandler.connect()) {
                        ResultSet rs2 = dbHandler.execQuery("SELECT userId FROM userTable WHERE username = '" + rs.getString("username") + "'");
                        // return rs2.getInt("userId"); // No reason for a return statement here?
                        Main.setCurrentUserId(rs.getInt("userId"));
                        Main.showMainView();
                    }
                } else {
                    if(!fieldsFilledCheckText.isVisible()) {
                        wrongInputCheckText.setVisible(true);
                        wrongInputCheckText.setFill(Color.RED);
                    }
>>>>>>> Stashed changes
                }
            }
        }
        return -1; // Login failed, also no reason for a return?
    }

    public void closeWindow(ActionEvent actionEvent) throws IOException {
   /*     FXMLLoader loader = new FXMLLoader(getClass().getResource("AddNewUser.fxml"));
        OwnController controller = loader.getController();
        controller.setStage(this.stage);
    */
        Main.showMainView();
//        //Returns the stage associated with the button. In this case, the cancel button
//        Stage stage = (Stage) cancelBtn.getScene().getWindow();
//        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fieldsFilledCheckText.setVisible(false);
        wrongInputCheckText.setVisible(false);
    }

}
