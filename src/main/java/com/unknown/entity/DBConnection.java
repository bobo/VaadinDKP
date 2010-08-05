/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unknown.entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author alde
 */
public final class DBConnection {
    Connection conn = null;

    private Connection connect() throws SQLException {
        String userName = "root", userPassword = "piccolo", databaseURL = "jdbc:mysql://unknown-entity.com:3306/dkp";
        conn = DriverManager.getConnection(databaseURL, userName, userPassword);
        return conn;
    }
    public DBConnection() throws SQLException {
        this.conn = connect();
    }
    public Connection getConnection() {
        return conn;
    }
}
