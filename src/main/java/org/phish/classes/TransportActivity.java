package org.phish.classes;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.phish.Main;
import org.phish.database.DBHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TransportActivity {
    private SimpleIntegerProperty activityId;
    private SimpleIntegerProperty userId;
    //private SimpleIntegerProperty vehicleId; //unnecessary?
    private SimpleIntegerProperty distance;
    private SimpleStringProperty date;
    private SimpleStringProperty activityName;
    private SimpleStringProperty title;

    //Assigned from a Vehicle object
    private SimpleIntegerProperty vehicleId;
    private SimpleStringProperty vehicleName;
    private SimpleDoubleProperty efficiency;

    //Calculated
    private SimpleDoubleProperty calculatedCO2;

    //private Vehicle vehicle; //Ugly way to do it //todo

    //Maybe reoccurring can extend this class?
    private SimpleBooleanProperty reoccurring;


    public TransportActivity(int activityId  ,int userId, int distance, String date, String activityName, Vehicle vehicle) {
        this.activityId = new SimpleIntegerProperty(activityId);
        //this.title = new SimpleStringProperty(title);
        this.userId = new SimpleIntegerProperty(userId);
        this.vehicleId = new SimpleIntegerProperty(vehicle.getVehicleId());
        this.distance = new SimpleIntegerProperty(distance);
        this.date = new SimpleStringProperty(date);
        this.activityName = new SimpleStringProperty(activityName);
        this.vehicleName = new SimpleStringProperty(vehicle.getVehicleName());
        this.efficiency = new SimpleDoubleProperty(vehicle.getKmLitre());
        this.calculatedCO2 = new SimpleDoubleProperty(efficiency.get()*distance);
        this.reoccurring = new SimpleBooleanProperty(false);
    }

    public int getActivityId() {
        return activityId.get();
    }

    public SimpleIntegerProperty activityIdProperty() {
        return activityId;
    }

    public int getDistance() {
        return distance.get();
    }

    public SimpleIntegerProperty distanceProperty() {
        return distance;
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public int getVehicleId() {
        return vehicleId.get();
    }

    public SimpleIntegerProperty vehicleIdProperty() {
        return vehicleId;
    }

    public String getVehicleName() {
        return vehicleName.get();
    }

    public SimpleStringProperty vehicleNameProperty() {
        return vehicleName;
    }

    public double getCalculatedCO2() {
        return calculatedCO2.get();
    }

    public SimpleDoubleProperty calculatedCO2Property() {
        return calculatedCO2;
    }

    public String getActivityName() {
        return activityName.get();
    }

    public SimpleStringProperty activityNameProperty() {
        return activityName;
    }

    public boolean isReoccurring() {
        return reoccurring.get();
    }

    public SimpleBooleanProperty reoccurringProperty() {
        return reoccurring;
    }

    public void setReoccurring(boolean reoccurring) {
        this.reoccurring.set(reoccurring);
    }

    @Override
    public String toString(){
        return activityName.get();
    }
}
