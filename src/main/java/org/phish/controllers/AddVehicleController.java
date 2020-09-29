package org.phish.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.phish.classes.FuelType;
import org.phish.classes.User;
import org.phish.classes.VehicleType;
import org.phish.database.DBHandler;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AddVehicleController implements Initializable {

    private DBHandler dbHandler = new DBHandler();
    ObservableList<VehicleType> vehicleTypes = FXCollections.observableArrayList();
    ObservableList<FuelType> fuelTypes = FXCollections.observableArrayList();


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
        loadChoiceBoxes();
    }

    private void loadChoiceBoxes(){
        vehicleTypes.clear();
        fuelTypes.clear();

        //VehicleTypeBox
        String SQLquery = "SELECT * FROM vehicleType";
        try (Connection conn = dbHandler.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(SQLquery)){
            while(rs.next()){
                try {
                    System.out.println(rs.getString("type"));
                    vehicleTypes.add(new VehicleType(rs.getInt("vehicleTypeId"), rs.getString("type")));
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                vehicleTypeBox.setItems(vehicleTypes);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        //FueltypeBox
        String SQLquery2 = "SELECT * FROM fuelType";
        try (Connection conn = dbHandler.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(SQLquery2)){
            while(rs.next()){
                try {
                    System.out.println(rs.getString("fuelName"));
                    fuelTypes.add(new FuelType(rs.getInt("fuelId"), rs.getString("fuelName"), rs.getInt("gCO2Litre")));
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                fuelTypeBox.setItems(fuelTypes);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        //Sets a default value in the choiceboxes
        vehicleTypeBox.setValue(vehicleTypes.get(0));
        fuelTypeBox.setValue(fuelTypes.get(0));
    }
}
