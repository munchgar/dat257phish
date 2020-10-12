package org.phish.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.phish.Main;
import org.phish.classes.TransportActivity;
import org.phish.classes.User;
import org.phish.classes.Vehicle;
import org.phish.classes.VehicleType;
import org.phish.database.DBHandler;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class AddActivityController implements Initializable {
    
    private final ObservableList <VehicleType> vehicleTypes = FXCollections.observableArrayList();
    private final ObservableList <Vehicle> vehicles = FXCollections.observableArrayList();
    private final ObservableList <Vehicle> personalVehicles = FXCollections.observableArrayList(); //todo make dynamic drop down list

    private final DBHandler dbHandler = new DBHandler();

    private Calendar calendar = Calendar.getInstance();

    @FXML
    private ChoiceBox<VehicleType> transportTypeBox;
    @FXML
    private ChoiceBox<Vehicle> vehicleChoiceBox;
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
    private Spinner<Integer> distanceSpinner;
    @FXML
    private Spinner<Integer> timesDaySpinner;
    @FXML
    private Text errorText;

    public void updateVehiclesBox(ActionEvent actionEvent) {
        //todo if the personal vehicles is selected in the choicebox it shjould update the vehicles available and set it to the personal vehicles list
    }


    public void updateReoccurring(ActionEvent actionEvent) {
        if(reoccurringCheckBox.isSelected()){
            setReoccurringVisible();
        }else{
            setReoccurringInvisible();
        }
    }

    public void addBtnPressed(ActionEvent actionEvent) throws SQLException {

        //todo fail safe
        if (!titleField.getText().isBlank()) {
            errorText.setVisible(false);


            //TransportActivity transportActivityToAdd = new TransportActivity(1, Main.getCurrentUserId(), distanceSpinner.getValue(), datePicker.getValue().toString(), titleField.getText(), vehicleChoiceBox.getSelectionModel().getSelectedItem());
            //System.out.println(transportActivityToAdd.toString() + " "+ transportActivityToAdd.getCalculatedCO2());

            //todo tests the reoccurring items
            if (reoccurringCheckBox.isSelected()) {
                //todo check for additional fields related to the reoccurring components

            }
            String sql = "INSERT INTO transportActivity (FKuserId, activityName, distanceKm, FKVehicleId, date) VALUES(?, ?, ?, ?, ?)";


            if (dbHandler.connect()) {
                PreparedStatement pstmt = dbHandler.getConn().prepareStatement(sql);
                System.out.println(distanceSpinner.getValue());
                pstmt.setInt(1, Main.getCurrentUserId());
                pstmt.setString(2, titleField.getText());
                pstmt.setInt(3, distanceSpinner.getValue());
                pstmt.setInt(4, vehicleChoiceBox.getSelectionModel().getSelectedItem().getVehicleId());
                pstmt.setString(5, datePicker.getValue().toString());
                pstmt.executeUpdate();
                System.out.println("Activity successfully added to DB");

            } else {
                errorText.setText("All fields must be entered");
                errorText.setVisible(true);
                errorText.setFill(Color.RED);
            }
        }
    }

        @Override
    public void initialize(URL location, ResourceBundle resources) {
        setReoccurringInvisible();
            try {
                setUpVehicleBox();
                setUpVehicleTypeBox();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            setUpSpinners();
        //Sets the datepicker to the current date
        LocalDate ld =  LocalDate.now();
        datePicker.setValue(ld);
        //The datePicker is set to not be editable as it is too time-consuming to add fail safes for user interaction
        datePicker.setEditable(false);
        errorText.setVisible(false);
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
    private void setUpVehicleTypeBox() throws SQLException {
        vehicleTypes.clear();
        //VehicleTypeBox
        String SQLquery = "SELECT * FROM vehicleType";
        if(dbHandler.connect()) {
            ResultSet rs = dbHandler.execQuery(SQLquery);
            while(rs.next()){
                try {
                    vehicleTypes.add(new VehicleType(rs.getInt("vehicleTypeId"), rs.getString("type")));
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
            transportTypeBox.setItems(vehicleTypes);
            transportTypeBox.setValue(vehicleTypes.get(0));
        } else {
            System.out.println("Could not execute query!");
        }

    }
    private void setUpVehicleBox() throws SQLException {
        vehicles.clear();
        //Todo if the selected vehicle type is something else than personal the relevant vehicles should be loaded.
        //todo if the selected vehicle type is personal all the personal vehicles should be loaded. (as follows below)
        String SQLquery = "SELECT * FROM vehicles WHERE FKuserId=" +Main.getCurrentUserId();

        if(dbHandler.connect()) {
            ResultSet rs = dbHandler.execQuery(SQLquery);
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
        } else {
            System.out.println("Could not execute query!");
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
    public void cancel(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }
}
