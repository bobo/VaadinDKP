/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unknown.entity;

import com.vaadin.data.Item;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Table;
import java.util.Collections;
import java.util.Comparator;
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
	addContainerProperty("WowID", Integer.class, 0);
        addContainerProperty("Price", Double.class, 0);
        addContainerProperty("Slot", String.class, "");
        addContainerProperty("Type", String.class, "");
        addContainerProperty("Heroic", Boolean.class, false);

	this.addListener(new ItemClickListener() {

	    @Override
	    public void itemClick(ItemClickEvent event) {
		Items item = (Items) event.getItemId();
		ItemInfo info = new ItemInfo(item);
		info.printInfo();
		getApplication().getMainWindow().addWindow(info);
		info.center();
		info.setWidth("400px");
		info.setHeight("400px");
	    }
	});
    }

    public void printList() {
    List<Items> items = itemDAO.getItems();
//	Collections.sort(items);

	for (final Items item : items) {
	    Item addItem = addItem(item);
	    addItem.getItemProperty("Name").setValue(item.getName());
	    addItem.getItemProperty("WowID").setValue(item.getWowID());
            addItem.getItemProperty("Price").setValue(item.getPrice());
            addItem.getItemProperty("Slot").setValue(item.getSlot());
            addItem.getItemProperty("Type").setValue(item.getType());
            addItem.getItemProperty("Heroic").setValue(item.isHeroic());
	}
    }
}