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
        addContainerProperty("Name", String.class, "");
        addContainerProperty("Comment", String.class, "");
        addContainerProperty("Date", String.class, "");

        this.addListener(new ItemClickListener() {

            @Override
            public void itemClick(ItemClickEvent event) {
                Raids raid = (Raids) event.getItemId();
                RaidInfo info = new RaidInfo(raid);
                info.printInfo();
                info.setCaption(raid.getName());
                getApplication().getMainWindow().addWindow(info);
                info.center();
                info.setWidth("400px");
                info.setHeight("400px");
            }
        });
    }

    public void printList() {
        List<Raids> raids = raidDAO.getRaids();

        for (final Raids raid : raids) {
            Item addItem = addItem(raid);
            addItem.getItemProperty("Name").setValue(raid.getName());
            addItem.getItemProperty("Comment").setValue(raid.getComment());
            addItem.getItemProperty("Date").setValue(raid.getDate());

        }
    }
}