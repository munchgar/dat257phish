package org.phish.classes;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.phish.database.DBHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class User {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty fName;
    private final SimpleStringProperty lName;

    private DBHandler dbHandler = new DBHandler();

    public User(int id, String fName, String lName) {
        this.id = new SimpleIntegerProperty(id);
        this.fName = new SimpleStringProperty(fName);
        this.lName = new SimpleStringProperty(lName);
    }

    public boolean register() {
        // Attempt to register via the database
        String SQLquery = "SELECT fName, lName FROM userTable"; // Do we need * or only fName and lName?
        try (Connection conn = dbHandler.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(SQLquery)){
            while(rs.next()){
                try {
                    if (rs.getString("fName").equals(fName) && rs.getString("lName").equals(lName)) {
                        System.out.println("USER ALREADY EXISTS");
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return false; // If unable to register return false
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
