package org.phish.classes;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class User {
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty co2;

    public User(String name, int co2) {
        this.name = new SimpleStringProperty(name);
        this.co2 = new SimpleIntegerProperty(co2);
    }

    public String getName() {
        return name.get();
    }

   /* public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }*/

    public Integer getCo2() {
        return co2.get();
    }

    /*public void setCo2(int co2) {
        this.co2 = new SimpleIntegerProperty(co2);
    }*/
}
