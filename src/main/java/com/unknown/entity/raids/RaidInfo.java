/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unknown.entity.raids;

import com.unknown.entity.DBConnection;
import com.unknown.entity.character.CharacterDB;
import com.vaadin.data.Item;
import com.vaadin.ui.HorizontalLayout;
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

        HorizontalLayout hzl = new HorizontalLayout();
        hzl.setSpacing(true);
        Table Attendants = charList(raid);
        if (Attendants.size() > 0) {
            hzl.addComponent(Attendants);
        } else {
            hzl.addComponent(new Label("No members in this raid."));
        }
     
        Table Loots = lootList(raid);
        if (Loots.size() > 0) {
            hzl.addComponent(Loots);
        } else {
            hzl.addComponent(new Label("No loot in this raid."));
        }
        addComponent(hzl);
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
                 c = new DBConnection().getConnection();
                 PreparedStatement p = c.prepareStatement("SELECT * FROM rewards JOIN character_rewards JOIN characters ON rewards.raid_id=? AND rewards.id=character_rewards.reward_id AND character_rewards.character_id=characters.id");
            p.setInt(1, raid.getID());
            ResultSet rs = p.executeQuery();
            int i=0;
            while (rs.next()) {
                i++;
                Item addItem = tbl.addItem(new Integer(i));
                addItem.getItemProperty("Name").setValue(rs.getString("characters.name"));
            }
        } catch(SQLException e) {}

        return tbl;
    }

    private Table lootList(Raids raid) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CharacterDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        Table tbl = new Table();
        tbl.addContainerProperty("Name", String.class, "");
        tbl.addContainerProperty("Item", String.class, "");
        tbl.addContainerProperty("Price", Double.class, 0);
        tbl.addContainerProperty("Heroic", String.class, "");
        tbl.setHeight(150);
        Connection c = null;
        try {
            c = new DBConnection().getConnection();
            PreparedStatement p = c.prepareStatement("SELECT * FROM loots JOIN raids JOIN characters JOIN items WHERE loots.raid_id=raids.id AND character_id=characters.id AND loots.item_id=items.id AND raids.id=?");
            p.setInt(1, raid.getID());
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                Item addItem = tbl.addItem(new Integer(rs.getInt("raids.id")));
                addItem.getItemProperty("Name").setValue(rs.getString("characters.name"));
                addItem.getItemProperty("Item").setValue(rs.getString("items.name"));
                addItem.getItemProperty("Price").setValue(rs.getDouble("loots.price"));
                addItem.getItemProperty("Heroic").setValue(rs.getBoolean("loots.heroic"));
            }
        } catch(SQLException e) {}

        return tbl;
    }
}

