/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity.character.windows;

import com.unknown.entity.character.CharacterItem;
import com.unknown.entity.character.User;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ConversionException;
import com.vaadin.data.Property.ReadOnlyException;
import com.vaadin.ui.GridLayout.OutOfBoundsException;
import com.vaadin.ui.GridLayout.OverlapsException;
import com.vaadin.ui.HorizontalLayout;
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
                this.addStyleName("opaque");
                this.setCaption(user.getUsername());
                this.center();
                this.getContent().setSizeUndefined();
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
                HorizontalLayout hzl = new HorizontalLayout();
                hzl.addComponent(new Label("Name: "));
                Label charname = new Label(user.getUsername());
                charname.addStyleName("color");
                hzl.addComponent(charname);
                addComponent(hzl);
                hzl = new HorizontalLayout();
                hzl.addComponent(new Label("Class: "));
                Label charclass = new Label(user.getRole().toString());
                charclass.addStyleName("color");
                hzl.addComponent(charclass);
                addComponent(hzl);
                hzl=new HorizontalLayout();
                hzl.addComponent(new Label("Status: "));
                Label charactive = new Label((user.isActive() ? "Active" : "Inactive"));
                charactive.addStyleName("color");
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
                tbl.setHeight("150px");
                for (CharacterItem charitem : user.getCharItems()) {
                        Item addItem = tbl.addItem(charitem.getId());
                        characterInfoLootTableAddRow(addItem, charitem);
                }
                return tbl;
        }
}
