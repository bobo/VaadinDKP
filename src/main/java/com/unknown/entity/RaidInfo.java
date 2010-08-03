/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unknown.entity;

import com.vaadin.data.Item;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alde
 */
public class RaidInfo extends Window {

    private final Raids raid;

    public RaidInfo(Raids raid) {
        this.raid = raid;
    }

    public void printInfo() {
        addComponent(new Label("Raid information"));

        addComponent(new Label("Zone: " + raid.getName()));
        addComponent(new Label("Comment: " + raid.getComment()));
        addComponent(new Label("Date: " + raid.getDate()));

        Table Attendants = charList(raid);
        if (Attendants.size() > 0) {
            addComponent(Attendants);
        } else {
            addComponent(new Label("No members in that raid."));
        }
    }

    private Connection connect() throws SQLException {
        String userName = "root", userPassword = "piccolo", databaseURL = "jdbc:mysql://unknown-entity.com:3306/dkp";
        Connection conn = null;
        conn = (Connection) DriverManager.getConnection(databaseURL, userName, userPassword);
        return conn;
    }

    private Table charList(Raids raid) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CharacterDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        Table tbl = new Table();
        tbl.addContainerProperty("Name", String.class, "");
        tbl.setHeight(150);
        Connection c = null;
        try {
            c = connect();
            PreparedStatement p = c.prepareStatement("SELECT * FROM rewards JOIN character_rewards JOIN characters ON rewards.raid_id=? AND rewards.id=character_rewards.reward_id AND character_rewards.character_id=characters.id");
            p.setInt(1, raid.getID());
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                Item addItem = tbl.addItem(new Integer(rs.getInt("character_rewards.id")));
                addItem.getItemProperty("Name").setValue(rs.getString("characters.name"));
            }
        } catch(SQLException e) {}

        return tbl;
    }
}

