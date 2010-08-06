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
import java.util.List;

/**
 *
 * @author alde
 */
public class RaidCharWindow extends Window {

        private final Raid raid;
        private final RaidReward reward;

        RaidCharWindow(Raid raid, RaidReward reward) {

                this.raid = raid;
                this.reward = reward;
                center();
                setWidth("600px");
                setHeight("320px");
                setCaption(reward.getComment()+" ID:" +reward.getId());
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

        private Table charList(Raid raid) {
                Table tbl = new Table();
                tbl.addContainerProperty("Name", String.class, "");
                tbl.addContainerProperty("Shares", Integer.class, "");
                tbl.setHeight(150);
                for (RaidChar rchar : raid.getRaidChars()) {
                        if (rchar.getRaidId() == raid.getID()) {
                                Item addItem = tbl.addItem(rchar);
                                addItem.getItemProperty("Name").setValue(rchar.getName());
                                addItem.getItemProperty("Shares").setValue(rchar.getShares());
                        }
                }
                return tbl;
        }
}
