/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity.items;

import com.vaadin.data.Item;
import com.vaadin.data.Property.ConversionException;
import com.vaadin.data.Property.ReadOnlyException;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.GridLayout.OutOfBoundsException;
import com.vaadin.ui.GridLayout.OverlapsException;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;

/**
 *
 * @author alde
 */
public class ItemInfoWindow extends Window {

        private final Items item;

        public ItemInfoWindow(Items item) {
                this.item = item;
                this.setCaption(item.getName());
                this.addStyleName("opaque");
                this.center();
                this.getContent().setSizeUndefined();
        }

        public void printInfo() {
                itemInformation();
                itemGrid();
                itemLootedByTable();
        }

        private void itemLootedByTable() {
                addComponent(new Label("Looted by"));
                HorizontalLayout hzl = new HorizontalLayout();
                hzl.setSpacing(true);
                Table Attendants = lootList(item);
                if (Attendants.size() > 0) {
                        hzl.addComponent(Attendants);
                } else {
                        hzl.addComponent(new Label("Not looted by anyone"));
                }
                addComponent(hzl);
        }

        private void itemGrid() throws OverlapsException, OutOfBoundsException {
                GridLayout gl = new GridLayout(3, 3);
                gl.setWidth("300px");
                gl.addComponent(new Label("Normal "), 1, 0);
                gl.addComponent(new Label("Heroic "), 2, 0);
                gl.addComponent(new Label("WowID: "), 0, 1);
                final Button wowIdBtn = new Button("" + item.getWowId());
                wowIdBtn.setStyleName(Button.STYLE_LINK);
                wowIdBtn.addListener(new WowIdButtonClickListener());
                gl.addComponent(wowIdBtn, 1, 1);
                final Button wowIdBtnhc = new Button("" + item.getWowId_hc());
                wowIdBtnhc.setStyleName(Button.STYLE_LINK);
                wowIdBtnhc.addListener(new WowIdHcButtonClickListener());
                gl.addComponent(wowIdBtnhc, 2, 1);
                gl.addComponent(new Label("Price: "), 0, 2);
                gl.addComponent(new Label("" + item.getPrice()), 1, 2);
                gl.addComponent(new Label("" + item.getPrice_hc()), 2, 2);
                addComponent(gl);
        }

        private void itemInformation() {
                addComponent(new Label("Item information"));
                addComponent(new Label("Name: " + item.getName()));
                addComponent(new Label("Slot: " + item.getSlot()));
                addComponent(new Label("Type: " + item.getType()));
        }

        private Table lootList(Items item) {
                Table tbl = new Table();
                itemTableHeaders(tbl);
                tbl.setHeight(150);
                for (ItemLooter looters : item.getItemList()) {
                        Item addItem = tbl.addItem(looters.getId());
                        itemTableRowAdd(addItem, looters);

                }
                return tbl;
        }

        private void itemTableRowAdd(Item addItem, ItemLooter looters) throws ConversionException, ReadOnlyException {
                addItem.getItemProperty("Name").setValue(looters.getName());
                addItem.getItemProperty("Price").setValue(looters.getPrice());
                addItem.getItemProperty("Raid").setValue(looters.getRaid());
                addItem.getItemProperty("Date").setValue(looters.getDate());
        }

        private void itemTableHeaders(Table tbl) throws UnsupportedOperationException {
                tbl.addContainerProperty("Name", String.class, "");
                tbl.addContainerProperty("Price", Double.class, 0);
                tbl.addContainerProperty("Raid", String.class, "");
                tbl.addContainerProperty("Date", String.class, "");
        }

        private class WowIdButtonClickListener implements ClickListener {

                public WowIdButtonClickListener() {
                }

                @Override
                public void buttonClick(ClickEvent event) {
                        String url = "http://www.wowhead.com/item=" + item.getWowId();
                        getWindow().open(new ExternalResource(url), "_blank");
                }
        }

        private class WowIdHcButtonClickListener implements ClickListener {

                public WowIdHcButtonClickListener() {
                }

                @Override
                public void buttonClick(ClickEvent event) {
                        String url = "http://www.wowhead.com/item=" + item.getWowId_hc();
                        getWindow().open(new ExternalResource(url), "_blank");
                }
        }
}
