package org.phish.classes;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class User {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty fName;
    private final SimpleStringProperty lName;

    public User(int id, String fName, String lName) {
        this.id = new SimpleIntegerProperty(id);
        this.fName = new SimpleStringProperty(fName);
        this.lName = new SimpleStringProperty(lName);
    }

    public int getId() {
        return id.get();
    }

  /*  public SimpleIntegerProperty idProperty() {
        return id;
    }*/

    public String getFName() {
        return fName.get();
    }

    /*public SimpleStringProperty FNameProperty() {
        return FName;
    }*/

    public String getLName() {
        return lName.get();
    }

    /*public SimpleStringProperty lNameProperty() {
        return lName;
    }*/
}
