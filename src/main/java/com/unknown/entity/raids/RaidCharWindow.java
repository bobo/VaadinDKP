/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unknown.entity.raids;

import com.vaadin.data.Item;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;

/**
 *
 * @author alde
 */
public class RaidCharWindow extends Window {

        private final RaidReward raid;

	RaidCharWindow(RaidReward raid) {

		this.raid = raid;
		center();
		setWidth("600px");
		setHeight("320px");
		setCaption(raid.getComment());
	}

        public void printInfo() {
		HorizontalLayout hzl = new HorizontalLayout();
		hzl.setSpacing(true);
		Table Attendants = charList(raid);
		if (Attendants.size() > 0) {
			hzl.addComponent(Attendants);
		} else {
			hzl.addComponent(new Label("No members in this raid."));
		}
		addComponent(hzl);
	}

	private Table charList(RaidReward reward) {
                Table tbl = new Table();
                tbl.addContainerProperty("Name", String.class, "");
                tbl.addContainerProperty("Shares", Integer.class, "");
                tbl.setHeight(150);
                for (RaidChar raidreward : reward.getRewardChars()) {
                    Item addItem = tbl.addItem(new Integer(raidreward.getId()));
                    addItem.getItemProperty("Name").setValue(raidreward.getName());
                    addItem.getItemProperty("Shares").setValue(raidreward.getShares());
                }
                return tbl;
	}
}
