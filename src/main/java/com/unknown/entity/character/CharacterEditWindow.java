/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unknown.entity.character;

import com.unknown.entity.Role;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

/**
 *
 * @author alde
 */
public class CharacterEditWindow extends Window {
        private final User user;

        public CharacterEditWindow(User user) {
                this.user = user;
                this.setCaption(user.getUsername());
                this.center();
                this.setWidth("400px");
                this.setHeight("400px");
        }
        public void printInfo() {
                CharacterInformation();
//                CharacterDKP();
//                CharacterLoots();

        }

        private void CharacterInformation() {
                addComponent(new Label("Character information"));
                TextField name = new TextField("Name: ",user.getUsername());
                ComboBox characterClass = new ComboBox("Class: ");
                for (Role role : Role.values()) {
                        characterClass.addItem(role);
                }
                characterClass.setValue(user.getRole());
                characterClass.setNullSelectionAllowed(false);
                CheckBox active = new CheckBox("Status: ", user.isActive());
                addComponent(name);
                addComponent(characterClass);
                addComponent(active);
        }
}
