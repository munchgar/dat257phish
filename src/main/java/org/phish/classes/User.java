package org.phish.classes;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.phish.database.DBHandler;

import java.sql.*;

public class User {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty fName;
    private final SimpleStringProperty lName;

    private DBHandler dbHandler = DBHandler.getInstance();

    public User(int id, String fName, String lName) {
        this.id = new SimpleIntegerProperty(id);
        this.fName = new SimpleStringProperty(fName);
        this.lName = new SimpleStringProperty(lName);
    }

    public int register(String username, String password) throws SQLException {
        if (password.length() == 0 || username.length() == 0) {
            return -1;
        }

        // Attempt to register via the database
        String SQLquery = "SELECT username FROM userTable"; // Do we need * or only fName and lName?
        if (dbHandler.connect()) { // Attempt to connect to database.
            ResultSet rs = dbHandler.execQuery(SQLquery); // Execute query
            while (rs.next()) {
                try {
                    if (rs.getString("username").equals(username) && username.length() > 0) {
                        System.out.println("USER ALREADY EXISTS");
                        return 0;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            String input = fName.getName() + ", " + lName.getName() + ", " + username + ", " + password;
            String query = "INSERT INTO userTable (fName, lName, username, password) VALUES (" + input + ")";

            dbHandler.execQuery(query);
            System.out.println("User successfully added to DB");


        }
        return 0; // If unable to register return false
    }

    public int getId() {
        return id.get();
    }

  /*  public SimpleIntegerProperty idProperty() {
        return id;
    } */

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
