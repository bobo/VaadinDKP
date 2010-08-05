/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.relation.RoleStatus;

/**
 *
 * @author alde
 */
public class CharacterAdd extends Window {

    public CharacterAdd() {
    }

    public void printInfo() {

        VerticalLayout adduser = new VerticalLayout();
        addComponent(adduser);
        final TextField nameField = new TextField("Name");
        nameField.setImmediate(true);
        nameField.focus();
        adduser.addComponent(nameField);
        final ComboBox classCombo = new ComboBox("Class");
        classCombo.setImmediate(true);
        for (Role roles : Role.values()) {
            classCombo.addItem(roles);
        }
        adduser.addComponent(classCombo);

        final CheckBox activeCheck = new CheckBox("active", true);
        activeCheck.setImmediate(true);
        activeCheck.setDescription("active");
        activeCheck.addListener(new ValueChangeListener() {

            @Override
            public void valueChange(ValueChangeEvent event) {
                boolean checked = (Boolean) activeCheck.getValue();
                if (checked == false) {
                    activeCheck.setValue(true);
                } else {
                    activeCheck.setValue(false);
                }
            }
        });

        adduser.addComponent(activeCheck);

        final Button btn = new Button("Add");
        btn.addListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
        final String cname = (String) nameField.getValue();
        final String crole = classCombo.getValue().toString();

        final boolean cactive = (Boolean) activeCheck.getValue();

                int success = addChar(cname, crole, cactive);
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
        adduser.addComponent(hzl);
    }
    private int addChar(String name, String role, boolean isActive) {
                try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CharacterDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        Connection c = null;
        int class_id=0, update=0;

        try {
            c = new DBConnection().getConnection();
            PreparedStatement ps = c.prepareStatement("INSERT INTO characters (name, character_class_id, active, user_id) VALUES(?,?,?,NULL)");
            PreparedStatement pclass = c.prepareStatement("SELECT * FROM character_classes WHERE name=?");
            if (role.equals("DeathKnight")) {
                role = "Death Knight";
            }
            pclass.setString(1, role);
            ResultSet rsclass = pclass.executeQuery();

            while (rsclass.next()) {
                class_id = rsclass.getInt("id");
            }
            ps.setString(1, name);
            ps.setInt(2, class_id);
            ps.setBoolean(3, isActive);
            update = ps.executeUpdate();

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
        return update;
    }
}
