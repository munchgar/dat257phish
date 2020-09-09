package org.phish.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.phish.Main;
import org.phish.classes.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserViewController implements Initializable {



    @FXML
    private TableView <User> userTableView;
    @FXML
    private TableColumn<User, String> nameTableCol;
    @FXML
    private TableColumn<User, Integer> co2TableCol;


    public ObservableList<User> users = FXCollections.observableArrayList(
            new User("Pontus", 56365),
            new User("Alexander", 64349),
            new User("Adrian", 5637),
            new User("Johan", 72385),
            new User("Lukas", 47639),
            new User("Hedén", 54678),
            new User("Zack", 643930)
    );


    @FXML
    public void loadUserData(){
        nameTableCol.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        co2TableCol.setCellValueFactory(new PropertyValueFactory<User, Integer>("co2"));
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
