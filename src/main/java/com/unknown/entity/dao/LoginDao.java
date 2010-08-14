/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity.dao;

import com.unknown.entity.DBConnection;
import com.unknown.entity.character.SiteUser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author bobo
 */
public class LoginDao implements ILoginDao {

	@Override
	public SiteUser checkLogin(String username, String password) {

		Connection conn = null;
		DBConnection dBConnection = new DBConnection();
		try {
			conn = dBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT rank from users where name=? and password = ? limit 1");
			ps.setString(1, username);
			ps.setString(2, password);
			final ResultSet res = ps.executeQuery();
			if (res.next()) {
				final int rank = res.getInt("rank");
				return new SiteUser() {
					@Override
					public int getLevel() {
							return rank;
					}
				};

			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dBConnection.closeConnection(conn);
		}
		return null;

	}
}
