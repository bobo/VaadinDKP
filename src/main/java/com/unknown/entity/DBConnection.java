/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alde
 */
public final class DBConnection {

	private Connection conn = null;
	private PreparedStatement ps = null;
	private Properties properties;

	public DBConnection() {
		properties = getProperties();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	private Connection connect()  {
		try {
			close();
			conn = DriverManager.getConnection("jdbc:mysql://" + properties.getProperty("db.url") + ":" + properties.getProperty("db.port") + "/" + properties.getProperty("db.db"), properties.getProperty("db.username"), properties.getProperty("db.password"));
			return conn;
		} catch (SQLException ex) {
			throw new SQLRuntimeException(ex);
		}
	}

	public Connection getConnection() throws SQLException {
		return connect();
	}

	public PreparedStatement prepareStatement(String string) {
		connect();
		try {

			ps = conn.prepareStatement(string);
		} catch (SQLException ex) {
			throw new SQLRuntimeException(ex);
		}
		return ps;
	}

	public void close() {
		closeStatement();
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException ex) {
			//Ignore
		}
	}

	public void closeStatement() {
		if (ps != null) {
			try {
				if (ps.getResultSet() != null) {
					ps.getResultSet().close();
				}
			} catch (SQLException ex) {
				//Ignore
			}
			try {
				ps.close();
			} catch (SQLException ex) {
				//Ignore
			}
		}
	}

	private Properties getProperties() {
		Properties prop = verifyPropertiesFile();
		return prop;
	}

	private Properties verifyPropertiesFile() {
		Properties prop = new Properties();
		File f = null;
		boolean notFound = true;
		while (notFound) {
			try {
				f = new File("db.properties");
				final FileInputStream fileInputStream = new FileInputStream(f);
				prop.load(fileInputStream);
				fileInputStream.close();
				notFound = verifyProperties(prop);

			} catch (Exception ex) {
				notFound = true;

			}
			if (notFound) {
				printError(f);
				try {
					Thread.sleep(3000);
				} catch (InterruptedException ex2) {
					ex2.printStackTrace();
				}
			}

		}
		return prop;
	}

	private void printError(File f) {
		System.out.println("please create " + f.getAbsolutePath());
		System.out.println("format: db.url = mydomain.com ");
		System.out.println("db.username= nils");
		System.out.println("db.db = mydb");
		System.out.println("db.port=1234");
		System.out.println("db.password = secret");
	}

	private boolean verifyProperties(Properties prop) {
		if (prop.getProperty("db.url") != null && prop.getProperty("db.port") != null && prop.getProperty("db.username") != null && prop.getProperty("db.password") != null && prop.getProperty("db.db") != null) {
			return false;
		}
		return true;
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

	public PreparedStatement prepareStatement(String string, int options) {
		connect();
		try {

			ps = conn.prepareStatement(string, options);
		} catch (SQLException ex) {
			throw new SQLRuntimeException(ex);
		}
		return ps;

	}
}
