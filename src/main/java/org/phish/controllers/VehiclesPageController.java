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
import org.phish.classes.Vehicle;
import org.phish.database.DBHandler;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class VehiclesPageController implements Initializable {


    private ObservableList<Vehicle> vehicles = FXCollections.observableArrayList();
    private DBHandler dbHandler = new DBHandler();

    @FXML
    private TableView<Vehicle> vehiclesTableView;
    @FXML
    private TableColumn<Vehicle, String> vehicleNameCol;
    @FXML
    private TableColumn<Vehicle, String> vehicleTypeCol;
    @FXML
    private TableColumn<Vehicle, String> vehicleFuelTypeCol;
    @FXML
    private TableColumn<Vehicle, Double> vehicleEfficiencyCol;

    public void openAddVehicle(ActionEvent actionEvent) throws IOException {
        //todo open add new vehicle window
        Main.showModalWindow("AddVehicleWindow.fxml", "Add Vehicle");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initColumns();
        loadData();
    }

    private void loadData() {
        vehicles.clear();
        String SQLquery = "SELECT * FROM vehicles WHERE FKuserId=" +Main.getCurrentUserId();

        try (Connection conn = dbHandler.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(SQLquery)){
            while(rs.next()){
                try {
                    //public Vehicle(int vehicleId, int userId, int vehicleType, int fuelType, double kmLitre, String vehicleName)
                    vehicles.add(new Vehicle(rs.getInt("vehicleId"), rs.getInt("FKuserId"), rs.getInt("FKvehicleTypeId"),
                            rs.getInt("FKfuelType"), rs.getDouble("litresKilometer"), rs.getString("vehicleName")));
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        //vehicles.add(new Vehicle(10, 1, 1, 1, 0.8, "testVehicle", "Personal","Diesel" ));

    }

    private void initColumns() {
        vehicleNameCol.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("vehicleName"));
        vehicleTypeCol.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("vehicleTypeString"));
        vehicleFuelTypeCol.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("fuelTypeString"));
        vehicleEfficiencyCol.setCellValueFactory(new PropertyValueFactory<Vehicle, Double>("kmLitre"));
        //loadData();
        vehiclesTableView.setItems(vehicles);
    }
}
