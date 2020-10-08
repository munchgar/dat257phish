package org.phish.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class AddActivityController implements Initializable {


    @FXML
    private ComboBox vehicleTypeBox, vehicleBox;
    @FXML
    private TextField distanceField, titleField, timesDayField;
    @FXML
    private CheckBox reoccurringCheck;
    @FXML
    private ToggleButton monBtn, tuesBtn, wedBtn, thurBtn, friBtn, satBtn, sunBtn;
    @FXML
    private Button cancelbtn, addbtn;




    public void updateVehiclesBox(ActionEvent actionEvent) {
    }

    public void updateReocurring(ActionEvent actionEvent) {
    }

    public void cancel(ActionEvent actionEvent) {
    }

    public void addBtnPressed(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
