package org.phish.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.phish.Main;
import org.phish.database.DBHandler;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    private DBHandler dbHandler = DBHandler.getInstance();;

    @FXML
    public Text fieldsFilledCheckText;
    @FXML
    private TextField txtUserName;
    @FXML
    private TextField txtUserName1;
    @FXML
    private TextField txtUserName2;
    @FXML
    private PasswordField psfPassword;


    public void SignUp (ActionEvent actionEvent) throws SQLException, IOException {


        // TODO: Now only one password matters and is inputted to database... Control so that they are the same?
        String sql = "INSERT INTO userTable (userName, password, fName, lName) VALUES(?,?,?,?)";

        if(txtUserName.getText().isBlank() || psfPassword.getText().isBlank()) { // TODO: Implement check for fName and lName aswell?
            System.out.println("ERROR!!!");
            if(!fieldsFilledCheckText.isVisible()) {
                fieldsFilledCheckText.setText("All fields must be filled");
                fieldsFilledCheckText.setVisible(true);
                fieldsFilledCheckText.setFill(Color.RED);
            }
        } else {
            fieldsFilledCheckText.setVisible(false);
            dbHandler.connect();
            if(dbHandler.connect()) { // Attempt to connect to database.
                ResultSet rs = dbHandler.execQuery("SELECT * FROM userTable"); // Execute query
                while (rs.next()) {
                    if (rs.getString("username").equals(txtUserName.getText())) {
                        System.out.println("USER ALREADY EXISTS"); // Implement login for user ID PRIMARY KEY IN DB
                        fieldsFilledCheckText.setText("USER ALREADY EXISTS");
                        fieldsFilledCheckText.setVisible(true);
                        fieldsFilledCheckText.setFill(Color.RED);
                        return;
                    }
                }
            }
            try {
                try (
                        PreparedStatement preparedStatement = dbHandler.getConn().prepareStatement(sql)){
                    preparedStatement.setString(1,txtUserName.getText());
                    preparedStatement.setString(2,psfPassword.getText());
                    preparedStatement.setString(3,txtUserName1.getText());
                    preparedStatement.setString(4,txtUserName2.getText());
                    preparedStatement.executeUpdate();
                    System.out.println("User successfully added to DB");
                    Main.loadCenter("LoginPage.fxml");
                }
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            } finally {
                if (dbHandler.getConn() != null){
                    // dbHandler.kill();
                }
            }
        }
    }

    public void showLogin(ActionEvent actionEvent) throws IOException {
        Main.loadCenter("LoginPage.fxml");
    }

    public void goHome (ActionEvent actionEvent) throws IOException {
        Main.showMainView();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fieldsFilledCheckText.setVisible(false);
    }
}


