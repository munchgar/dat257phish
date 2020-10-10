package org.phish.classes;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class VehicleType {
    private SimpleIntegerProperty vehicleTypeId;
    private SimpleStringProperty vehicleTypeName;

    public VehicleType(int vehicleTypeId, String vehicleTypeName) {
        this.vehicleTypeId = new SimpleIntegerProperty(vehicleTypeId);
        this.vehicleTypeName = new SimpleStringProperty(vehicleTypeName);
    }

    public int getVehicleTypeId() {
        return vehicleTypeId.get();
    }

    /*public SimpleIntegerProperty vehicleTypeIdProperty() {
        return vehicleTypeId;
    }*/

    public String getVehicleTypeName() {
        return vehicleTypeName.get();
    }

    /*public SimpleStringProperty vehicleTypeNameProperty() {
        return vehicleTypeName;
    }*/

    //The toString is overridden for use in a Choicebox
    @Override
    public String toString(){
        return vehicleTypeName.get();
    }
}
