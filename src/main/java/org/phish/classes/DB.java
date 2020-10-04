package org.phish.classes;

import javafx.scene.control.TextField;

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


    static boolean register(String username, String password) {
        String sql = "INSERT INTO userTable (username, password) VALUES(?,?)";
        try {
            try (Connection conn = connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) { // pstmt : Variable name?
                pstmt.setString(1, (username));
                pstmt.setString(2, password);
                pstmt.executeUpdate();
                System.out.println("User successfully added to DB");
            }
            return true;

        } catch (Error | SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }


//    static ResultSet select(String retrieve, String table) {
//
//        String SQLquery = "SELECT fName, lName FROM userTable"; // Do we need * or only fName and lName?
//        try (Connection conn = connect();
//             Statement stmt  = conn.createStatement();
//             ResultSet rs    = stmt.executeQuery(SQLquery)){
//            while(rs.next()){
//                try {
//                    return rs;
//                }catch (Exception ex){
//                    ex.printStackTrace();
//                }
//            }
//        }catch (SQLException e){
//            System.out.println(e.getMessage());
//        }
//
//        return null;
//    }

}