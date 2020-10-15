package org.phish.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import org.phish.Main;
import org.phish.classes.GeneralEmission;
import org.phish.classes.TransportActivity;
import org.phish.classes.Vehicle;
import org.phish.database.DBHandler;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AllEmissionsController implements Initializable {



    private ObservableList<GeneralEmission> emissions = FXCollections.observableArrayList();
    private DBHandler dbHandler = new DBHandler();

    private ObservableList<Vehicle> vehicles = FXCollections.observableArrayList();
    private ObservableList<TransportActivity> transportActivities = FXCollections.observableArrayList();


    @FXML
    private TableView<GeneralEmission> emissionsTableview;
    @FXML
    private TableColumn<GeneralEmission, String> dateCol;
    @FXML
    private TableColumn<GeneralEmission, String> titleCol;
    @FXML
    private TableColumn<GeneralEmission, String> categoryCol;
    @FXML
    private TableColumn<GeneralEmission, Double> co2Col;

    @FXML
    private ToggleButton allToggle, foodToggle, transportToggle, houseToggle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SetupTable();
        try {
            loadData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void loadData() throws SQLException {
        loadVehicleData();
        loadFoodData();
        loadHouseData();
    }

    private void loadHouseData() {
    }

    private void loadFoodData() {

    }

    private void loadVehicleData() throws SQLException {
        //have to load in information from DB about the vehicle as I could not get inner join on three tables to work
        vehicles.clear();
        String SQLquery = "SELECT * FROM vehicles WHERE FKuserId=" +Main.getCurrentUserId();

        if(dbHandler.connect()){
            ResultSet rs    = dbHandler.execQuery(SQLquery);
            while (rs.next()) {
                try {
                    vehicles.add(new Vehicle(rs.getInt("vehicleId"), rs.getInt("FKuserId"), rs.getInt("FKvehicleTypeId"),
                            rs.getInt("FKfuelType"), rs.getDouble("litresKilometer"), rs.getString("vehicleName")));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        //load Transport activities from DB
        transportActivities.clear();
        SQLquery = "SELECT * FROM transportActivity WHERE FKuserId=" +Main.getCurrentUserId() + " ORDER BY date ASC";
        if(dbHandler.connect()){
            ResultSet rs    = dbHandler.execQuery(SQLquery);
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
        }

        for (int i =0; i<transportActivities.size();i++){
            //(String category, int FKId, LocalDate date, String title, double emission) {
            emissions.add(new GeneralEmission("Transport", transportActivities.get(i).getActivityId(), transportActivities.get(i).getDate(), transportActivities.get(i).getActivityName(), transportActivities.get(i).getCalculatedCO2()));
        }

    }

    private void SetupTable() {
        dateCol.setCellValueFactory(new PropertyValueFactory<GeneralEmission, String>("date"));
        titleCol.setCellValueFactory(new PropertyValueFactory<GeneralEmission, String>("title"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<GeneralEmission, String>("category"));
        co2Col.setCellValueFactory(new PropertyValueFactory<GeneralEmission, Double>("emission"));

        emissionsTableview.setItems(emissions);
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
