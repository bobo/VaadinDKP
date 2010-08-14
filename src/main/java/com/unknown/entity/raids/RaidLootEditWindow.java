/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity.raids;

import com.unknown.entity.database.CharacterDB;
import com.unknown.entity.database.ItemDB;
import com.unknown.entity.database.RaidDB;
import com.unknown.entity.character.*;
import com.unknown.entity.items.*;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alde
 */
public class RaidLootEditWindow extends Window {

        Raid raid;
        RaidItem item;
        RaidDAO raidDao;
        CharacterDAO characterDao;
        ItemDAO itemDao;

        public RaidLootEditWindow(Raid raid, RaidItem item) {
                this.raid = raid;
                this.item = item;
                this.raidDao = new RaidDB();
                this.characterDao = new CharacterDB();
                this.itemDao = new ItemDB();
                this.setWidth("300px");
                this.setHeight("400px");
                this.setCaption("Edit loot: " + item.getName() + " for raid : "+raid.getComment());
        }

        public void printInfo() {
                final ComboBox charname = new ComboBox("Name");
                final ComboBox itemname = new ComboBox("Item");
                final CheckBox heroic = new CheckBox("Heroic");
                final TextField price = new TextField("Price");
                final Button deleteButton = new Button("Remove");
                final Button addButton = new Button("Add");

                HorizontalLayout hzl = new HorizontalLayout();
                addComponent(charname);
                addComponent(itemname);
                addComponent(heroic);
                addComponent(price);
                hzl.addComponent(deleteButton);
                hzl.addComponent(addButton);
                addComponent(hzl);
                for(RaidChar rc : raid.getRaidChars())
                        charname.addItem(rc.getName());
                charname.setValue(item.getLooter());
                charname.setImmediate(true);
                charname.setNullSelectionAllowed(false);

                for(Items i : itemDao.getItems())
                        itemname.addItem(i.getName());
                itemname.setValue(item.getName());
                itemname.setImmediate(true);
                itemname.setNullSelectionAllowed(false);

                heroic.setValue(item.isHeroic());
                price.setValue(item.getPrice());

                deleteButton.addListener(new DeleteItemListener());
                
        }

                private int deleteItem(RaidItem item) throws SQLException {
                        return raidDao.removeLootFromRaid(item);
                }

                private class DeleteItemListener implements ClickListener {

                @Override
                public void buttonClick(ClickEvent event) {
                        try {
                                System.out.println("Reward Item ID: "+item.getId());
                                int success = deleteItem(item);
                                System.out.println(success + "items deleted.");
                        } catch (SQLException ex) {
                                Logger.getLogger(RaidLootEditWindow.class.getName()).log(Level.SEVERE, null, ex);
                        }

                }
        }
}

