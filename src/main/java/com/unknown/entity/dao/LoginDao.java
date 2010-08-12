/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unknown.entity.dao;

import com.unknown.entity.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author bobo
 */
public class LoginDao {



	public boolean checkLogin() {

		Connection conn = null;
		DBConnection dBConnection = new DBConnection();
		try {
			conn = dBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * from tblUsers where username=? and password = ? limit 1");
			ResultSet res = ps.executeQuery();
			if (res.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dBConnection.closeConnection(conn);
		}
		return false;

	}


}
