/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity.raids;

import com.vaadin.data.Item;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;

/**
 *
 * @author alde
 */
public class RaidInfo extends Window {

	private final Raid raid;

	public RaidInfo(Raid raid) {
		this.raid = raid;
		center();
		setWidth("600px");
		setHeight("320px");
		setCaption(raid.getName());

	}

	public void printInfo() {
		addComponent(new Label("Raid information"));

		addComponent(new Label("Zone: " + raid.getName()));
		addComponent(new Label("Comment: " + raid.getComment()));
		addComponent(new Label("Date: " + raid.getDate()));
		addComponent(new Label("id: "+raid.getID()));
		HorizontalLayout hzl = new HorizontalLayout();
		hzl.setSpacing(true);
		Table Rewards = rewardList(raid);
		if (Rewards.size() > 0) {
			hzl.addComponent(Rewards);
		} else {
			hzl.addComponent(new Label("No rewards in this raid."));
		}

		Table Loots = lootList(raid);
		if (Loots.size() > 0) {
			hzl.addComponent(Loots);
		} else {
			hzl.addComponent(new Label("No loot in this raid."));
		}
		addComponent(hzl);
	}

	private Table lootList(Raid raid) {
		Table tbl = new Table();
		tbl.addContainerProperty("Name", String.class, "");
		tbl.addContainerProperty("Item", String.class, "");
		tbl.addContainerProperty("Price", Double.class, 0);
		tbl.addContainerProperty("Heroic", String.class, "");
		tbl.setHeight(150);
		for (RaidItem item : raid.getRaidItems()) {
			Item addItem = tbl.addItem(new Integer(item.getId()));
			addItem.getItemProperty("Name").setValue(item.getLooter());
			addItem.getItemProperty("Item").setValue(item.getName());
			addItem.getItemProperty("Price").setValue(item.getPrice());
			addItem.getItemProperty("Heroic").setValue(item.isHeroic());
		}

		return tbl;
	}

    private Table rewardList(final Raid raid) {
                Table tbl = new Table();
				System.out.println("getting rewardlist for : "+raid.getID());
                tbl.addContainerProperty("Comment", String.class, "");
                tbl.addContainerProperty("Shares", Integer.class, "");
                tbl.setHeight(150);
                for (RaidReward rewards : raid.getRaidRewards()) {
                    Item addItem = tbl.addItem(rewards);
                    addItem.getItemProperty("Comment").setValue(rewards.getComment());
                    addItem.getItemProperty("Shares").setValue(rewards.getShares());
                }
                tbl.addListener(new ItemClickListener() {

                    @Override
                    public void itemClick(ItemClickEvent event) {
                        RaidReward rreward = (RaidReward) event.getItemId();
                        RaidCharWindow info = new RaidCharWindow(raid, rreward);
                        info.printInfo();
                        addComponent(new Label("Raid id; "+rreward.getRaidId()));
                        getApplication().getMainWindow().addWindow(info);
                    }
                });

                return tbl;

    }
}
