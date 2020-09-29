package org.phish.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.phish.Main;
import org.phish.classes.FuelType;
import org.phish.classes.VehicleType;
import org.phish.database.DBHandler;

import java.net.URL;
import java.sql.*;
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
    @FXML
    private Text errorText;


    public void cancel() {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    public void addVehicle() {

        //todo make sure the efficiency is a double

        if(vehicleNameField.getText().isBlank() || efficiencyField.getText().isBlank()){
            errorText.setVisible(true);
            errorText.setText("All field must be filled");
        }else{
            errorText.setVisible(false);
            String name = vehicleNameField.getText();
            double efficiency= Double.parseDouble(efficiencyField.getText());
            VehicleType vehicleTypeSelected = (VehicleType)vehicleTypeBox.getSelectionModel().getSelectedItem();
            FuelType fuelTypeSelected = (FuelType)fuelTypeBox.getSelectionModel().getSelectedItem();
            //System.out.println(vehicleTypeSelected.getVehicleTypeName() + " " + fuelTypeSelected.getFuelName());
            int userId = Main.getCurrentUserId();

            String sql = "INSERT INTO vehicles (FKuserId, FKvehicleTypeId, FKfuelType, litresKilometer, vehicleName) VALUES(?,?,?,?,?)";
                try (Connection conn = dbHandler.connect();
                     PreparedStatement pstmt = conn.prepareStatement(sql)) { // pstmt : Variable name?
                        pstmt.setInt(1, userId);
                        pstmt.setInt(2,vehicleTypeSelected.getVehicleTypeId());
                        pstmt.setInt(3,fuelTypeSelected.getFuelTypeId());
                        pstmt.setDouble(4,efficiency);
                        pstmt.setString(5,name);
                        pstmt.executeUpdate();
                        System.out.println("Vehicle successfully added to DB");
                    } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                clearFields();
        }
    }

    private void clearFields() {
        vehicleNameField.clear();
        efficiencyField.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorText.setVisible(false);
        loadChoiceBoxes();
        System.out.println(Main.getCurrentUserId());
    }

    private void loadChoiceBoxes(){
        //precautionary measure to not have the boxes populated. Alse NOT hardcoded since the DB might change
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
