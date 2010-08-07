/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity.items;

import com.vaadin.data.Item;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Table;
import java.util.List;

/**
 *
 * @author bobo
 */
public class ItemList extends Table {

    private ItemDAO itemDAO;

    public ItemList(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
        addContainerProperty("Name", String.class, "");
        addContainerProperty("Price Normal", Double.class, 0);
        addContainerProperty("Price Heroic", Double.class, 0);
        addContainerProperty("Slot", String.class, "");
        addContainerProperty("Type", String.class, "");

        this.setHeight("500px");
        
        this.addListener(new ItemClickListener() {

            @Override
            public void itemClick(ItemClickEvent event) {
                Items item = (Items) event.getItemId();
                ItemInfoWindow info = new ItemInfoWindow(item);
                info.printInfo();
                info.setCaption(item.getName());
                getApplication().getMainWindow().addWindow(info);
                info.center();
                info.setWidth("400px");
                info.setHeight("400px");
            }
        });
    }
    public void clear()  {
        this.removeAllItems();
    }

    public void printList() {
        clear();
        List<Items> itemses = itemDAO.getItems();

        for (final Items item : itemses) {
            Item addItem = addItem(item);
            addItem.getItemProperty("Name").setValue(item.getName());
            addItem.getItemProperty("Price Normal").setValue(item.getPrice());
            addItem.getItemProperty("Price Heroic").setValue(item.getPrice_hc());
            addItem.getItemProperty("Slot").setValue(item.getSlot());
            addItem.getItemProperty("Type").setValue(item.getType());
        }
    }
}
