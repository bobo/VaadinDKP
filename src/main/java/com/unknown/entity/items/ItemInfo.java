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
public class ItemInfo extends Window {

    private final Items item;

    public ItemInfo(Items item) {
        this.item = item;
    }

    public void printInfo() {
        addComponent(new Label("Item information"));

        addComponent(new Label("Slot: " + item.getSlot()));
        addComponent(new Label("Type: " + item.getType()));
        addComponent(new Label("Name: " + item.getName()));

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

    private Table lootList(Items item) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CharacterDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        Table tbl = new Table();
        tbl.addContainerProperty("Name", String.class, "");
        tbl.addContainerProperty("Price", Double.class, 0);
        tbl.addContainerProperty("Raid", String.class, "");
        tbl.addContainerProperty("Date", String.class, "");
        tbl.setHeight(150);
        Connection c = null;
        try {
            c = new DBConnection().getConnection();
            PreparedStatement p = c.prepareStatement("SELECT * FROM loots JOIN characters JOIN items JOIN raids WHERE loots.character_id=characters.id AND loots.item_id=? AND items.id=loots.item_id AND raids.id=loots.raid_id");
            p.setInt(1, item.getID());
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                Item addItem = tbl.addItem(new Integer(rs.getInt("loots.id")));
                addItem.getItemProperty("Name").setValue(rs.getString("characters.name"));
                addItem.getItemProperty("Price").setValue(rs.getDouble("loots.price"));
                addItem.getItemProperty("Raid").setValue(rs.getString("raids.comment"));
                addItem.getItemProperty("Date").setValue(rs.getString("raids.date"));
            }
        } catch(SQLException e) {}

        return tbl;
    }
}
