package org.phish.database;

import java.sql.*;

public class DBHandler {

    //private static final String url = "jdbc:sqlite:src/main/java/org/phish/database/userDB.db";
    private static final String url = "jdbc:sqlite:src/main/java/org/phish/database/userDBv2.db";
    Connection conn;


    public /*static*/ void Testconnection() {
        Connection conn = null;
        try {
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }


    public boolean connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        this.conn = conn;
        return true;
    }

    public boolean execUpdate(String query) throws SQLException {
        Statement stmt  = conn.createStatement();
        stmt.executeUpdate(query);
        conn = null; // Kill connection
        return true;
    }

    public ResultSet execQuery(String query) throws SQLException {

        Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery(query);
        conn = null; // Kill connection
        return rs;
    }

    public Connection getConn() {
        return conn;
    }
}