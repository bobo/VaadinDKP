/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity.raids;

import com.unknown.entity.raids.windows.*;
import com.unknown.entity.dao.*;
import com.unknown.entity.character.SiteUser;
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
                raidListSetHeaders();

                this.setHeight("500px");
                this.setWidth("300px");
                this.addListener(new RaidListClickListener());
        }

        private boolean isAdmin() {
                final SiteUser siteUser = (SiteUser) getApplication().getUser();
                return siteUser != null && siteUser.getLevel() == 1;
        }

        private void RaidListAddRow(Item addItem, final Raid raid) throws ReadOnlyException, ConversionException {
                addItem.getItemProperty("Zone").setValue(raid.getName());
                addItem.getItemProperty("Comment").setValue(raid.getComment());
                addItem.getItemProperty("Date").setValue(raid.getDate());
        }

        private void raidListSetHeaders() throws UnsupportedOperationException {
                addContainerProperty("Zone", String.class, "");
                addContainerProperty("Comment", String.class, "");
                addContainerProperty("Date", String.class, "");
        }

        public void clear() {
                this.removeAllItems();
        }

        public void printList() {
                clear();
                List<Raid> raids = raidDAO.getRaids();

                for (final Raid raid : raids) {
                        Item addItem = addItem(raid);
                        RaidListAddRow(addItem, raid);

                }
        }

        private class RaidListClickListener implements ItemClickListener {

                @Override
                public void itemClick(ItemClickEvent event) {
                        Raid raid = (Raid) event.getItemId();
                        if (isAdmin()) {
                                RaidEditWindow info = new RaidEditWindow(raid);
                                info.printInfo();
                                getApplication().getMainWindow().addWindow(info);
                        } else {
                                RaidInfoWindow info = new RaidInfoWindow(raid);
                                info.printInfo();
                                getApplication().getMainWindow().addWindow(info);
                        }
                }
        }
}
