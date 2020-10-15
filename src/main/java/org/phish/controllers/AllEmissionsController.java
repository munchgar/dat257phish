package org.phish.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import org.phish.classes.GeneralEmission;

import java.net.URL;
import java.util.ResourceBundle;

public class AllEmissionsController implements Initializable {


    private ObservableList<GeneralEmission> emissions = FXCollections.observableArrayList();


    @FXML
    private TableView<GeneralEmission> emissionsTableview;
   /* @FXML
    private TableColumn<>
    @FXML
    private TableColumn<>
    @FXML
    private TableColumn<>
    @FXML
    private TableColumn<>

    @FXML
    public ToggleButton allToggle;*/


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void filterAll(ActionEvent actionEvent) {
    }

    public void filterFood(ActionEvent actionEvent) {
    }

    public void filterTransport(ActionEvent actionEvent) {
    }

    public void filterHouse(ActionEvent actionEvent) {
    }
}
