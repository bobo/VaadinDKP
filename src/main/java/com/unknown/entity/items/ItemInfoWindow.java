/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity.items;

import com.unknown.entity.character.CharacterDB;
import com.unknown.entity.DBConnection;
import com.vaadin.data.Item;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.GridLayout.OutOfBoundsException;
import com.vaadin.ui.GridLayout.OverlapsException;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alde
 */
public class ItemInfoWindow extends Window {

        private final Items item;

        public ItemInfoWindow(Items item) {
                this.item = item;
        }

        public void printInfo() {
                ItemInformation();
                ItemGrid();
                ItemLootedByTable();
        }

        private void ItemLootedByTable() {
                addComponent(new Label("Looted by"));
                HorizontalLayout hzl = new HorizontalLayout();
                hzl.setSpacing(true);
                Table Attendants = lootList(item);
                if (Attendants.size() > 0) {
                        hzl.addComponent(Attendants);
                } else {
                        hzl.addComponent(new Label("Not looted by anyone"));
                }
                addComponent(hzl);
        }

        private void ItemGrid() throws OverlapsException, OutOfBoundsException {
                GridLayout gl = new GridLayout(3, 3);
                gl.setWidth("300px");
                gl.addComponent(new Label("Normal "), 1, 0);
                gl.addComponent(new Label("Heroic "), 2, 0);
                gl.addComponent(new Label("WowID: "), 0, 1);
                final Button wowIdBtn = new Button("" + item.getWowID());
                wowIdBtn.setStyleName(Button.STYLE_LINK);
                wowIdBtn.addListener(new Button.ClickListener() {

                        @Override
                        public void buttonClick(ClickEvent event) {
                                String url = "http://www.wowhead.com/item=" + item.getWowID();
                                getWindow().open(new ExternalResource(url), "_blank");
                        }
                });
                gl.addComponent(wowIdBtn, 1, 1);
                final Button wowIdBtnhc = new Button("" + item.getWowID_hc());
                wowIdBtnhc.setStyleName(Button.STYLE_LINK);
                wowIdBtnhc.addListener(new Button.ClickListener() {

                        @Override
                        public void buttonClick(ClickEvent event) {
                                String url = "http://www.wowhead.com/item=" + item.getWowID_hc();
                                getWindow().open(new ExternalResource(url), "_blank");
                        }
                });
                gl.addComponent(wowIdBtnhc, 2, 1);
                gl.addComponent(new Label("Price: "), 0, 2);
                gl.addComponent(new Label("" + item.getPrice()), 1, 2);
                gl.addComponent(new Label("" + item.getPrice_hc()), 2, 2);
                addComponent(gl);
        }

        private void ItemInformation() {
                addComponent(new Label("Item information"));
                addComponent(new Label("Slot: " + item.getSlot()));
                addComponent(new Label("Type: " + item.getType()));
                addComponent(new Label("Name: " + item.getName()));
        }

        private Table lootList(Items item) {
                Table tbl = new Table();
                tbl.addContainerProperty("Name", String.class, "");
                tbl.addContainerProperty("Price", Double.class, 0);
                tbl.addContainerProperty("Raid", String.class, "");
                tbl.addContainerProperty("Date", String.class, "");
                tbl.setHeight(150);
                for (ItemLooter looters : item.getItemList()) {
                        Item addItem = tbl.addItem(looters.getId());
                        addItem.getItemProperty("Name").setValue(looters.getName());
                        addItem.getItemProperty("Price").setValue(looters.getPrice());
                        addItem.getItemProperty("Raid").setValue(looters.getRaid());
                        addItem.getItemProperty("Date").setValue(looters.getDate());

                }
                return tbl;
        }
}
