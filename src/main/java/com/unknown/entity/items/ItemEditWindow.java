/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity.items;

import com.unknown.entity.Slots;
import com.unknown.entity.Type;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ConversionException;
import com.vaadin.data.Property.ReadOnlyException;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.GridLayout.OutOfBoundsException;
import com.vaadin.ui.GridLayout.OverlapsException;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

/**
 *
 * @author alde
 */
public class ItemEditWindow extends Window {

        private final Items item;

        public ItemEditWindow(Items item) {
                this.item = item;
                this.setCaption(item.getName());
                this.center();
                this.setWidth("550px");
                this.setHeight("500px");
        }

        public void printInfo() {
                addComponent(new Label("Item information"));

                TextField name = new TextField("Name: ", item.getName());
                name.setWidth("300px");
                name.setImmediate(true);
                ComboBox slot = new ComboBox("Slot: ");
                for (Slots slots : Slots.values()) {
                        slot.addItem(slots);
                }
                slot.setNullSelectionAllowed(false);
                slot.setValue(Slots.valueOf(item.getSlot().replace("-", "")));
                slot.setImmediate(true);

                ComboBox type = new ComboBox("Type: ");
                for (Type types : Type.values()) {
                        type.addItem(types);
                }
                type.setNullSelectionAllowed(false);
                type.setValue(item.getType());
                type.setImmediate(true);

                addComponent(name);
                addComponent(slot);
                addComponent(type);

                GridLayout gl = new GridLayout(3, 4);
                gl.setWidth("500px");
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
                TextField wowIdfield = new TextField();
                wowIdfield.setValue("" + item.getWowID());
                gl.addComponent(wowIdBtn, 1, 1);
                gl.addComponent(wowIdfield, 1, 2);
                final Button wowIdBtnhc = new Button("" + item.getWowID_hc());
                wowIdBtnhc.setStyleName(Button.STYLE_LINK);
                wowIdBtnhc.addListener(new Button.ClickListener() {

                        @Override
                        public void buttonClick(ClickEvent event) {
                                String url = "http://www.wowhead.com/item=" + item.getWowID_hc();
                                getWindow().open(new ExternalResource(url), "_blank");
                        }
                });
                TextField wowIdfieldhc = new TextField();
                wowIdfieldhc.setValue("" + item.getWowID_hc());
                gl.addComponent(wowIdBtnhc, 2, 1);
                gl.addComponent(wowIdfieldhc, 2, 2);
                gl.addComponent(new Label("Price: "), 0, 3);
                TextField price = new TextField();
                price.setImmediate(true);
                TextField pricehc = new TextField();
                pricehc.setImmediate(true);
                price.setValue(item.getPrice());
                pricehc.setValue(item.getPrice_hc());
                gl.addComponent(price, 1, 3);
                gl.addComponent(pricehc, 2, 3);
                addComponent(gl);

                CheckBox islegendary = new CheckBox("Legendary", item.isLegendary());

                Button updateButton = new Button("Update");

                final String newname = name.getValue().toString();
                final Slots newslot = (Slots) slot.getValue();
                final Type newtype = (Type) type.getValue();
                final int newwowid = Integer.parseInt(wowIdfield.getValue().toString());
                final int newwowidhc = Integer.parseInt(wowIdfieldhc.getValue().toString());
                final double newprice = Double.parseDouble(price.getValue().toString());
                final double newpricehc = Double.parseDouble(pricehc.getValue().toString());
                final boolean legendary = (Boolean) islegendary.getValue();
                
                updateButton.addListener(new Button.ClickListener() {

                        @Override
                        public void buttonClick(ClickEvent event) {
                                final int success = updateItem(newname, newslot, newtype, newwowid, newwowidhc, newprice, newpricehc, legendary);

                addComponent(new Label("Success: " + success));
                        }
                });
                addComponent(updateButton);
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

        private int updateItem(String newname, Slots newslot, Type newtype, int newwowid, int newwowidhc, double newprice, double newpricehc, boolean legendary) {

                ItemDAO itemDao = new ItemDB();
                return itemDao.updateItem(item, newname, newslot, newtype, newwowid, newwowidhc, newprice, newpricehc, legendary);
        }

        private Table lootList(Items item) {
                Table tbl = new Table();
                ItemTableHeaders(tbl);
                tbl.setHeight(150);
                for (ItemLooter looters : item.getItemList()) {
                        Item addItem = tbl.addItem(looters.getId());
                        ItemTableRowAdd(addItem, looters);

                }
                return tbl;
        }

        private void ItemTableRowAdd(Item addItem, ItemLooter looters) throws ConversionException, ReadOnlyException {
                addItem.getItemProperty("Name").setValue(looters.getName());
                addItem.getItemProperty("Price").setValue(looters.getPrice());
                addItem.getItemProperty("Raid").setValue(looters.getRaid());
                addItem.getItemProperty("Date").setValue(looters.getDate());
        }

        private void ItemTableHeaders(Table tbl) throws UnsupportedOperationException {
                tbl.addContainerProperty("Name", String.class, "");
                tbl.addContainerProperty("Price", Double.class, 0);
                tbl.addContainerProperty("Raid", String.class, "");
                tbl.addContainerProperty("Date", String.class, "");
        }
}
