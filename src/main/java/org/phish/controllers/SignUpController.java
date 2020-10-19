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
    private PasswordField psfPassword;
    @FXML
    private Button btnSignUp;
    @FXML
    private Label signUpLabel;


    public void SignUp (ActionEvent actionEvent)throws SQLException{



        String sql = "INSERT INTO userTable (userName, password, fName, lName) VALUES(?,?,?,?)";  // What happens with firstname and lastname? Not implemented?
                                                                                // TODO: Since fName and lName is needed maybe we should fix the insert?

        if(txtUserName.getText().isBlank() || psfPassword.getText().isBlank()) { // TODO: Implement retard proof textfield. Example " S" doesnt work.
            System.out.println("ERROR!!!");
            if(!fieldsFilledCheckText.isVisible()) {
                fieldsFilledCheckText.setVisible(true);
                fieldsFilledCheckText.setFill(Color.RED);
            }
        } else {
            fieldsFilledCheckText.setVisible(false);
            dbHandler.connect();
            try {
                try (
                        PreparedStatement preparedStatement = dbHandler.getConn().prepareStatement(sql)){
                        preparedStatement.setString(1,txtUserName.getText());
                        preparedStatement.setString(2,psfPassword.getText());
                        // TODO: Better implementation for fName and lName... We accept input from fName and lName but do nothing with them?
                        preparedStatement.setString(3,"asd");
                        preparedStatement.setString(4,"asd");

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











/*
    public void SignUp(ActionEvent actionEvent) throws IOException {
        //if (DB.register(txtUserName.getText(), psfPassword.toString()))
        if(!txtUserName.getText().isBlank() && !psfPassword.getText().isBlank()){
            if(fieldsFilledCheckText.isVisible()){
                fieldsFilledCheckText.setVisible(false);
            }
        }
        else {
            // System.out.println("All textfields must be filled");
            fieldsFilledCheckText.setVisible(true);
            fieldsFilledCheckText.setFill(Color.RED);
        }

        String sql =  "INSERT INTO userTable (username, password) VALUES(?,?)";
        try {
            try (Connection connection = dbHandler.connect();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                if (!(((txtUserName.getText().length() == 0) || (psfPassword.getText().length() == 0)))) {
                    preparedStatement.setString(1, txtUserName.getText());
                    preparedStatement.setString(2, psfPassword.getText());
                    preparedStatement.executeUpdate();
                    System.out.println("User successfully added to DB");
                }
            } catch (Error | SQLException error) {
                System.out.println(error.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
*/
