package org.phish.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.phish.Main;
import org.phish.classes.Vehicle;
import org.phish.classes.VehicleType;
import org.phish.database.DBHandler;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddActivityController implements Initializable {


    private final ObservableList <VehicleType> vehicleTypes = FXCollections.observableArrayList();
    private final ObservableList <Vehicle> vehicles = FXCollections.observableArrayList();
    private final ObservableList <Vehicle> personalVehicles = FXCollections.observableArrayList(); //todo make dynamic drop down list

    private final DBHandler dbHandler = new DBHandler();

    @FXML
    private ChoiceBox transportTypeBox, vehicleChoiceBox;
    @FXML
    private TextField titleField;
    @FXML
    private CheckBox reoccurringCheckBox;
    @FXML
    private ToggleButton monBtn, tuesBtn, wedBtn, thurBtn, friBtn, satBtn, sunBtn;
    @FXML
    private Button cancelBtn, addBtn;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Spinner distanceSpinner, timesDaySpinner;



    public void updateVehiclesBox(ActionEvent actionEvent) {

    }

    public void updateReoccurring(ActionEvent actionEvent) {
        if(reoccurringCheckBox.isSelected()){
            setReoccurringVisible();
        }else{
            setReoccurringInvisible();
        }
    }

    public void cancel(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    public void addBtnPressed(ActionEvent actionEvent) {
        //tests first if the non-reoccurring items are filled

            transportTypeBox.getSelectionModel().getSelectedItem();
            vehicleChoiceBox.getSelectionModel().getSelectedItem();
            titleField.getText().toString();

            //tests the reoccurring items
            if(reoccurringCheckBox.isSelected()){

            } else
    {
        //todo errormessage
    }
        }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setReoccurringInvisible();
        setUpVehicleTypeBox();
        setUpVehicleBox();
        setUpSpinners();
        //Sets the datepicker to the current date
        LocalDate ld =  LocalDate.now();
        datePicker.setValue(ld);
        //The datePicker is set to not be editable as it is too time-consuming to add fail safes for user interaction
        datePicker.setEditable(false);
    }

    //Sets up the distance spinner and the times per day spinner
    private void setUpSpinners() {
        //Distance spinenr
        SpinnerValueFactory<Integer> distanceValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 10, 5);
        distanceSpinner.setValueFactory(distanceValueFactory);
        distanceSpinner.setEditable(true); //todo make sure it only accepts integers

        //times per day spinner
        SpinnerValueFactory<Integer> timesDayValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        timesDaySpinner.setValueFactory(timesDayValueFactory);
        //timesDaySpinner.setEditable(true); //todo make sure it only accepts integers
    }
    private void setUpVehicleTypeBox() {
        vehicleTypes.clear();
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
            }
            transportTypeBox.setItems(vehicleTypes);
            transportTypeBox.setValue(vehicleTypes.get(0));
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    private void setUpVehicleBox() {
        vehicles.clear();
        //Todo if the selected vehicle type is something else than personal the relevant vehicles should be loaded.
        //todo if the selected vehicle type is personal all the personal vehicles should be loaded. (as follows below)
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
            vehicleChoiceBox.setItems(vehicles);
            vehicleChoiceBox.setValue(vehicles.get(0));
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }
    //sets the reoccurring activities components invisible
    private void setReoccurringInvisible() {
        //todo set these to disable
        monBtn.setDisable(true);
        tuesBtn.setDisable(true);
        wedBtn.setDisable(true);
        thurBtn.setDisable(true);
        friBtn.setDisable(true);
        satBtn.setDisable(true);
        sunBtn.setDisable(true);
        timesDaySpinner.setDisable(true);
    }
    //When the checkbox for reoccurring activities is ticked the relevant components become visible
    private void setReoccurringVisible() {
        //todo set these to disable
        monBtn.setDisable(false);
        tuesBtn.setDisable(false);
        wedBtn.setDisable(false);
        thurBtn.setDisable(false);
        friBtn.setDisable(false);
        satBtn.setDisable(false);
        sunBtn.setDisable(false);
        timesDaySpinner.setDisable(false);
    }
}
