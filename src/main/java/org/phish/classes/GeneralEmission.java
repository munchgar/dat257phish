package org.phish.classes;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class GeneralEmission {
    private SimpleIntegerProperty GeneralEmissionId;
    private SimpleStringProperty category;
    private SimpleIntegerProperty FKId;
    private SimpleStringProperty date;
    private SimpleStringProperty title;
    private SimpleDoubleProperty emission;

    public GeneralEmission(/*int generalEmissionId,*/ String category, int FKId, String date, String title, double emission) {
        //GeneralEmissionId = new SimpleIntegerProperty(generalEmissionId);
        this.category = new SimpleStringProperty(category);
        this.FKId = new SimpleIntegerProperty(FKId);
        this.date = new SimpleStringProperty(date);
        this.title = new SimpleStringProperty(title);
        this.emission = new SimpleDoubleProperty(emission);
    }

   /* public int getGeneralEmissionId() {
        return GeneralEmissionId.get();
    }

    public SimpleIntegerProperty generalEmissionIdProperty() {
        return GeneralEmissionId;
    }*/

    public String getCategory() {
        return category.get();
    }

    public  SimpleStringProperty categoryProperty() {
        return category;
    }

    public int getFKId() {
        return FKId.get();
    }

    public SimpleIntegerProperty FKIdProperty() {
        return FKId;
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty getDateProperty(){
        return date;
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public double getEmission() {
        return emission.get();
    }

    public SimpleDoubleProperty emissionProperty() {
        return emission;
    }

    @Override
    public String toString(){
        return title.get();
    }
}
