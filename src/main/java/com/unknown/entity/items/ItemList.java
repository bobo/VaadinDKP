/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity.items;

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
                ItemListColumnHeaders();

                this.setHeight("500px");

                this.addListener(new ItemClickListener() {

                        @Override
                        public void itemClick(ItemClickEvent event) {
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
                });
        }

        private void ItemListAddRow(Item addItem, final Items item) throws ConversionException, ReadOnlyException {
                addItem.getItemProperty("Name").setValue(item.getName());
                addItem.getItemProperty("Price Normal").setValue(item.getPrice());
                addItem.getItemProperty("Price Heroic").setValue(item.getPrice_hc());
                addItem.getItemProperty("Slot").setValue(item.getSlot());
                addItem.getItemProperty("Type").setValue(item.getType().toString());
        }

        private void ItemListColumnHeaders() throws UnsupportedOperationException {
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
                List<Items> itemses = itemDAO.getItems();

                for (final Items item : itemses) {
                        Item addItem = addItem(item);
                        ItemListAddRow(addItem, item);
                }
        }

        private boolean isAdmin() {
                final SiteUser siteUser = (SiteUser) getApplication().getUser();
                return siteUser != null && siteUser.getLevel() == 1;
        }
}
