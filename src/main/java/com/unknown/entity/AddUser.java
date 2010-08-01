/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unknown.entity;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        GridLayout adduser = new GridLayout(2,4);
        addComponent(adduser);
        Label name = new Label("Name");
        adduser.addComponent(name, 0, 0);
        TextField entername = new TextField("");
        adduser.addComponent(entername,0,1);
    }
}
