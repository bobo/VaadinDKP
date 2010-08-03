/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unknown.entity;

import com.mysql.jdbc.Connection;
import com.vaadin.data.Item;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bobo
 */
public class CharacterInfo extends Window {
private final User user;

    public CharacterInfo(User user) {
	this.user = user;
    }


    public void printInfo() {
	addComponent(new Label("Character information"));
	addComponent(new Label("Name: "+user.getUsername()));
        addComponent(new Label("Class: "+user.getRole().toString()));
	addComponent(new Label("Status: "+(user.isActive() ? "Active" : "Inactive")));

	addComponent(new Label("DKP"));
	GridLayout dkpGrid = new GridLayout(2, 4);
	dkpGrid.addComponent(new Label("Shares: "), 0, 0);
	dkpGrid.addComponent(new Label("DKP Earned: "), 0, 1);
	dkpGrid.addComponent(new Label("DKP Spent: "), 0, 2);
	dkpGrid.addComponent(new Label("DKP: "), 0, 3);

	dkpGrid.addComponent(new Label(""+user.getShares()), 1, 0);
	dkpGrid.addComponent(new Label(""+user.getDKPEarned()), 1, 1);
	dkpGrid.addComponent(new Label(""+user.getDKPSpent()), 1, 2);
	dkpGrid.addComponent(new Label(""+user.getDKP()), 1, 3);
	addComponent(dkpGrid);

        Table loots = lootList(user);
        if (loots.size() > 0) {
        addComponent(lootList(user));
        } else {
            addComponent(new Label("No items looted yet."));
        }

    }

    private Connection connect() throws SQLException {
        String userName = "root", userPassword = "piccolo", databaseURL = "jdbc:mysql://unknown-entity.com:3306/dkp";
        Connection conn = null;
        conn = (Connection) DriverManager.getConnection(databaseURL, userName, userPassword);
        return conn;
    }

    private Table lootList(User user) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CharacterDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        Table tbl = new Table();
        tbl.addContainerProperty("Name", String.class, "");
        tbl.addContainerProperty("Price", Double.class, 0);
        tbl.setHeight(150);
        boolean isEmpty = true;
        Connection c = null;
        try {
            c = connect();
            PreparedStatement p = c.prepareStatement("SELECT * FROM loots JOIN items WHERE loots.character_id=? AND loots.item_id=items.id");
            p.setInt(1, user.getID());
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                Item addItem = tbl.addItem(new Integer(rs.getInt("loots.id")));
                addItem.getItemProperty("Name").setValue(rs.getString("items.name"));
                addItem.getItemProperty("Price").setValue(rs.getDouble("loots.price"));
                isEmpty = false;

            }
        } catch(SQLException e) {}

        return tbl;
    }
}
