package org.phish.classes;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class FoodItem {
    private SimpleIntegerProperty foodID;
    private SimpleStringProperty foodName;
    private SimpleDoubleProperty co2g;

    public FoodItem(int foodID, String foodName, double co2g) {
        this.foodID = new SimpleIntegerProperty(foodID);
        this.foodName = new SimpleStringProperty(foodName);
        this.co2g = new SimpleDoubleProperty(co2g);
    }

    public int getID() {
        return foodID.get();
    }

    public String getName() {
        return foodName.get();
    }

    public Double getCo2g() {
        return co2g.get();
    }


    @Override
    public String toString() {
        return foodName.get();
    }
}

