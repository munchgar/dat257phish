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
import org.phish.classes.TransportActivity;
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

public class TransportActivitiesController implements Initializable {

    private DBHandler dbHandler = new DBHandler();
    private ObservableList<TransportActivity>  transportActivities = FXCollections.observableArrayList();
    private ObservableList<Vehicle> vehicles = FXCollections.observableArrayList();

    @FXML
    private TableView<TransportActivity> activityTableView;
    @FXML
    private TableColumn<TransportActivity, String> dateCol;
    @FXML
    private TableColumn<TransportActivity, Integer> distanceCol;
    @FXML
    private TableColumn<TransportActivity, String> vehicleNameCol;
    @FXML
    private TableColumn<TransportActivity, Double> co2Col;


    public void openAddActivitiesWindow(ActionEvent actionEvent) throws IOException {
        Main.showModalWindow("AddActivity.fxml", "Add new Activity");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpTable();
        loadData();
    }

    public void refresh(){
       // vehicles.clear();
       // transportActivities.clear();
        loadData();
    }

    private void loadData() {

        //have to load in information from DB about the vehicle as I could not get inner join on three tables to work
        vehicles.clear();
        String SQLquery = "SELECT * FROM vehicles WHERE FKuserId=" +Main.getCurrentUserId();

        try (Connection conn = dbHandler.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(SQLquery)){
            while(rs.next()){
                try {
                    vehicles.add(new Vehicle(rs.getInt("vehicleId"), rs.getInt("FKuserId"), rs.getInt("FKvehicleTypeId"),
                            rs.getInt("FKfuelType"), rs.getDouble("litresKilometer"), rs.getString("vehicleName")));
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        //load Transport activities from DB
        transportActivities.clear();
        SQLquery = "SELECT * FROM transportActivity WHERE FKuserId=" +Main.getCurrentUserId() + " ORDER BY date ASC";
        try (Connection conn = dbHandler.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(SQLquery)){
            while(rs.next()){
                try {
                    //todo clean up
                    int vehicleId = rs.getInt("FKVehicleId");
                    int index = -1;
                    for(int i = 0; i <vehicles.size(); i++){
                        if (vehicles.get(i).getVehicleId()==vehicleId){
                            index = i;
                            i=vehicles.size();
                        }
                    }
                    //TransportActivity(int activityId, int userId, int distance, String date, String activityName, Vehicle vehicle)
                    transportActivities.add(new TransportActivity(rs.getInt("transportActivityId"), rs.getInt("FKuserId"), rs.getInt("distanceKm"),
                            rs.getString("date"), rs.getString("activityName"), vehicles.get(index)));
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        activityTableView.setItems(transportActivities);
    }

    private void setUpTable() {
        dateCol.setCellValueFactory(new PropertyValueFactory<TransportActivity, String>("date"));
        distanceCol.setCellValueFactory(new PropertyValueFactory<TransportActivity, Integer>("distance"));
        vehicleNameCol.setCellValueFactory(new PropertyValueFactory<TransportActivity, String>("vehicleName"));
        co2Col.setCellValueFactory(new PropertyValueFactory<TransportActivity, Double>("calculatedCO2"));
    }
}
