/*
 * Copyright 2009 IT Mill Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.unknown.entity;

import com.unknown.entity.dao.CharacterDAO;
import com.unknown.entity.dao.ItemDAO;
import com.unknown.entity.dao.RaidDAO;
import com.unknown.entity.database.CharacterDB;
import com.unknown.entity.database.ItemDB;
import com.unknown.entity.database.RaidDB;
import com.unknown.entity.raids.*;
import com.unknown.entity.character.*;
import com.unknown.entity.items.*;
import com.unknown.entity.panel.AdminPanel;
import com.vaadin.Application;
import com.vaadin.data.Property.ConversionException;
import com.vaadin.data.Property.ReadOnlyException;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import java.util.Collection;

/**
 * The Application's "main" class
 */
@SuppressWarnings("serial")
public class UnknownEntityDKP extends Application {

        private Window window;
        private final AdminPanel adminPanel = new AdminPanel();

        public HorizontalLayout HorizontalSegment(final DkpList dKPList, ItemList itemList, RaidList raidList) {
                final HorizontalLayout hzl = new HorizontalLayout();
                hzl.setSpacing(true);

                // Vertical DKP List
                VerticalLayout vertDKP = VerticalDKPListLayout(dKPList);
                hzl.addComponent(vertDKP);
                // Vertical Item List
                VerticalLayout vertItem = VerticalItemListLayout(itemList);
                hzl.addComponent(vertItem);

                // Vertical Raid List
                VerticalLayout vertRaid = VerticalRaidListLayout(raidList);
                hzl.addComponent(vertRaid);

                return hzl;
        }

        private VerticalLayout VerticalRaidListLayout(RaidList raidList) {
                VerticalLayout vertRaid = new VerticalLayout();
                vertRaid.addComponent(new Label("Raids"));
                vertRaid.addComponent(raidList);
                raidList.printList();
                return vertRaid;
        }

        private VerticalLayout VerticalItemListLayout(ItemList itemList) {
                VerticalLayout vertItem = new VerticalLayout();
                vertItem.addComponent(new Label("Items"));
                vertItem.addComponent(itemList);
                itemList.printList();
                return vertItem;
        }

        private VerticalLayout VerticalDKPListLayout(final DkpList dkpList) throws UnsupportedOperationException {
                VerticalLayout vertDKP = new VerticalLayout();
                vertDKP.addComponent(new Label("DKP"));
                vertDKP.addComponent(dkpList);
                dkpFilterBox(vertDKP, dkpList);

                dkpList.printList();
                return vertDKP;
        }

        private void dkpFilterBox(VerticalLayout vertDKP, final DkpList dkpList) throws UnsupportedOperationException, ReadOnlyException, ConversionException {
                final ComboBox filterDkp = new ComboBox("Filter");
                filterDkp.addStyleName("select-button");
                filterDkp.setWidth("180px");
                filterDkp.setImmediate(true);
                vertDKP.addComponent(filterDkp);
                filterDkp.addItem("<none>");
                for (Armor armor : Armor.values()) {
                        filterDkp.addItem(armor);
                }
                filterDkp.setNullSelectionAllowed(false);
                Collection<?> itemIds = filterDkp.getItemIds();
                filterDkp.setValue(itemIds.iterator().next());
                filterDkp.addListener(new filterChangeListener(dkpList, filterDkp));
        }

        @Override
        public void init() {
                window = new Window("Unknown Entity DKP");
                window.setTheme("ue");
                //        window.setTheme("chameleon-ue");
                setMainWindow(window);

                Drawings();
        }

        private void Drawings() {
                //  ----------- Declarations of Variables
                RaidDAO raidDAO = new RaidDB();
                CharacterDAO characterDAO = new CharacterDB();
                ItemDAO itemDAO = new ItemDB();

                final Button updateButton = new Button("Update");
                final CharacterList charList = new CharacterList(characterDAO);
                final DkpList dKPList = new DkpList(characterDAO);
                dKPList.addStyleName("small");
                final RaidList raidList = new RaidList(raidDAO);
                raidList.addStyleName("small");
                final ItemList itemList = new ItemList(itemDAO);
                itemList.addStyleName("small");
                final HorizontalLayout hzl = HorizontalSegment(dKPList, itemList, raidList);

                window.addComponent(adminPanel);
                adminPanel.init();

                // Character List based on Character Class
                characterListOnCharacterClass(charList);

                // DKP Table, Item Table, Raid Table
                window.addComponent(hzl);

                // Update Button ---- TO BE REMOVED EVENTUALLY
                UpdateButton(updateButton);
//				FileResource f = new FileResource("db.properties", this);

        }

        private void UpdateButton(final Button updateButton) {
                updateButton.addListener(new Button.ClickListener() {

                        @Override
                        public void buttonClick(ClickEvent event) {
                                window.removeAllComponents();
                                Drawings();
                        }
                });
                window.addComponent(updateButton);
        }

        private void characterListOnCharacterClass(final CharacterList charList) {
                HorizontalLayout hzChar = new HorizontalLayout();
                hzChar.addComponent(charList);
                charList.printList();
                window.addComponent(hzChar);
        }

        private static class filterChangeListener implements ValueChangeListener {

                private final DkpList dkpList;
                private final ComboBox filterDkp;

                public filterChangeListener(DkpList dkpList, ComboBox filterDkp) {
                        this.dkpList = dkpList;
                        this.filterDkp = filterDkp;
                }

                @Override
                public void valueChange(ValueChangeEvent event) {
                        if (filterDkp.getValue().equals("<none>")) {
                                dkpList.filter(null);
                        } else {
                                dkpList.filter(filterDkp.getValue());
                        }
                }
        }
}
