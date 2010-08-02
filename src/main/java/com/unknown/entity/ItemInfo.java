/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unknown.entity;

import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
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
//        gl.addComponent(new Label(""+item.getWowID()), 1, 1);
        final Button wowIdBtn = new Button(""+item.getWowID());
            wowIdBtn.addListener(new Button.ClickListener() {
                @Override
                public void buttonClick(ClickEvent event) {
                    String url= "http://www.wowhead.com/item="+item.getWowID();
                    getWindow().open(new ExternalResource(url), "_blank");
                }
            });
        gl.addComponent(wowIdBtn, 1, 1);
        gl.addComponent(new Label(""+item.getWowID_hc()), 2, 1);
//        gl.addComponent(new Button(""+item.getWowID_hc()), 1, 1);
        final Button wowIdBtnhc = new Button(""+item.getWowID_hc());
            wowIdBtnhc.addListener(new Button.ClickListener() {
                @Override
                public void buttonClick(ClickEvent event) {
                    String url= "http://www.wowhead.com/item="+item.getWowID_hc();
                    getWindow().open(new ExternalResource(url), "_blank");
                }
            });
        gl.addComponent(wowIdBtnhc, 1, 2);

        gl.addComponent(new Label("Price: "), 0, 2);
        gl.addComponent(new Label(""+item.getPrice()), 1, 2);
        gl.addComponent(new Label(""+item.getPrice_hc()), 2, 2);
        addComponent(gl);
    }

}
