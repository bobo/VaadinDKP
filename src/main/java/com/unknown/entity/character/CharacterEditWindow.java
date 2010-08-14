/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity.character;

import com.unknown.entity.database.CharacterDB;
import com.unknown.entity.Role;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ConversionException;
import com.vaadin.data.Property.ReadOnlyException;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout.OutOfBoundsException;
import com.vaadin.ui.GridLayout.OverlapsException;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 *
 * @author alde
 */
public class CharacterEditWindow extends Window {

        private final User user;

        public CharacterEditWindow(User user) {
                this.user = user;
                this.setCaption("Edit character: "+user.getUsername());
                this.setPositionX(200);
                this.setPositionY(400);
                this.setWidth("400px");
                this.setHeight("500px");
        }

        public void printInfo() {
                characterInformation();
                characterDKP();
                characterLoots();

        }

        private void characterInformation() {
                addComponent(new Label("Character information"));

                final TextField name = new TextField("Name: ", user.getUsername());
                final ComboBox characterClass = characterEditClassComboBox();
                final CheckBox active = new CheckBox("Status: ", user.isActive());
                Button updateButton = characterEditUpdateButton(name, characterClass, active);

                addComponent(name);
                addComponent(characterClass);
                addComponent(active);

                addComponent(updateButton);
        }

        private Button characterEditUpdateButton(final TextField name, final ComboBox characterClass, final CheckBox active) {
                Button updateButton = new Button("Update");
                updateButton.addListener(new updateBtnClickListener(name, characterClass, active));
                return updateButton;
        }

        private ComboBox characterEditClassComboBox() throws ConversionException, UnsupportedOperationException, ReadOnlyException {
                final ComboBox characterClass = new ComboBox("Class: ");
                for (Role role : Role.values()) {
                        characterClass.addItem(role);
                }
                characterClass.setValue(user.getRole());
                characterClass.setNullSelectionAllowed(false);
                return characterClass;
        }

        private void characterLootTableSetColumnHeaders(Table tbl) throws UnsupportedOperationException {
                tbl.addContainerProperty("Name", String.class, "");
                tbl.addContainerProperty("Price", Double.class, 0);
        }

        private void characterLootTableSetRow(Item addItem, CharacterItem charitem) throws ReadOnlyException, ConversionException {
                addItem.getItemProperty("Name").setValue(charitem.getName());
                addItem.getItemProperty("Price").setValue(charitem.getPrice());
        }

        private int updateCharacter(String name, String charclass, boolean active) {
                CharacterDAO charDao = new CharacterDB();
                return charDao.updateCharacter(user, name, charclass, active);
        }

        private void characterLoots() {
                Table loots = lootList(user);
                if (loots.size() > 0) {
                        addComponent(lootList(user));
                } else {
                        addComponent(new Label("No items looted yet."));
                }
        }

        private void characterDKP() throws OutOfBoundsException, OverlapsException {
                addComponent(new Label("DKP"));

                VerticalLayout vert = new VerticalLayout();
                vert.addComponent(new Label("Shares: "+ user.getShares()));
                vert.addComponent(new Label("DKP Earned: "+ user.getDKPEarned()));
                vert.addComponent(new Label("DKP Spent: "+ user.getDKPSpent()));
                vert.addComponent(new Label("DKP: "+user.getDKP()));
                addComponent(vert);
        }

        private Table lootList(User user) {
                Table tbl = new Table();
                characterLootTableSetColumnHeaders(tbl);
                tbl.setHeight(150);
                for (CharacterItem charitem : user.getCharItems()) {
                        Item addItem = tbl.addItem(charitem.getId());
                        characterLootTableSetRow(addItem, charitem);
                }
                return tbl;

        }

        private class updateBtnClickListener implements ClickListener {

                private final TextField name;
                private final ComboBox characterClass;
                private final CheckBox active;

                public updateBtnClickListener(TextField name, ComboBox characterClass, CheckBox active) {
                        this.name = name;
                        this.characterClass = characterClass;
                        this.active = active;
                }

                @Override
                public void buttonClick(ClickEvent event) {
                        int success = updateCharacter(name.getValue().toString(), characterClass.getValue().toString(), (Boolean) active.getValue());
                        addComponent(new Label("Success: " + success));
                }
        }
}
