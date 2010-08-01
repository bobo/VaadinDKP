/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author alde
 */
public class AddUser extends Window {

    private Connection connect() throws SQLException {
        String userName = "root", userPassword = "piccolo", databaseURL = "jdbc:mysql://unknown-entity.com:3306/dkp";
        Connection conn = null;
        conn = DriverManager.getConnection(databaseURL, userName, userPassword);
        return conn;
    }

    public AddUser() {
    }

    public void printInfo() {
        GridLayout adduser = new GridLayout(2, 4);
        addComponent(adduser);
        Label name = new Label("Name");
        adduser.addComponent(name, 0, 0);
        TextField entername = new TextField("");
        adduser.addComponent(entername, 0, 1);
        Label role = new Label("Class");
        adduser.addComponent(role, 1, 0);
        ComboBox addrole = new ComboBox();
        adduser.addComponent(addrole, 1, 1);

    }
}
