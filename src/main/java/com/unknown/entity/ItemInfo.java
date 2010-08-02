/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unknown.entity;

import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

/**
 *
 * @author alde
 */

public class ItemInfo extends Window{
    private final Items item;

    public ItemInfo(Items item) {
	this.item = item;
    }


    public void printInfo() {
	addComponent(new Label("Item information"));
	addComponent(new Label("Name: "+item.getName()));
        addComponent(new Label("WowID: "+item.getWowID()));
	addComponent(new Label("Heroic: "+(item.isHeroic() ? "Heroic" : "Normal")));
        addComponent(new Label("Price: "+item.getPrice()));
        addComponent(new Label("Slot: "+item.getSlot()));
        addComponent(new Label("Type: "+item.getType()));
    }

}
