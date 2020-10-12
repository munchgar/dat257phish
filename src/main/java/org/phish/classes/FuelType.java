package org.phish.classes;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class FuelType {
    private SimpleIntegerProperty fuelTypeId;
    private SimpleStringProperty fuelName;
    private SimpleIntegerProperty gCO2Litre;

    public FuelType(int fuelTypeId, String fuelName, int gCO2Litre) {
        this.fuelTypeId = new SimpleIntegerProperty(fuelTypeId);
        this.fuelName = new SimpleStringProperty(fuelName);
        this.gCO2Litre = new SimpleIntegerProperty(gCO2Litre);
    }

    public int getFuelTypeId() {
        return fuelTypeId.get();
    }

  /*  public SimpleIntegerProperty fuelTypeIdProperty() {
        return fuelTypeId;
    }*/

    public String getFuelName() {
        return fuelName.get();
    }

   /* public SimpleStringProperty fuelNameProperty() {
        return fuelName;
    }*/

    public int getgCO2Litre() {
        return gCO2Litre.get();
    }

   /* public SimpleIntegerProperty gCO2LitreProperty() {
        return gCO2Litre;
    }*/

    //The toString is overridden for use in a Choicebox
    @Override
    public String toString(){
         return fuelName.get();
    }
}
