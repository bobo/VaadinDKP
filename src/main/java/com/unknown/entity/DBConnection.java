/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unknown.entity;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alde
 */
public final class DBConnection {
    Connection conn = null;
	private static Properties properties;
	
    private Connection connect() throws SQLException {
		conn = DriverManager.getConnection(properties.getProperty("db.url"), properties.getProperty("db.username"), properties.getProperty("db.password"));
        return conn;
    }
    public Connection getConnection() throws SQLException {
		return connect();
    }

	public static void setPropertisPath(String propertisPath) {
		DBConnection.properties = getProperties(propertisPath);
	}
	private static Properties getProperties(String propertiesPath) {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(propertiesPath));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return prop;
	}

	public void closeConnection(Connection conn) {



		if (conn != null) {
			try {
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}


}
