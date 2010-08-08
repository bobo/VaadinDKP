/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity.raids;

import com.vaadin.data.Item;
import com.vaadin.data.Property.ConversionException;
import com.vaadin.data.Property.ReadOnlyException;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;

/**
 *
 * @author alde
 */
public class RaidInfoWindow extends Window {

        private final Raid raid;

        public RaidInfoWindow(Raid raid) {
                this.raid = raid;
                center();
                setWidth("600px");
                setHeight("320px");
                setCaption(raid.getName());

        }

        public void printInfo() {
                raidInformation();

                HorizontalLayout hzl = new HorizontalLayout();
                hzl.setSpacing(true);

                hzl.addComponent(getTable(rewardList(raid)));
                hzl.addComponent(getTable(lootList(raid)));

                addComponent(hzl);
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
                addComponent(new Label("Raid information"));
                addComponent(new Label("Zone: " + raid.getName()));
                addComponent(new Label("Comment: " + raid.getComment()));
                addComponent(new Label("Date: " + raid.getDate()));
                addComponent(new Label("id: " + raid.getID()));
        }

        private Table lootList(Raid raid) {
                Table tbl = new Table();
                RaidInfoWindowLootListSetHeaders(tbl);
                tbl.setHeight(150);
                for (RaidItem item : raid.getRaidItems()) {
                        Item addItem = tbl.addItem(new Integer(item.getId()));
                        RaidInfoWindowLootListAddRow(addItem, item);
                }
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
                                RaidCharWindow info = new RaidCharWindow(rreward.getRewardChars());
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
