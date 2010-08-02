/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unknown.entity;

import com.google.common.base.Predicate;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bobo
 */
public class ItemDB implements ItemDAO {

    private Connection connect() throws SQLException {
        String userName = "root", userPassword = "piccolo", databaseURL = "jdbc:mysql://unknown-entity.com:3306/dkp";
        Connection conn = null;
        conn = DriverManager.getConnection(databaseURL, userName, userPassword);
        return conn;
    }

    @Override
    public List<Items> getItems() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CharacterDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        Connection c = null;
        List<Items> items = new ArrayList<Items>();
        try {
            c = connect();
            PreparedStatement p = c.prepareStatement("SELECT * FROM items");
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                items.add(new Items(rs.getString("name"), rs.getInt("wowid"), rs.getDouble("price"), rs.getString("slot"), rs.getString("type"), rs.getBoolean("heroic")));
            }
        } catch(SQLException e) {}
        return items;
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