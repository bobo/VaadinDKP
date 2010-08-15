/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity.raids;

import com.unknown.entity.PopUpControl;
import com.unknown.entity.dao.*;
import com.unknown.entity.character.SiteUser;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
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
        IndexedContainer ic;

        public RaidList(RaidDAO raidDAO) {
                this.raidDAO = raidDAO;
                this.ic = new IndexedContainer();

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
                ic.addContainerProperty("Zone", String.class, "");
                ic.addContainerProperty("Comment", String.class, "");
                ic.addContainerProperty("Date", String.class, "");
                this.setContainerDataSource(ic);
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

        public void filter(Object value, String column) {
                ic.removeAllContainerFilters();
                ic.addContainerFilter("Armor", filterString(value), true, false);
        }

        private String filterString(Object value) {
                if (value == null) {
                        return "";
                } else {
                        return value.toString();
                }
        }

        private class RaidListClickListener implements ItemClickListener {

                @Override
                public void itemClick(ItemClickEvent event) {
                        if (event.isCtrlKey()) {
                                Raid raid = (Raid) event.getItemId();
                                PopUpControl pop = new PopUpControl(RaidList.this.getApplication());
                                pop.showProperRaidWindow(raid);
                        }
                }
        }
}
