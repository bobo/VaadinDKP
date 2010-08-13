/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity.raids;

import com.unknown.entity.character.*;
import com.unknown.entity.items.*;
import com.vaadin.data.Property.ConversionException;
import com.vaadin.data.Property.ReadOnlyException;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alde
 */
public class RaidLootAddWindow extends Window {

        Raid raid;
        RaidDAO raidDao;
        ItemDAO itemDao;
        CharacterDAO characterDao;

        RaidLootAddWindow(Raid raid) {
                this.raid = raid;
                this.setCaption(raid.getComment().toString());
                this.setWidth("500px");
                this.setHeight("400px");
                this.raidDao = new RaidDB();
                this.itemDao = new ItemDB();
                this.characterDao = new CharacterDB();
        }

        public void printInfo() throws SQLException {
                final ComboBox boss = bossListComboBox();
                addComponent(boss);
                HashSet<Items> lootlist = getLootList();
                final ComboBox loots = lootListComboBox(lootlist);
                addComponent(loots);
                final CheckBox heroic = new CheckBox("Heroic");
                addComponent(heroic);
                final TextField price = new TextField("Price");
                addComponent(price);
                final ComboBox name = nameComboList();
                addComponent(name);

                price.setImmediate(true);
                heroic.setImmediate(true);
                loots.setImmediate(true);
                boss.setImmediate(true);
                name.setImmediate(true);

                loots.addListener(new ValueChangeListener() {

                        @Override
                        public void valueChange(ValueChangeEvent event) {
                                price.setValue(getItemPrice(loots.getValue().toString(), heroic.booleanValue()));
                        }
                });
                heroic.addListener(new ValueChangeListener() {

                        @Override
                        public void valueChange(ValueChangeEvent event) {
                                price.setValue(getItemPrice(loots.getValue().toString(), heroic.booleanValue()));
                        }
                });

                Button addButton = new Button("Add");
                addButton.addListener(new Button.ClickListener() {

                        @Override
                        public void buttonClick(ClickEvent event) {
                                addRaidLoot(boss.getValue().toString(), name.getValue().toString(), loots.getValue().toString(), Boolean.parseBoolean(heroic.getValue().toString()), Double.parseDouble(price.getValue().toString()));
                        }
                });
                addComponent(addButton);
        }

        private void addRaidLoot(String boss, String name, String loot, boolean isheroic, double price) {
                try {
                        raidDao.addLootToRaid(raid, boss, name, loot, isheroic, price);
                } catch (SQLException ex) {
                        Logger.getLogger(RaidLootAddWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
        }

        private ComboBox nameComboList() throws UnsupportedOperationException {
                final ComboBox name = new ComboBox("Name");
                HashSet<RaidChar> charlist = new HashSet<RaidChar>();
                TreeSet<String> sortedlist = new TreeSet<String>();
                charlist.addAll(raid.getRaidChars());
                for (RaidChar eachname : charlist) {
                        sortedlist.add(eachname.getName());
                }
                for (String s : sortedlist) {
                        name.addItem(s);
                }
                name.setNullSelectionAllowed(false);

                return name;
        }

        private Double getItemPrice(String itemname, Boolean isheroic) {
                Double price = 0.0;
                if (itemname != null) {

                        try {
                                price = getDefaultPrice(itemname, isheroic.booleanValue());
                        } catch (SQLException ex) {
                                Logger.getLogger(RaidLootAddWindow.class.getName()).log(Level.SEVERE, null, ex);
                        }
                }
                return price;
        }

        private ComboBox lootListComboBox(HashSet<Items> lootlist) throws UnsupportedOperationException {
                ComboBox loots = new ComboBox();
                for (Items eachitem : lootlist) {
                        loots.addItem(eachitem.getName());
                }
                loots.setNullSelectionAllowed(false);
                return loots;
        }

        private Double getDefaultPrice(String itemname, boolean isheroic) throws SQLException {
                return (Double) itemDao.getItemPrice(itemname, isheroic);
        }

        private ComboBox bossListComboBox() throws ConversionException, ReadOnlyException, UnsupportedOperationException, SQLException {
                List<String> bosslist = new ArrayList<String>();
                bosslist = raidDao.getBossesForRaid(raid);
                ComboBox boss = new ComboBox();
                for (String eachboss : bosslist) {
                        boss.addItem(eachboss);
                }
                boss.setNullSelectionAllowed(false);
                Collection<?> itemIds = boss.getItemIds();
                boss.setValue(itemIds.iterator().next());
                return boss;
        }

        private HashSet<Items> getLootList() {
                HashSet<Items> lootlist = new HashSet<Items>();
                lootlist.addAll(itemDao.getItems());
                return lootlist;
        }
}
