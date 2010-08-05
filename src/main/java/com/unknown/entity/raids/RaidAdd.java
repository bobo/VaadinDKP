/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity.raids;

import com.unknown.entity.DBConnection;
import com.unknown.entity.character.CharacterDB;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alde
 */
public class RaidAdd extends Window {

    public RaidAdd() {
    }

    public void printInfo() {

        VerticalLayout addItem = new VerticalLayout();
        addComponent(addItem);
        final ComboBox zone = new ComboBox("Zone");
        final TextField comment = new TextField("Comment");
        final TextField datum = new TextField("Date");

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CharacterDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        Connection c = null;
        try {
            c = new DBConnection().getConnection();
            PreparedStatement ps = c.prepareStatement("INSERT INTO raids (zone_id, date, comment) VALUES(?,?,?)");
            PreparedStatement pzone = c.prepareStatement("SELECT * FROM zones");
            ResultSet rzone = pzone.executeQuery();
            while (rzone.next()) {
                zone.addItem(rzone.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CharacterDB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        zone.setImmediate(true);
        zone.setNullSelectionAllowed(false);
        Collection<?> itemIds = zone.getItemIds();
        zone.setValue(itemIds.iterator().next());
        comment.focus();
        comment.setImmediate(true);
        datum.setImmediate(true);

        addItem.addComponent(zone);
        addItem.addComponent(comment);
        addItem.addComponent(datum);
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        datum.setValue(dateFormat.format(date));

        final Button btn = new Button("Add");
        btn.addListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                String rzone = zone.getValue().toString();
                String rcomment = comment.getValue().toString();
                String rdate = datum.getValue().toString();
                int success = addRaid(rzone, rcomment, rdate);
              addComponent(new Label("Update :"+success));
            }
        });
        final Button cbtn = new Button("Close");
        cbtn.addListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                close();
            }
        });
        HorizontalLayout hzl = new HorizontalLayout();
        hzl.setSpacing(true);
        hzl.addComponent(btn);
        hzl.addComponent(cbtn);
        addItem.addComponent(hzl);
    }

    private int addRaid(String zone, String comment, String date) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CharacterDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        Connection c = null;
        int result = 0;
        int zoneId = 0;

        try {
            c = new DBConnection().getConnection();
            PreparedStatement ps = c.prepareStatement("INSERT INTO raids (zone_id, date, comment) VALUES(?,?,?)");
            PreparedStatement pzone = c.prepareStatement("SELECT * FROM zones WHERE name=?");
            pzone.setString(1, zone);
            ResultSet rzone = pzone.executeQuery();
            while (rzone.next()) {
                zoneId = rzone.getInt("id");
            }
            ps.setInt(1, zoneId);
            ps.setString(2, date);
            ps.setString(3, comment);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CharacterDB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return result;
    }
}
