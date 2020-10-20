package org.phish.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.phish.Main;
import org.phish.classes.GeneralEmission;
import org.phish.classes.TransportActivity;
import org.phish.classes.Vehicle;
import org.phish.database.DBHandler;

import javax.xml.transform.Result;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AllEmissionsController implements Initializable {


    private ObservableList<GeneralEmission> transportEmissions = FXCollections.observableArrayList();
    private ObservableList<GeneralEmission> emissions = FXCollections.observableArrayList();
    private DBHandler dbHandler = DBHandler.getInstance();

    private ObservableList<Vehicle> vehicles = FXCollections.observableArrayList();
    private ObservableList<TransportActivity> transportActivities = FXCollections.observableArrayList();

    private ObservableList<GeneralEmission> foodEmissions = FXCollections.observableArrayList();

    private ObservableList<GeneralEmission> flightEmissions = FXCollections.observableArrayList();

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
    private TextField foodField, transportField, houseField, flightField, totalField;
    @FXML
    private CheckBox dateFilterCheck;
    @FXML
    private DatePicker fromDate, toDate;
    @FXML
    private Text errorText;
    @FXML
    private ToggleButton allToggle, foodToggle, transportToggle, houseToggle, flightToggle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTable();
        try {
            loadData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        allToggle.setSelected(true);
        try {
            filterAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(!dateFilterCheck.isSelected()){
            fromDate.setDisable(true);
            toDate.setDisable(true);
        }
        fromDate.setValue(LocalDate.now().minusDays(1));
        toDate.setValue(LocalDate.now().plusDays(1));
        errorText.setText("From date cannot \nbe after To date");
        errorText.setVisible(false);
    }

    private void loadData() throws SQLException {
        emissions.clear();
        double transportSum=0, foodSum=0, houseSum = 0, flightSum = 0, totalSum=0;

        loadVehicleData();
        loadFoodData();
        loadHouseData();
        loadFlightData();
        loadPTData();

        if(transportToggle.isSelected()){
            emissions.addAll(transportEmissions);
            for (GeneralEmission ge : transportEmissions) {
                transportSum += ge.getEmission();
            }
            transportField.setText(Double.toString(transportSum));
        }
        if(foodToggle.isSelected()){
            emissions.addAll(foodEmissions);
            for (GeneralEmission ge : foodEmissions) {
                foodSum += ge.getEmission();
            }
            foodField.setText(Double.toString(foodSum));
        }
        if (flightToggle.isSelected()) {
            emissions.addAll(flightEmissions);
            for (GeneralEmission ge : flightEmissions) {
                flightSum += ge.getEmission();
            }
            flightField.setText(Double.toString(flightSum));
        }
        if(houseToggle.isSelected()){
            //Todo emissions.addAll(houseEmissions);

            houseField.setText(Double.toString(houseSum));
        }
        totalSum= transportSum+foodSum+houseSum;
        totalField.setText(Double.toString(totalSum));
    }

    private void loadFlightData() throws SQLException {
        flightEmissions.clear();
        String SQLquery = "SELECT 'Flight' AS name, date, co2 FROM flightActivity WHERE userID=" + Main.getCurrentUserId() + " ORDER BY date ASC";
        if (dbHandler.connect()) {
            ResultSet rs = dbHandler.execQuery(SQLquery);
            while (rs.next()) {
                if(dateFilterCheck.isSelected()){
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String datetoCheckString = rs.getString("date");

                    //convert String to LocalDate
                    LocalDate date = LocalDate.parse(datetoCheckString, formatter);
                    if (date.isAfter(fromDate.getValue()) && date.isBefore(toDate.getValue())) {
                        // NOTE: Since foodConsumptionActivity doesn't have an id column, FKId is set to -1
                        flightEmissions.add(new GeneralEmission("Flight",-1,rs.getString("date"),rs.getString("name"),rs.getDouble("co2")));
                    }
                }else
                {
                    // NOTE: Since foodConsumptionActivity doesn't have an id column, FKId is set to -1
                    flightEmissions.add(new GeneralEmission("Flight",-1,rs.getString("date"),rs.getString("name"),rs.getDouble("co2")));

                }
            }
        }

    }

    private void loadPTData() throws SQLException {
        // TODO: Implement public transport table in database -> (NOT DONE)

        // TODO: Retrieve all PublicTransportdata
    }

    private void loadHouseData() {
        // TODO: Implement house table in database -> (SHOULD BE DONE)

        // TODO: Retrieve all housedata
    }

    private void loadFoodData() throws SQLException {
        foodEmissions.clear();
        String SQLquery = "SELECT foodName, date, round(SUM((co2g*weight) / 1000),2) AS co2 FROM foodConsumptionActivity INNER JOIN foodItem USING(foodID) " +
                "WHERE userID=" + Main.getCurrentUserId() + " GROUP BY foodName, date ORDER BY date ASC;";
        if (dbHandler.connect()) {
            ResultSet rs = dbHandler.execQuery(SQLquery);
            while (rs.next()) {
                if(dateFilterCheck.isSelected()){
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String datetoCheckString = rs.getString("date");

                    //convert String to LocalDate
                    LocalDate date = LocalDate.parse(datetoCheckString, formatter);
                    if (date.isAfter(fromDate.getValue()) && date.isBefore(toDate.getValue())) {
                        // NOTE: Since foodConsumptionActivity doesn't have an id column, FKId is set to -1
                        foodEmissions.add(new GeneralEmission("Food",-1,rs.getString("date"),rs.getString("foodName"),rs.getDouble("co2")));
                    }
                }else
                {
                // NOTE: Since foodConsumptionActivity doesn't have an id column, FKId is set to -1
                foodEmissions.add(new GeneralEmission("Food",-1,rs.getString("date"),rs.getString("foodName"),rs.getDouble("co2")));

                }
            }
        }
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
        transportEmissions.clear();
        //loadFoodData-implementation
        for (TransportActivity transportActivity : transportActivities) {
            if (dateFilterCheck.isSelected()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String datetoCheckString = transportActivity.getDate();

                //convert String to LocalDate
                LocalDate date = LocalDate.parse(datetoCheckString, formatter);
                if (date.isAfter(fromDate.getValue()) && date.isBefore(toDate.getValue())) {
                    //(String category, int FKId, LocalDate date, String title, double emission)
                    transportEmissions.add(new GeneralEmission("Transport", transportActivity.getActivityId(), transportActivity.getDate(), transportActivity.getActivityName(), transportActivity.getCalculatedCO2()));
                }
            }else
            {
                transportEmissions.add(new GeneralEmission("Transport", transportActivity.getActivityId(), transportActivity.getDate(), transportActivity.getActivityName(), transportActivity.getCalculatedCO2()));
            }
        }
    }

    private void setupTable() {
        dateCol.setCellValueFactory(new PropertyValueFactory<GeneralEmission, String>("date"));
        titleCol.setCellValueFactory(new PropertyValueFactory<GeneralEmission, String>("title"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<GeneralEmission, String>("category"));
        co2Col.setCellValueFactory(new PropertyValueFactory<GeneralEmission, Double>("emission"));

        emissionsTableview.setItems(emissions);
    }

    public void filterAll() throws SQLException {
        if(allToggle.isSelected()){
            foodToggle.setSelected(true);
            houseToggle.setSelected(true);
            transportToggle.setSelected(true);
            flightToggle.setSelected(true);
        }else{
            foodToggle.setSelected(false);
            houseToggle.setSelected(false);
            transportToggle.setSelected(false);
            flightToggle.setSelected(false);
        }
        loadData();
    }

    public void filterFood() throws SQLException {
        allToggle.setSelected(false);
        loadData();
    }

    public void filterTransport() throws SQLException {
        allToggle.setSelected(false);
        loadData();
    }

    public void filterHouse() {
        allToggle.setSelected(false);
    }

    public void filterFlight() throws SQLException {
        allToggle.setSelected(false);
        loadData();
    }

    public void checkDateFilter(ActionEvent actionEvent) throws SQLException {
        if(!dateFilterCheck.isSelected()){
            fromDate.setDisable(true);
            toDate.setDisable(true);
        }else
        {
            fromDate.setDisable(false);
            toDate.setDisable(false);
        }
        loadData();
    }

    public void checkDatePickers(ActionEvent actionEvent) throws SQLException {
        if(!fromDate.getValue().isBefore(toDate.getValue())){
            errorText.setVisible(true);
            allToggle.setDisable(true);
            transportToggle.setDisable(true);
            houseToggle.setDisable(true);
            foodToggle.setDisable(true);
        }else{
            errorText.setVisible(false);
            allToggle.setDisable(false);
            transportToggle.setDisable(false);
            houseToggle.setDisable(false);
            foodToggle.setDisable(false);
        }
        loadData();
    }
}
