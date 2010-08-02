/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unknown.entity;

import com.vaadin.ui.GridLayout;
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

        addComponent(new Label("Slot: "+item.getSlot()));
        addComponent(new Label("Type: "+item.getType()));
       	addComponent(new Label("Name: "+item.getName()));

        GridLayout gl = new GridLayout(3, 3);
        gl.setSpacing(true);
        gl.addComponent(new Label("Normal"), 1, 0);
        gl.addComponent(new Label("Heroic"), 2, 0);
        gl.addComponent(new Label("WowID: "), 0, 1);
        gl.addComponent(new Label(""+item.getWowID()), 1, 1);
        gl.addComponent(new Label(""+item.getWowID_hc()), 2, 1);
        gl.addComponent(new Label("Price: "), 0, 2);
        gl.addComponent(new Label(""+item.getPrice()), 1, 2);
        gl.addComponent(new Label(""+item.getPrice_hc()), 2, 2);
        addComponent(gl);
    }

}
