package org.phish.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddVehicleController implements Initializable {



    @FXML
    private TextField vehicleNameField, efficiencyField;
    @FXML
    private ChoiceBox vehicleTypeBox, fuelTypeBox;
    @FXML
    private Button addBtn, cancelBtn;


    public void cancel(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    public void addVehicle(ActionEvent actionEvent) {
        if(vehicleNameField.getText().isBlank() || efficiencyField.getText().isBlank()){
            //todo add error message
        }else{
            String name = vehicleNameField.getText().toString();
            int efficiency= Integer.parseInt(efficiencyField.getText().toString());

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
