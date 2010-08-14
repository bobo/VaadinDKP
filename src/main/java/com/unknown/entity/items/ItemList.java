/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity.items;

import com.unknown.entity.items.windows.ItemEditWindow;
import com.unknown.entity.items.windows.ItemInfoWindow;
import com.unknown.entity.dao.ItemDAO;
import com.unknown.entity.character.SiteUser;
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
                this.setHeight("500px");

                this.addListener(new ItemListClickListener());
        }

        private void itemListAddRow(Item addItem, final Items item) throws ConversionException, ReadOnlyException {
                addItem.getItemProperty("Name").setValue(item.getName());
                addItem.getItemProperty("Price Normal").setValue(item.getPrice());
                addItem.getItemProperty("Price Heroic").setValue(item.getPrice_hc());
                addItem.getItemProperty("Slot").setValue(item.getSlot());
                addItem.getItemProperty("Type").setValue(item.getType().toString());
        }

        private void itemListColumnHeaders() throws UnsupportedOperationException {
                addContainerProperty("Name", String.class, "");
                addContainerProperty("Price Normal", Double.class, 0);
                addContainerProperty("Price Heroic", Double.class, 0);
                addContainerProperty("Slot", String.class, "");
                addContainerProperty("Type", String.class, "");
        }

        public void clear() {
                this.removeAllItems();
        }

        public void printList() {
                clear();
                itemListColumnHeaders();
                List<Items> itemses = itemDAO.getItems();

                for (final Items item : itemses) {
                        Item addItem = addItem(item);
                        itemListAddRow(addItem, item);
                }
        }

        private boolean isAdmin() {
                final SiteUser siteUser = (SiteUser) getApplication().getUser();
                return siteUser != null && siteUser.getLevel() == 1;
        }

        private class ItemListClickListener implements ItemClickListener {

                public ItemListClickListener() {
                }

                @Override
                public void itemClick(ItemClickEvent event) {
                        if (event.isCtrlKey()) {
                                Items item = (Items) event.getItemId();
                                if (isAdmin()) {
                                        ItemEditWindow info = new ItemEditWindow(item);
                                        info.printInfo();
                                        getApplication().getMainWindow().addWindow(info);
                                } else {
                                        ItemInfoWindow info = new ItemInfoWindow(item);
                                        info.printInfo();
                                        getApplication().getMainWindow().addWindow(info);
                                }
                        }
                }
        }
}
