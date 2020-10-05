package org.phish.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.phish.Main;
import org.phish.classes.User;
import org.phish.database.DBHandler;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class UserViewController implements Initializable {


    private DBHandler dbHandler = new DBHandler();

    @FXML
    private TableView <User> userTableView;
    @FXML
    private TableColumn<User, Integer> idTableCol;
    @FXML
    private TableColumn<User, String> fNameTableCol;
    @FXML
    private TableColumn<User, String> lNameTableCol;

    @FXML
    private Button refreshBtn;
    @FXML
    private Button addUserBtn;


    public ObservableList<User> users = FXCollections.observableArrayList();


    @FXML
    public void refreshUserData(){
        users.clear();
        String SQLquery = "SELECT * FROM userTable";

        try (Connection conn = dbHandler.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(SQLquery)){
            while(rs.next()){
                try {
                   // System.out.println(rs.getInt("id")+ rs.getString("fName")+ rs.getString("lName"));
                    users.add(new User(rs.getInt("userId"), rs.getString("fName"), rs.getString("lName")));
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        loadUserData();
    }


    public void loadUserData(){

        idTableCol.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
        fNameTableCol.setCellValueFactory(new PropertyValueFactory<User, String>("fName"));
        lNameTableCol.setCellValueFactory(new PropertyValueFactory<User, String>("lName"));

        userTableView.setItems(users);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       loadUserData();
    }

    @FXML
    public void openAddUserWin(ActionEvent actionEvent) throws IOException {
        Main.showAddUserWindow();
    }
}
