/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unknown.entity;

import com.vaadin.data.Item;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Table;
import java.util.List;

/**
 *
 * @author alde
 */
public class RaidList extends Table {

    private RaidDAO raidDAO;

    public RaidList(RaidDAO raidDAO) {
        this.raidDAO = raidDAO;
        addContainerProperty("Zone", String.class, "");
        addContainerProperty("Comment", String.class, "");
        addContainerProperty("Date", String.class, "");

        this.setHeight("500px");
        this.addListener(new ItemClickListener() {

            @Override
            public void itemClick(ItemClickEvent event) {
                Raids raid = (Raids) event.getItemId();
                RaidInfo info = new RaidInfo(raid);
                info.printInfo();
                info.setCaption(raid.getName());
                getApplication().getMainWindow().addWindow(info);
                info.center();
                info.setWidth("600px");
                info.setHeight("320px");
            }
        });
    }
    public void clear()  {
        this.removeAllItems();
    }

    public void printList() {
        clear();
        List<Raids> raids = raidDAO.getRaids();

        for (final Raids raid : raids) {
            Item addItem = addItem(raid);
            addItem.getItemProperty("Zone").setValue(raid.getName());
            addItem.getItemProperty("Comment").setValue(raid.getComment());
            addItem.getItemProperty("Date").setValue(raid.getDate());

        }
    }
}
