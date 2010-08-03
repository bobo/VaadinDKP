/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity;

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
import java.sql.SQLException;
import javax.management.relation.RoleStatus;

/**
 *
 * @author alde
 */
public class CharacterAdd extends Window {

    private Connection connect() throws SQLException {
        String userName = "root", userPassword = "piccolo", databaseURL = "jdbc:mysql://unknown-entity.com:3306/dkp";
        Connection conn = null;
        conn = DriverManager.getConnection(databaseURL, userName, userPassword);
        return conn;
    }

    public CharacterAdd() {
    }

    public void printInfo() {
        VerticalLayout adduser = new VerticalLayout();
        addComponent(adduser);
        TextField entername = new TextField("Name");
        adduser.addComponent(entername);
        ComboBox addrole = new ComboBox("Class");
        for (Role roles : Role.values()) {
            addrole.addItem(roles);
        }
        adduser.addComponent(addrole);

        CheckBox cb = new CheckBox("active");
        cb.setDescription("active");
        
        adduser.addComponent(cb);

        final Button btn = new Button("Add");
                btn.addListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {

                close();
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
}
