/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity.character;

import database.CharacterDB;
import com.unknown.entity.Role;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ConversionException;
import com.vaadin.data.Property.ReadOnlyException;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.GridLayout.OutOfBoundsException;
import com.vaadin.ui.GridLayout.OverlapsException;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
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
                this.setCaption("Edit character: "+user.getUsername());
                this.setPositionX(200);
                this.setPositionY(400);
                this.setWidth("400px");
                this.setHeight("500px");
        }

        public void printInfo() {
                CharacterInformation();
                CharacterDKP();
                CharacterLoots();

        }

        private void CharacterInformation() {
                addComponent(new Label("Character information"));

                final TextField name = new TextField("Name: ", user.getUsername());
                final ComboBox characterClass = CharacterEditClassComboBox();
                final CheckBox active = new CheckBox("Status: ", user.isActive());
                Button updateButton = CharacterEditUpdateButton(name, characterClass, active);

                addComponent(name);
                addComponent(characterClass);
                addComponent(active);

                addComponent(updateButton);
        }

        private Button CharacterEditUpdateButton(final TextField name, final ComboBox characterClass, final CheckBox active) {
                Button updateButton = new Button("Update");
                updateButton.addListener(new Button.ClickListener() {

                        @Override
                        public void buttonClick(ClickEvent event) {
                                int success = updateCharacter(name.getValue().toString(), characterClass.getValue().toString(), (Boolean) active.getValue());
                                addComponent(new Label("Success: " + success));
                        }
                });
                return updateButton;
        }

        private ComboBox CharacterEditClassComboBox() throws ConversionException, UnsupportedOperationException, ReadOnlyException {
                final ComboBox characterClass = new ComboBox("Class: ");
                for (Role role : Role.values()) {
                        characterClass.addItem(role);
                }
                characterClass.setValue(user.getRole());
                characterClass.setNullSelectionAllowed(false);
                return characterClass;
        }

        private void CharacterLootTableSetColumnHeaders(Table tbl) throws UnsupportedOperationException {
                tbl.addContainerProperty("Name", String.class, "");
                tbl.addContainerProperty("Price", Double.class, 0);
        }

        private void CharacterLootTableSetRow(Item addItem, CharacterItem charitem) throws ReadOnlyException, ConversionException {
                addItem.getItemProperty("Name").setValue(charitem.getName());
                addItem.getItemProperty("Price").setValue(charitem.getPrice());
        }

        private int updateCharacter(String name, String charclass, boolean active) {
                CharacterDAO charDao = new CharacterDB();
                return charDao.updateCharacter(user, name, charclass, active);
        }

        private void CharacterLoots() {
                Table loots = lootList(user);
                if (loots.size() > 0) {
                        addComponent(lootList(user));
                } else {
                        addComponent(new Label("No items looted yet."));
                }
        }

        private void CharacterDKP() throws OutOfBoundsException, OverlapsException {
                addComponent(new Label("DKP"));
                GridLayout dkpGrid = new GridLayout(2, 4);
                dkpGrid.addComponent(new Label("Shares: "), 0, 0);
                dkpGrid.addComponent(new Label("DKP Earned: "), 0, 1);
                dkpGrid.addComponent(new Label("DKP Spent: "), 0, 2);
                dkpGrid.addComponent(new Label("DKP: "), 0, 3);
                dkpGrid.addComponent(new Label("" + user.getShares()), 1, 0);
                dkpGrid.addComponent(new Label("" + user.getDKPEarned()), 1, 1);
                dkpGrid.addComponent(new Label("" + user.getDKPSpent()), 1, 2);
                dkpGrid.addComponent(new Label("" + user.getDKP()), 1, 3);
                addComponent(dkpGrid);
        }

        private Table lootList(User user) {
                Table tbl = new Table();
                CharacterLootTableSetColumnHeaders(tbl);
                tbl.setHeight(150);
                for (CharacterItem charitem : user.getCharItems()) {
                        Item addItem = tbl.addItem(charitem.getId());
                        CharacterLootTableSetRow(addItem, charitem);
                }
                return tbl;

        }
}
