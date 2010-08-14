/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity.items;

import com.unknown.entity.database.ItemDB;
import com.unknown.entity.Slots;
import com.unknown.entity.Type;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alde
 */
public class ItemAddWindow extends Window {

        public ItemAddWindow() {
                this.setCaption("Add Item");
                this.center();
                this.setWidth("300px");
                this.addStyleName("opaque");
                this.setHeight("420px");

        }

        public void printInfo() {

                DefaultPrices def = new DefaultPrices(new ItemDB());
                List<ItemPrices> prices = new ArrayList<ItemPrices>();
                try {
                        prices = def.getPrices();
                } catch (SQLException ex) {
                        Logger.getLogger(ItemAddWindow.class.getName()).log(Level.SEVERE, null, ex);
                }

                VerticalLayout addItem = new VerticalLayout();
                addComponent(addItem);
                final TextField name = new TextField("Name");
                final TextField wowid = new TextField("WowID Normal");
                final TextField wowidheroic = new TextField("WowID Heroic");
                final TextField price = new TextField("Price Normal");
                final TextField priceheroic = new TextField("Price Heroic");
                final ComboBox slot = new ComboBox("Slot");
                final ComboBox type = new ComboBox("Type");
                final CheckBox legendary = new CheckBox(" Legendary");

                final List<ItemPrices> defaultprices = prices;

                name.setImmediate(true);
                name.focus();
                wowid.setImmediate(true);
                wowidheroic.setImmediate(true);
                price.setImmediate(true);
                priceheroic.setImmediate(true);
                slot.setImmediate(true);
                type.setImmediate(true);
                legendary.setImmediate(true);

                itemAddWIndowAddComponents(addItem, name, wowid, wowidheroic, slot, type, price, priceheroic, legendary);

                for (Slots slots : Slots.values()) {
                        slot.addItem(slots);
                }
                for (Type types : Type.values()) {
                        type.addItem(types);
                }

                slot.addListener(new SlotComboBoxValueChangeListener(slot, defaultprices, price, priceheroic));

                final Button btn = new Button("Add");
                btn.addListener(new AddButtonClickListener(name, wowid, wowidheroic, price, priceheroic, slot, type, legendary));
                final Button cbtn = new Button("Close");
                cbtn.addListener(new CloseButtonClickListener());
                HorizontalLayout hzl = new HorizontalLayout();
                hzl.setSpacing(true);
                hzl.addComponent(btn);
                hzl.addComponent(cbtn);
                addItem.addComponent(hzl);
        }

        private void itemAddWIndowAddComponents(VerticalLayout addItem, final TextField name, final TextField wowid, final TextField wowidheroic, final ComboBox slot, final ComboBox type, final TextField price, final TextField priceheroic, final CheckBox legendary) {
                addItem.addComponent(name);
                addItem.addComponent(wowid);
                addItem.addComponent(wowidheroic);
                addItem.addComponent(slot);
                addItem.addComponent(type);
                addItem.addComponent(price);
                addItem.addComponent(priceheroic);
                addItem.addComponent(legendary);
        }

        private int addItem(String name, int wowid, int wowid_hc, double price, double price_hc, String slot, String type, boolean isLegendary) throws SQLException {
                ItemDAO itemDao = new ItemDB();
                return itemDao.addItem(name, wowid, wowid_hc, price, price_hc, slot, type, isLegendary);
        }

        private static class SlotComboBoxValueChangeListener implements ValueChangeListener {

                private final ComboBox slot;
                private final List<ItemPrices> defaultprices;
                private final TextField price;
                private final TextField priceheroic;

                public SlotComboBoxValueChangeListener(ComboBox slot, List<ItemPrices> defaultprices, TextField price, TextField priceheroic) {
                        this.slot = slot;
                        this.defaultprices = defaultprices;
                        this.price = price;
                        this.priceheroic = priceheroic;
                }

                @Override
                public void valueChange(ValueChangeEvent event) {
                        String slotvalue = slot.getValue().toString();
                        double defprice = 0.0;
                        double defpricehc = 0.0;
                        for (ItemPrices ip : defaultprices) {
                                if (ip.getSlotString().equals(slotvalue)) {
                                        defprice = ip.getPrice();
                                        defpricehc = ip.getPriceHeroic();
                                }
                        }
                        price.setValue("" + defprice);
                        priceheroic.setValue("" + defpricehc);
                }
        }

        private class AddButtonClickListener implements ClickListener {

                private final TextField name;
                private final TextField wowid;
                private final TextField wowidheroic;
                private final TextField price;
                private final TextField priceheroic;
                private final ComboBox slot;
                private final ComboBox type;
                private final CheckBox legendary;

                public AddButtonClickListener(TextField name, TextField wowid, TextField wowidheroic, TextField price, TextField priceheroic, ComboBox slot, ComboBox type, CheckBox legendary) {
                        this.name = name;
                        this.wowid = wowid;
                        this.wowidheroic = wowidheroic;
                        this.price = price;
                        this.priceheroic = priceheroic;
                        this.slot = slot;
                        this.type = type;
                        this.legendary = legendary;
                }

                @Override
                public void buttonClick(ClickEvent event) {
                        final String iname = (String) name.getValue();
                        final int iwowid = Integer.parseInt(wowid.getValue().toString());
                        final int iwowidheroic = Integer.parseInt(wowidheroic.getValue().toString());
                        final double iprice = Double.parseDouble(price.getValue().toString());
                        final double ipriceheroic = Double.parseDouble(priceheroic.getValue().toString());
                        final String islot = slot.getValue().toString();
                        final String itype = type.getValue().toString();
                        final boolean isLegendary = Boolean.parseBoolean(legendary.getValue().toString());
                        int success = 0;
                        try {
                                success = addItem(iname, iwowid, iwowidheroic, iprice, ipriceheroic, islot, itype, isLegendary);
                        } catch (SQLException ex) {
                                Logger.getLogger(ItemAddWindow.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        addComponent(new Label("Update :" + success));
                }
        }

        private class CloseButtonClickListener implements ClickListener {

                public CloseButtonClickListener() {
                }

                @Override
                public void buttonClick(ClickEvent event) {
                        close();
                }
        }
}
