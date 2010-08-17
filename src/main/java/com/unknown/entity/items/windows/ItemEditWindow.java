/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity.items.windows;

import com.unknown.entity.dao.ItemDAO;
import com.unknown.entity.database.ItemDB;
import com.unknown.entity.Slots;
import com.unknown.entity.Type;
import com.unknown.entity.items.ItemInfoListener;
import com.unknown.entity.items.ItemLooter;
import com.unknown.entity.items.Items;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ConversionException;
import com.vaadin.data.Property.ReadOnlyException;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alde
 */
public class ItemEditWindow extends Window {

        private final Items item;
        private List<ItemInfoListener> listeners = new ArrayList<ItemInfoListener>();

        public ItemEditWindow(Items item) {
                this.item = item;
                this.addStyleName("opaque");
                this.setCaption("Edit item: " + item.getName());
                this.center();
                this.getContent().setSizeUndefined();
        }

        public void printInfo() {
                addComponent(new Label("Item information"));
                final TextField name = editInfoName();
                final ComboBox slot = editInfoSlot();
                final ComboBox type = editInfoType();
                final TextField wowIdfield = editInfoWowIdField();
                final TextField price = editInfoPriceField();
                final TextField pricehc = editInfoPriceHcField();
                final TextField wowIdfieldhc = editInfoWowIdHcField();

                addComponent(name);
                addComponent(slot);
                addComponent(type);
                itemEditGrid(wowIdfield, wowIdfieldhc, price, pricehc);

                final CheckBox islegendary = new CheckBox("Legendary", item.isLegendary());

                Button updateButton = new Button("Update");

                updateButton.addListener(new UpdateButtonClickListener(name, slot, type, wowIdfield, wowIdfieldhc, price, pricehc, islegendary));
                addComponent(updateButton);
                itemLootedByTable();
        }

        private void itemEditGrid(final TextField wowIdfield, final TextField wowIdfieldhc, final TextField price, final TextField pricehc) throws OutOfBoundsException, OverlapsException {
                GridLayout gl = new GridLayout(3, 4);
                gl.setWidth("500px");
                gl.addComponent(new Label("Normal "), 1, 0);
                gl.addComponent(new Label("Heroic "), 2, 0);
                gl.addComponent(new Label("WowID: "), 0, 1);
                Button wowIdBtn = editInfoWowIdButton();
                gl.addComponent(wowIdBtn, 1, 1);
                gl.addComponent(wowIdfield, 1, 2);
                Button wowIdBtnhc = editInfoWowIdHcButton();
                gl.addComponent(wowIdBtnhc, 2, 1);
                gl.addComponent(wowIdfieldhc, 2, 2);
                gl.addComponent(new Label("Price: "), 0, 3);
                gl.addComponent(price, 1, 3);
                gl.addComponent(pricehc, 2, 3);
                addComponent(gl);
        }

        private TextField editInfoPriceHcField() throws ReadOnlyException, ConversionException {
                final TextField pricehc = new TextField();
                pricehc.setImmediate(true);
                pricehc.setValue(item.getPrice_hc());
                return pricehc;
        }

        private TextField editInfoPriceField() throws ReadOnlyException, ConversionException {
                final TextField price = new TextField();
                price.setImmediate(true);
                price.setValue(item.getPrice());
                return price;
        }

        private TextField editInfoWowIdHcField() throws ConversionException, ReadOnlyException {
                final TextField wowIdfieldhc = new TextField();
                wowIdfieldhc.setImmediate(true);
                wowIdfieldhc.setValue("" + item.getWowId_hc());
                return wowIdfieldhc;
        }

        private Button editInfoWowIdHcButton() {
                final Button wowIdBtnhc = new Button("" + item.getWowId_hc());
                wowIdBtnhc.setStyleName(Button.STYLE_LINK);
                wowIdBtnhc.addListener(new WowIdHcButtonClickListener());
                return wowIdBtnhc;
        }

        private TextField editInfoWowIdField() throws ReadOnlyException, ConversionException {
                final TextField wowIdfield = new TextField();
                wowIdfield.setImmediate(true);
                wowIdfield.setValue("" + item.getWowId());
                return wowIdfield;
        }

        private Button editInfoWowIdButton() {
                final Button wowIdBtn = new Button("" + item.getWowId());
                wowIdBtn.setStyleName(Button.STYLE_LINK);
                wowIdBtn.addListener(new WowIdButtonClickListener());
                return wowIdBtn;
        }

        private ComboBox editInfoType() throws ConversionException, ReadOnlyException, UnsupportedOperationException {
                final ComboBox type = new ComboBox("Type: ");
                for (Type types : Type.values()) {
                        type.addItem(types);
                }
                type.setNullSelectionAllowed(false);
                type.setValue(item.getType());
                type.setImmediate(true);
                return type;
        }

        private ComboBox editInfoSlot() throws UnsupportedOperationException, ConversionException, ReadOnlyException {
                final ComboBox slot = new ComboBox("Slot: ");
                for (Slots slots : Slots.values()) {
                        slot.addItem(slots);
                }
                slot.setNullSelectionAllowed(false);
                slot.setValue(Slots.valueOf(item.getSlot().replace("-", "")));
                slot.setImmediate(true);
                return slot;
        }

        private TextField editInfoName() {
                final TextField name = new TextField("Name: ", item.getName());
                name.setWidth("300px");
                name.setImmediate(true);
                return name;
        }

        private void itemLootedByTable() {
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
                itemTableHeaders(tbl);
                tbl.setHeight(150);
                for (ItemLooter looters : item.getLooterList()) {
                        Item addItem = tbl.addItem(looters.getId());
                        itemTableRowAdd(addItem, looters);

                }
                return tbl;
        }

        private void itemTableRowAdd(Item addItem, ItemLooter looters) throws ConversionException, ReadOnlyException {
                addItem.getItemProperty("Name").setValue(looters.getName());
                addItem.getItemProperty("Price").setValue(looters.getPrice());
                addItem.getItemProperty("Raid").setValue(looters.getRaid());
                addItem.getItemProperty("Date").setValue(looters.getDate());
        }

        private void itemTableHeaders(Table tbl) throws UnsupportedOperationException {
                tbl.addContainerProperty("Name", String.class, "");
                tbl.addContainerProperty("Price", Double.class, 0);
                tbl.addContainerProperty("Raid", String.class, "");
                tbl.addContainerProperty("Date", String.class, "");
        }

        public void addItemInfoListener(ItemInfoListener listener) {
                listeners.add(listener);
        }

        private void notifyListeners() {
                for (ItemInfoListener itemInfoListener : listeners) {
                        itemInfoListener.onItemInfoChange();
                }
        }

        private class UpdateButtonClickListener implements ClickListener {

                private final TextField name;
                private final ComboBox slot;
                private final ComboBox type;
                private final TextField wowIdfield;
                private final TextField wowIdfieldhc;
                private final TextField price;
                private final TextField pricehc;
                private final CheckBox islegendary;

                public UpdateButtonClickListener(TextField name, ComboBox slot, ComboBox type, TextField wowIdfield, TextField wowIdfieldhc, TextField price, TextField pricehc, CheckBox islegendary) {
                        this.name = name;
                        this.slot = slot;
                        this.type = type;
                        this.wowIdfield = wowIdfield;
                        this.wowIdfieldhc = wowIdfieldhc;
                        this.price = price;
                        this.pricehc = pricehc;
                        this.islegendary = islegendary;
                }

                @Override
                public void buttonClick(ClickEvent event) {
                        final String newname = name.getValue().toString();
                        final Slots newslot = (Slots) slot.getValue();
                        final Type newtype = (Type) type.getValue();
                        final int newwowid = Integer.parseInt(wowIdfield.getValue().toString());
                        final int newwowidhc = Integer.parseInt(wowIdfieldhc.getValue().toString());
                        final double newprice = Double.parseDouble(price.getValue().toString());
                        final double newpricehc = Double.parseDouble(pricehc.getValue().toString());
                        final boolean legendary = (Boolean) islegendary.getValue();
                        final int success = updateItem(newname, newslot, newtype, newwowid, newwowidhc, newprice, newpricehc, legendary);
                        System.out.println("New Price Heroic" + newpricehc);
                        addComponent(new Label("Success: " + success));
                        notifyListeners();
                        close();
                }
        }

        private class WowIdHcButtonClickListener implements ClickListener {

                @Override
                public void buttonClick(ClickEvent event) {
                        String url = "http://www.wowhead.com/item=" + item.getWowId_hc();
                        getWindow().open(new ExternalResource(url), "_blank");
                }
        }

        private class WowIdButtonClickListener implements ClickListener {

                @Override
                public void buttonClick(ClickEvent event) {
                        String url = "http://www.wowhead.com/item=" + item.getWowId();
                        getWindow().open(new ExternalResource(url), "_blank");
                }
        }
}
