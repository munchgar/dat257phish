package org.phish.classes;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Vehicle {
    private SimpleIntegerProperty vehicleId;
    private SimpleIntegerProperty userId;
    private SimpleIntegerProperty vehicleType;
    private SimpleIntegerProperty fuelType;
    private SimpleIntegerProperty kmLitre;
    private SimpleStringProperty vehicleName;

    public Vehicle(int vehicleId, int userId, int vehicleType, int fuelType, int kmLitre, String vehicleName) {
        this.vehicleId = new SimpleIntegerProperty(vehicleId);
        this.userId = new SimpleIntegerProperty(userId);
        this.vehicleType = new SimpleIntegerProperty(vehicleType);
        this.fuelType = new SimpleIntegerProperty(fuelType);
        this.kmLitre = new SimpleIntegerProperty(kmLitre);
        this.vehicleName = new SimpleStringProperty(vehicleName);
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

    public int getKmLitre() {
        return kmLitre.get();
    }

    public SimpleIntegerProperty kmLitreProperty() {
        return kmLitre;
    }

    public String getVehicleName() {
        return vehicleName.get();
    }

    public SimpleStringProperty vehicleNameProperty() {
        return vehicleName;
    }

    @Override
    public String toString(){
        return vehicleName.get();
    }
}
