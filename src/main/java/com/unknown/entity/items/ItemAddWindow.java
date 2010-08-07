/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity.items;

import com.unknown.entity.character.CharacterDB;
import com.unknown.entity.DBConnection;
import com.unknown.entity.Slots;
import com.unknown.entity.Type;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alde
 */
public class ItemAddWindow extends Window {

    public ItemAddWindow() {
    }

    public void printInfo() {

        DefaultPrices def = new DefaultPrices();
        final List<ItemPrices> prices = def.getPrices();

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

        name.setImmediate(true);
        name.focus();
        wowid.setImmediate(true);
        wowidheroic.setImmediate(true);
        price.setImmediate(true);
        priceheroic.setImmediate(true);
        slot.setImmediate(true);
        type.setImmediate(true);
        legendary.setImmediate(true);
        
        addItem.addComponent(name);
        addItem.addComponent(wowid);
        addItem.addComponent(wowidheroic);
        addItem.addComponent(slot);
        addItem.addComponent(type);

        addItem.addComponent(price);
        addItem.addComponent(priceheroic);

        addItem.addComponent(legendary);

        for (Slots slots : Slots.values()) {
            slot.addItem(slots);
        }
        for (Type types : Type.values()) {
            type.addItem(types);
        }

        slot.addListener(new ComboBox.ValueChangeListener() {

            @Override
            public void valueChange(ValueChangeEvent event) {
                
                String slotvalue = slot.getValue().toString();

                double defprice = 0.0;
                double defpricehc = 0.0;
                for (ItemPrices ip : prices) {
                    if (ip.getSlotString().equals(slotvalue)) {
                        defprice = ip.getPrice();
                        defpricehc = ip.getPriceHeroic();
                    }
                }
                price.setValue(""+defprice);
                priceheroic.setValue(""+defpricehc);

            }
        });

        final Button btn = new Button("Add");
        btn.addListener(new Button.ClickListener() {

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

                int success = addItem(iname, iwowid, iwowidheroic, iprice, ipriceheroic, islot, itype, isLegendary);
              addComponent(new Label("Update :"+success));
            }
        });
        final Button cbtn = new Button("Close");
        cbtn.addListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                close();
            }
        });
        HorizontalLayout hzl = new HorizontalLayout();
        hzl.setSpacing(true);
        hzl.addComponent(btn);
        hzl.addComponent(cbtn);
        addItem.addComponent(hzl);
    }

    private int addItem(String name, int wowid, int wowid_hc, double price, double price_hc, String slot, String type, boolean isLegendary) {
                try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CharacterDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        Connection c = null;
        int result = 0;

        try {
            c = new DBConnection().getConnection();
            PreparedStatement ps = c.prepareStatement("INSERT INTO items (name, wowid_normal, wowid_heroic, price_normal, price_heroic, slot, type, isLegendary) VALUES(?,?,?,?,?,?,?,?)");
            ps.setString(1, name);
            ps.setInt(2, wowid);
            ps.setInt(3, wowid_hc);
            ps.setDouble(4, price);
            ps.setDouble(5, price_hc);
            ps.setString(6, slot);
            ps.setString(7, type);
            ps.setBoolean(8, isLegendary);

            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CharacterDB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return result;
    }
}
