package org.phish.classes;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.phish.database.DBHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Vehicle {
    private SimpleIntegerProperty vehicleId;
    private SimpleIntegerProperty userId;
    private SimpleIntegerProperty vehicleType;
    private SimpleIntegerProperty fuelType;
    private SimpleDoubleProperty kmLitre;
    private SimpleStringProperty vehicleName;

    private SimpleStringProperty vehicleTypeString;
    private SimpleStringProperty fuelTypeString;

    //To handle the foreign keys to fueltype and vehicletype (to get the strings attached)
    private DBHandler dbHandler = new DBHandler();



    public Vehicle(int vehicleId, int userId, int vehicleType, int fuelType, double kmLitre, String vehicleName) {
        this.vehicleId = new SimpleIntegerProperty(vehicleId);
        this.userId = new SimpleIntegerProperty(userId);
        this.vehicleType = new SimpleIntegerProperty(vehicleType);
        this.fuelType = new SimpleIntegerProperty(fuelType);
        this.kmLitre = new SimpleDoubleProperty(kmLitre);
        this.vehicleName = new SimpleStringProperty(vehicleName);

        //Get vehicle type as a string using a foreign key
        String SQLquery = "SELECT type FROM vehicleType WHERE vehicleTypeId = " + vehicleType;
        try {
            if (dbHandler.connect()) {
                ResultSet rs = dbHandler.execQuery(SQLquery); // Execute query
                this.fuelTypeString=  new SimpleStringProperty(rs.getString("type")); // WTF is this?
            }
        } catch(SQLException e) {
            System.out.println((e.getMessage()));
        }

        //Get fuel type as a string using a foreign key
        SQLquery = "SELECT fuelName FROM fuelType WHERE fuelId = " + fuelType;
        try {
            if (dbHandler.connect()) {
                ResultSet rs = dbHandler.execQuery(SQLquery);
                this.fuelTypeString=  new SimpleStringProperty(rs.getString("fuelName"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Vehicle(int vehicleId, int userId, int vehicleType, int fuelType, double kmLitre, String vehicleName, String vehicleTypeString, String fuelTypeString) {
        this.vehicleId = new SimpleIntegerProperty(vehicleId);
        this.userId = new SimpleIntegerProperty(userId);
        this.vehicleType = new SimpleIntegerProperty(vehicleType);
        this.fuelType = new SimpleIntegerProperty(fuelType);
        this.kmLitre = new SimpleDoubleProperty(kmLitre);
        this.vehicleName = new SimpleStringProperty(vehicleName);
        this.vehicleTypeString = new SimpleStringProperty(vehicleTypeString);
        this.fuelTypeString = new SimpleStringProperty(fuelTypeString);
    }

    public int getVehicleId() {
        return vehicleId.get();
    }

    public SimpleIntegerProperty vehicleIdProperty() {
        return vehicleId;
    }

    public int getUserId() {
        return userId.get();
    }

    public SimpleIntegerProperty userIdProperty() {
        return userId;
    }

    public int getVehicleType() {
        return vehicleType.get();
    }

    public SimpleIntegerProperty vehicleTypeProperty() {
        return vehicleType;
    }

    public int getFuelType() {
        return fuelType.get();
    }

    public SimpleIntegerProperty fuelTypeProperty() {
        return fuelType;
    }

    public double getKmLitre() {
        return kmLitre.get();
    }

    public SimpleDoubleProperty kmLitreProperty() {
        return kmLitre;
    }

    public String getVehicleName() {
        return vehicleName.get();
    }

    public SimpleStringProperty vehicleNameProperty() {
        return vehicleName;
    }

    public String getVehicleTypeString() {
        return vehicleTypeString.get();
    }

    public SimpleStringProperty vehicleTypeStringProperty() {
        return vehicleTypeString;
    }

    public String getFuelTypeString() {
        return fuelTypeString.get();
    }

    public SimpleStringProperty fuelTypeStringProperty() {
        return fuelTypeString;
    }

    @Override
    public String toString(){
        return vehicleName.get();
    }
}
