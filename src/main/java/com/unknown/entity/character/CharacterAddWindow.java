/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity.character;

import com.unknown.entity.DBConnection;
import com.unknown.entity.Role;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alde
 */
public class CharacterAddWindow extends Window {

        public CharacterAddWindow() {
                setCaption("Add Character");
                center();
                setWidth("300px");
                setHeight("250px");
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
                                final String charactername = (String) nameField.getValue();
                                final String characterclass = classCombo.getValue().toString();

                                final boolean characteractive = (Boolean) activeCheck.getValue();

                                int success = 0;
                                try {
                                        success = addChar(charactername, characterclass, characteractive);
                                } catch (SQLException ex) {
                                        Logger.getLogger(CharacterAddWindow.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                addComponent(new Label("Update :" + success));
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

        private int addChar(String name, String role, boolean isActive) throws SQLException {
                CharacterDAO characterDao = new CharacterDB();
                return characterDao.addNewCharacter(name, role, isActive);

        }
}
