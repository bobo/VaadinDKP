/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unknown.entity;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author alde
 */
public class RealDB implements CharacherDAO {

        public Connection connect() throws SQLException {
        String userName = "root", userPassword = "piccolo", databaseURL = "jdbc:mysql://unknown-entity.com:3306/dkp";
        Connection conn = null;
        conn = DriverManager.getConnection(databaseURL, userName, userPassword);
        return conn;
    }

    @Override
    public List<User> getUsers() {
        Connection c = null;
        try {
            c = connect();
            PreparedStatement p = c.prepareStatement("SELECT * FROM characters");
            ResultSet rs = p.executeQuery();
            Object[] rows;
            while (rs.next()) {
                rows = new Object[]{rs.getString(1), rs.getInt(2), rs.getInt(3)};
                Arrays.asList(rows);
            }
        } catch (SQLException e) {
        } finally {
            if (c != null) {
                c.close();
            }
        }
        
    return Arrays.asList();
    }

    @Override
    public Collection<User> getUsersWithRole(final Role role) {
    return Collections2.filter(getUsers(), new HasRolePredicate(role));
    }

    private static class HasRolePredicate implements Predicate<User> {

	private final Role role;

	public HasRolePredicate(Role role) {
	    this.role = role;
	}

	@Override
	public boolean apply(User user) {
	    return user.getRole().equals(role);
	}
    }

}
