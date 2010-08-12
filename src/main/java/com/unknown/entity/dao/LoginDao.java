/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unknown.entity.dao;

import com.unknown.entity.DBConnection;
import com.unknown.entity.character.SiteUser;
import com.unknown.entity.character.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
			PreparedStatement ps = conn.prepareStatement("SELECT id from users where name=? and password = ? limit 1");
			ps.setString(1, username);
			ps.setString(2, password);
			final ResultSet res = ps.executeQuery();
			if (res.next()) {

				return new SiteUser() {

					@Override
					public int getLevel() {
                                                try {
                                                        return res.getInt("rank");
                                                } catch (SQLException ex) {
                                                        Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, null, ex);
                                                }
                                                return 0;
					}
				};
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dBConnection.closeConnection(conn);
		}
		return null;

	}


}
