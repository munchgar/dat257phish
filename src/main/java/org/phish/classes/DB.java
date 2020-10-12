package org.phish.classes;

import java.sql.*;
import java.util.ArrayList;

public interface DB {

    static final String url = "jdbc:sqlite:src/main/java/org/phish/database/userDB.db";

    static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    static ResultSet select(String retrieve, String table) {
        String SQLquery = "SELECT username FROM userTable"; // Do we need * or only fName and lName?
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(SQLquery)){
            while(rs.next()){
                try {
                    return rs;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}