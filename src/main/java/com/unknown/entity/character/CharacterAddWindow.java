/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity.character;

import database.CharacterDB;
import com.unknown.entity.Role;
import com.vaadin.data.Property.ConversionException;
import com.vaadin.data.Property.ReadOnlyException;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import java.sql.SQLException;
import java.util.Collection;
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
                TextField nameField = CharacterAddNameField();
                adduser.addComponent(nameField);
                ComboBox classCombo = CharacterAddCharClassComboBox();
                adduser.addComponent(classCombo);
                CheckBox activeCheck = CharacterAddIsActive();
                
                adduser.addComponent(activeCheck);
                Button btn = CharacterAddButtonAdd(nameField, classCombo, activeCheck);
                Button cbtn = CharacterAddButtonClose();
                HorizontalLayout hzl = new HorizontalLayout();
                hzl.setSpacing(true);
                hzl.addComponent(btn);
                hzl.addComponent(cbtn);
                adduser.addComponent(hzl);
        }

        private Button CharacterAddButtonClose() {
                final Button cbtn = new Button("Close");
                cbtn.addListener(new Button.ClickListener() {

                        @Override
                        public void buttonClick(ClickEvent event) {
                                close();
                        }
                });
                return cbtn;
        }

        private Button CharacterAddButtonAdd(final TextField nameField, final ComboBox classCombo, final CheckBox activeCheck) {
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
                return btn;
        }

        private CheckBox CharacterAddIsActive() {
                final CheckBox activeCheck = new CheckBox("active", true);
                activeCheck.setImmediate(true);
                activeCheck.setDescription("active");
                return activeCheck;
        }

        private ComboBox CharacterAddCharClassComboBox() throws UnsupportedOperationException, ReadOnlyException, ConversionException {
                final ComboBox classCombo = new ComboBox("Class");
                classCombo.setImmediate(true);
                for (Role roles : Role.values()) {
                        classCombo.addItem(roles);
                }
                classCombo.setNullSelectionAllowed(false);
                Collection<?> itemIds = classCombo.getItemIds();
                classCombo.setValue(itemIds.iterator().next());
                return classCombo;
        }

        private TextField CharacterAddNameField() {
                final TextField nameField = new TextField("Name");
                nameField.setImmediate(true);
                nameField.focus();
                return nameField;
        }

        private int addChar(String name, String role, boolean isActive) throws SQLException {
                CharacterDAO characterDao = new CharacterDB();
                return characterDao.addNewCharacter(name, role, isActive);

        }
}
