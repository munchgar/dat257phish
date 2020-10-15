package org.phish.classes;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class GeneralEmission {
    private SimpleIntegerProperty GeneralEmissionId;
    private SimpleIntegerProperty categoryId;
    private SimpleIntegerProperty FKId;
    private LocalDate date;
    private SimpleStringProperty title;
    private SimpleDoubleProperty emission;

    public GeneralEmission(int generalEmissionId, int categoryId, int FKId, LocalDate date, String title, double emission) {
        GeneralEmissionId = new SimpleIntegerProperty(generalEmissionId);
        this.categoryId = new SimpleIntegerProperty(categoryId);
        this.FKId = new SimpleIntegerProperty(FKId);
        this.date = date;
        this.title = new SimpleStringProperty(title);
        this.emission = new SimpleDoubleProperty(emission);
    }

    public int getGeneralEmissionId() {
        return GeneralEmissionId.get();
    }

    public SimpleIntegerProperty generalEmissionIdProperty() {
        return GeneralEmissionId;
    }

    public int getCategoryId() {
        return categoryId.get();
    }

    public SimpleIntegerProperty categoryIdProperty() {
        return categoryId;
    }

    public int getFKId() {
        return FKId.get();
    }

    public SimpleIntegerProperty FKIdProperty() {
        return FKId;
    }

    public LocalDate getDate() {
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
