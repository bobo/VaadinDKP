/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity.character;

import com.vaadin.data.Item;
import com.vaadin.data.Property.ConversionException;
import com.vaadin.data.Property.ReadOnlyException;
import com.vaadin.ui.GridLayout.OutOfBoundsException;
import com.vaadin.ui.GridLayout.OverlapsException;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 *
 * @author bobo
 */
public class CharacterInfoWindow extends Window {

        private final User user;

        public CharacterInfoWindow(User user) {
                this.user = user;
                this.setCaption(user.getUsername());
                this.center();
                this.setWidth("400px");
                this.setHeight("400px");
        }

        public void printInfo() {
                characterInformation();
                characterDKP();
                characterLoots();

        }

        private void characterInfoLootTableAddRow(Item addItem, CharacterItem charitem) throws ReadOnlyException, ConversionException {
                addItem.getItemProperty("Name").setValue(charitem.getName());
                addItem.getItemProperty("Price").setValue(charitem.getPrice());
        }

        private void characterInfoLootTableSetHeaders(Table tbl) throws UnsupportedOperationException {
                tbl.addContainerProperty("Name", String.class, "");
                tbl.addContainerProperty("Price", Double.class, 0);
        }

        private void characterInformation() {
                addComponent(new Label("Character information"));
                addComponent(new Label("Name: " + user.getUsername()));
                addComponent(new Label("Class: " + user.getRole().toString()));
                addComponent(new Label("Status: " + (user.isActive() ? "Active" : "Inactive")));
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
                characterInfoLootTableSetHeaders(tbl);
                tbl.setHeight(150);
                for (CharacterItem charitem : user.getCharItems()) {
                        Item addItem = tbl.addItem(charitem.getId());
                        characterInfoLootTableAddRow(addItem, charitem);
                }
                return tbl;
        }
}
