/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unknown.entity.raids;

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
        this.setWidth("300px");
        this.addListener(new ItemClickListener() {

            @Override
            public void itemClick(ItemClickEvent event) {
                Raids raid = (Raids) event.getItemId();
                RaidInfo info = new RaidInfo(raid);
                info.printInfo();
                getApplication().getMainWindow().addWindow(info);
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
