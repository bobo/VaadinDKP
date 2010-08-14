/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity.items.windows;

import com.unknown.entity.Slots;
import com.unknown.entity.dao.ItemDAO;
import com.unknown.entity.database.ItemDB;
import com.unknown.entity.items.ItemPrices;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alde
 */
public class EditDefaultPricesWindow extends Window {

        ItemDAO itemDao = null;
        TextField normal = null;
        TextField heroic = null;
        ComboBox slot = null;
        List<ItemPrices> prices = new ArrayList<ItemPrices>();

        public EditDefaultPricesWindow() throws SQLException {
                this.setCaption("Edit default Prices");
                this.addStyleName("opaque");
                this.center();
                this.getContent().setSizeUndefined();
                this.normal = new TextField("Normal");
                this.heroic = new TextField("Heroic");
                this.slot = new ComboBox("Slot");
                this.itemDao = new ItemDB();
                this.prices.addAll(itemDao.getDefaultPrices());
        }

        public void printInfo() {
//                ComboBox slot = new ComboBox("Slot");
                for (Slots s : Slots.values()) {
                        slot.addItem(s);
                }
                slot.addListener(new SlotValueChangeListener());
//                TextField normal = new TextField("Normal");
//                TextField heroic = new TextField("Heroic");
                slot.setImmediate(true);
                normal.setImmediate(true);
                heroic.setImmediate(true);
                Button updateButton = new Button("Update");
                Button closeButton = new Button("Close");

                HorizontalLayout hzl = new HorizontalLayout();
                hzl.addComponent(slot);
                hzl.addComponent(normal);
                hzl.addComponent(heroic);
                closeButton.addListener(new CloseButtonListener());

                addComponent(hzl);
                hzl = new HorizontalLayout();
                hzl.addComponent(updateButton);
                hzl.addComponent(closeButton);
                hzl.setMargin(true);
                addComponent(hzl);
        }

        private void slotChangesMeansPriceChanges() {
                        String slotvalue = slot.getValue().toString();
                        double defprice = 0.0;
                        double defpricehc = 0.0;
                        for (ItemPrices ip : prices) {
                                if (ip.getSlotString().equals(slotvalue)) {
                                        defprice = ip.getPrice();
                                        defpricehc = ip.getPriceHeroic();
                                }
                        }
                        normal.setValue(""+defprice);
                        heroic.setValue(""+defpricehc);
        }

        private class CloseButtonListener implements ClickListener {

                @Override
                public void buttonClick(ClickEvent event) {
                        close();
                }
        }

        private class SlotValueChangeListener implements ValueChangeListener {

                @Override
                public void valueChange(ValueChangeEvent event) {
                        slotChangesMeansPriceChanges();
                }
        }
}
