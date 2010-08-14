/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity.raids.windows;

import com.unknown.entity.dao.RaidDAO;
import com.unknown.entity.database.RaidDB;
import com.unknown.entity.raids.Raid;
import com.unknown.entity.raids.RaidItem;
import com.unknown.entity.raids.RaidReward;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ConversionException;
import com.vaadin.data.Property.ReadOnlyException;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alde
 */
public class RaidEditWindow extends Window {

        private final Raid raid;

        public RaidEditWindow(Raid raid) {
                this.raid = raid;
                this.center();
                this.getContent().setSizeUndefined();
                this.addStyleName("opaque");
                this.setCaption("Edit raid: " + raid.getName());

        }

        public void printInfo() {
                raidInformation();

                HorizontalLayout hzl = new HorizontalLayout();
                hzl.setSpacing(true);

                hzl.addComponent(getTable(rewardList(raid)));
                hzl.addComponent(getTable(lootList(raid)));

                Button addReward = new Button("Add Reward");
                addReward.addListener(new Button.ClickListener() {

                        @Override
                        public void buttonClick(ClickEvent event) {
                                RaidRewardAddWindow rewardadd = new RaidRewardAddWindow(raid);
                                rewardadd.printInfo();
                                getApplication().getMainWindow().addWindow(rewardadd);

                        }
                });
                Button addLoot = new Button("Add loot");

                addLoot.addListener(new Button.ClickListener() {

                        @Override
                        public void buttonClick(ClickEvent event) {
                                RaidLootAddWindow rlootadd = new RaidLootAddWindow(raid);
                                try {
                                        rlootadd.printInfo();
                                } catch (SQLException ex) {
                                        Logger.getLogger(RaidEditWindow.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                getApplication().getMainWindow().addWindow(rlootadd);
                        }
                });

                addComponent(hzl);
                HorizontalLayout vert = new HorizontalLayout();
                vert.addComponent(addReward);
                vert.addComponent(addLoot);
                addComponent(vert);

        }

        private void RaidInfoWindowLootListAddRow(Item addItem, RaidItem item) throws ReadOnlyException, ConversionException {
                addItem.getItemProperty("Name").setValue(item.getLooter());
                addItem.getItemProperty("Item").setValue(item.getName());
                addItem.getItemProperty("Price").setValue(item.getPrice());
                addItem.getItemProperty("Heroic").setValue(item.isHeroic());
        }

        private void RaidInfoWindowLootListSetHeaders(Table tbl) throws UnsupportedOperationException {
                tbl.addContainerProperty("Name", String.class, "");
                tbl.addContainerProperty("Item", String.class, "");
                tbl.addContainerProperty("Price", Double.class, 0);
                tbl.addContainerProperty("Heroic", String.class, "");
        }

        private void RaidInfoWindowRewardListAddRow(Item addItem, RaidReward reward) throws ReadOnlyException, ConversionException {
                addItem.getItemProperty("Comment").setValue(reward.getComment());
                addItem.getItemProperty("Shares").setValue(reward.getShares());
        }

        private void RaidInfoWindowRewardListSetHeaders(Table tbl) throws UnsupportedOperationException {
                tbl.addContainerProperty("Comment", String.class, "");
                tbl.addContainerProperty("Shares", Integer.class, "");
        }

        private void raidInformation() {
                RaidDAO raidDAO = new RaidDB();
                List<String> zoneList = raidDAO.getRaidZoneList();

                HorizontalLayout hzl = new HorizontalLayout();
                addComponent(new Label("Raid information"));
                final ComboBox zone = new ComboBox("Zone");
                for (String zones : zoneList) {
                        zone.addItem(zones);
                }
                zone.setImmediate(true);
                zone.setNullSelectionAllowed(false);
                zone.setValue(raid.getName());

                final TextField comment = new TextField("Comment: ", raid.getComment());
                comment.setImmediate(true);

                final TextField datum = new TextField("Date: ", raid.getDate());
                datum.setImmediate(true);

                Button updateButton = new Button("Update");

                hzl.addComponent(zone);
                hzl.addComponent(comment);
                hzl.addComponent(datum);

                addComponent(hzl);
                addComponent(updateButton);

                updateButton.addListener(new Button.ClickListener() {

                        @Override
                        public void buttonClick(ClickEvent event) {
                                final String raidzoneName = zone.getValue().toString();
                                final String raidcomment = comment.getValue().toString();
                                final String raiddate = datum.getValue().toString();
                                final int success;
                                try {
                                        success = updateRaid(raidzoneName, raidcomment, raiddate);
                                        addComponent(new Label("Success: " + success));
                                } catch (SQLException ex) {
                                        Logger.getLogger(RaidEditWindow.class.getName()).log(Level.SEVERE, null, ex);
                                }
                        }
                });
        }

        private int updateRaid(String raidzoneName, String raidcomment, String raiddate) throws SQLException {
                        RaidDAO raidDao = new RaidDB();
                        return raidDao.doRaidUpdate(raid, raidzoneName, raidcomment, raiddate);
                        }

        private Table lootList(final Raid raid) {
                Table tbl = new Table();
                RaidInfoWindowLootListSetHeaders(tbl);
                tbl.setHeight(150);
                for (RaidItem item : raid.getRaidItems()) {
//                        Item addItem = tbl.addItem(new Integer(item.getId()));
                        Item addItem = tbl.addItem(item);
                        RaidInfoWindowLootListAddRow(addItem, item);
                }
                tbl.addListener(new ItemClickListener() {

                        @Override
                        public void itemClick(ItemClickEvent event) {
                                RaidItem ritem = (RaidItem) event.getItemId();
                                RaidLootEditWindow info = new RaidLootEditWindow(raid, ritem);
                                info.printInfo();
                                getApplication().getMainWindow().addWindow(info);
                        }
                });
                return tbl;
        }

        private Table rewardList(final Raid raid) {
                Table tbl = new Table();
                RaidInfoWindowRewardListSetHeaders(tbl);
                tbl.setHeight(150);
                for (RaidReward reward : raid.getRaidRewards()) {
                        Item addItem = tbl.addItem(reward);
                        RaidInfoWindowRewardListAddRow(addItem, reward);
                }
                tbl.addListener(new ItemClickListener() {

                        @Override
                        public void itemClick(ItemClickEvent event) {
                                RaidReward rreward = (RaidReward) event.getItemId();
                                RaidRewardEditWindow info = new RaidRewardEditWindow(rreward);
                                info.printInfo();
                                getApplication().getMainWindow().addWindow(info);
                        }
                });

                return tbl;

        }

        private Component getTable(Table rewards) {
                if (rewards.size() > 0) {
                        return rewards;
                } else {
                        return new Label("No rewards in this raid.");
                }
        }
}
