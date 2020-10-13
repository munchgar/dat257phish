package org.phish.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.phish.database.DBHandler;
import org.phish.classes.User;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddNewUserController implements Initializable {

    private DBHandler dbHandler = DBHandler.getInstance();

    @FXML
    public TextField fNameField;
    @FXML
    public TextField lNameField;
    @FXML
    public Button cancelBtn;
    @FXML
    public Text fieldsFilledCheckText;



    public void addUser(ActionEvent actionEvent) throws SQLException {
        if(!fNameField.getText().isBlank() && !lNameField.getText().isBlank()){
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

        if (dbHandler.connect()) { // Attempt to connect to database.

                try {
                    if (!(((fNameField.getText().length() == 0) || (lNameField.getText().length() == 0)))) {

                        String input = "'" + fNameField.getText() + "'" + "," + "'" + lNameField.getText() + "'";
                        System.out.println(input);
                        String query = "INSERT INTO userTable (fName, lName) VALUES ("+ input + ");"; // implement for strings instead ''

                        dbHandler.execUpdate(query);
                        System.out.println("User successfully added to DB");
                    } else {
                        // Is currently "handled" in xml, something we can do here?
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

        }
        /*
            String sql = "INSERT INTO userTable (fName, lName) VALUES(?,?)";
        try {
            try (Connection conn = dbHandler.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) { // pstmt : Variable name?
                if(!(((fNameField.getText().length() == 0) || (lNameField.getText().length()==0)))) {
                    pstmt.setString(1, fNameField.getText());
                    pstmt.setString(2, lNameField.getText());
                    pstmt.executeUpdate();
                    System.out.println("User successfully added to DB");
                } else {
                    // Is currently "handled" in xml, something we can do here?
                }
            }
         */
            clearFields();



    }

    private void clearFields() {
        fNameField.clear();
        lNameField.clear();
        fNameField.requestFocus();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fieldsFilledCheckText.setVisible(false);
        fNameField.requestFocus();

    }
}
