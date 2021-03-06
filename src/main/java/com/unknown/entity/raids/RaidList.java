/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity.raids;

import com.unknown.entity.PopUpControl;
import com.unknown.entity.dao.*;
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
public class RaidList extends Table implements RaidInfoListener {

        private RaidDAO raidDAO;
        IndexedContainer ic;
        private final RaidList raidList = this;

        public RaidList(RaidDAO raidDAO) {
                this.raidDAO = raidDAO;
                this.ic = new IndexedContainer();
                this.setSelectable(true);
                this.setHeight("500px");
                this.setWidth("300px");
                this.addListener(new RaidListClickListener());
                raidListSetHeaders();
        }

        private void update() {
                ic.removeAllItems();
                ic.removeAllContainerFilters();
                printList();
        }

        private void raidListAddRow(Item addItem, final Raid raid) throws ReadOnlyException, ConversionException {
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
                //           clear();
                List<Raid> raids = raidDAO.getRaids();

                for (final Raid raid : raids) {
                        Item addItem = addItem(raid);
                        raidListAddRow(addItem, raid);

                }
        }

        public void filter(Object value, String column) {
                ic.removeAllContainerFilters();
                ic.addContainerFilter(column, filterString(value), true, false);
        }

        private String filterString(Object value) {
                if (value == null) {
                        return "";
                } else {
                        return value.toString();
                }
        }

        @Override
        public void onRaidInfoChanged() {
                update();
        }

        private class RaidListClickListener implements ItemClickListener {

                @Override
                public void itemClick(ItemClickEvent event) {
                        if (event.isCtrlKey()) {
                                Raid raid = (Raid) event.getItemId();
                                PopUpControl pop = new PopUpControl(RaidList.this.getApplication());
                                pop.setRaidList(raidList);
                                pop.showProperRaidWindow(raid);
                        }
                }
        }
}
