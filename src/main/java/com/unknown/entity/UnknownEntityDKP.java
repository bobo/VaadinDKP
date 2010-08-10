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

import com.unknown.entity.raids.*;
import com.unknown.entity.character.*;
import com.unknown.entity.items.*;
import com.unknown.entity.panel.AdminPanel;
import com.vaadin.Application;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.terminal.FileResource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import java.io.File;

/**
 * The Application's "main" class
 */
@SuppressWarnings("serial")
public class UnknownEntityDKP extends Application {

        private Window window;

       private final AdminPanel adminPanel = new AdminPanel();
        public HorizontalLayout HorizontalSegment(final DKPList dKPList, ItemList itemList, RaidList raidList) {
                final HorizontalLayout hzl = new HorizontalLayout();

                final Object username = getMainWindow().getApplication().getUser();

                hzl.setSpacing(true);

                // Vertical DKP List
                VerticalLayout vertDKP = VerticalDKPListLayout(dKPList, username);
                hzl.addComponent(vertDKP);
                // Vertical Item List
                VerticalLayout vertItem = VerticalItemListLayout(itemList, username);
                hzl.addComponent(vertItem);

                // Vertical Raid List
                VerticalLayout vertRaid = VerticalRaidListLayout(raidList, username);
                hzl.addComponent(vertRaid);

                return hzl;
        }

        private VerticalLayout VerticalRaidListLayout(RaidList raidList, final Object username) {
                VerticalLayout vertRaid = new VerticalLayout();
                vertRaid.addComponent(new Label("Raids"));
                vertRaid.addComponent(raidList);
                raidList.printList();
                return vertRaid;
        }

        private VerticalLayout VerticalItemListLayout(ItemList itemList, final Object username) {
                VerticalLayout vertItem = new VerticalLayout();
                vertItem.addComponent(new Label("Items"));
                vertItem.addComponent(itemList);
                itemList.printList();
                return vertItem;
        }

        private VerticalLayout VerticalDKPListLayout(final DKPList dKPList, final Object username) throws UnsupportedOperationException {
                final IndexedContainer ic = new IndexedContainer();
                VerticalLayout vertDKP = new VerticalLayout();
                vertDKP.addComponent(new Label("DKP"));
                vertDKP.addComponent(dKPList);
                final ComboBox filterDKP = new ComboBox("Filter");
                filterDKP.setImmediate(true);
                vertDKP.addComponent(filterDKP);
                for (Armor armor : Armor.values()) {
                        filterDKP.addItem(armor);
                }
                filterDKP.addListener(new ValueChangeListener() {

                        @Override
                        public void valueChange(ValueChangeEvent event) {
                                dKPList.filter(filterDKP.getValue());
                        }
                });

                dKPList.printList();
                return vertDKP;
        }

        @Override
        public void init() {
                window = new Window("Unknown Entity DKP");
//        window.setTheme("ue");
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
                final DKPList dKPList = new DKPList(characterDAO);
                final RaidList raidList = new RaidList(raidDAO);
                final ItemList itemList = new ItemList(itemDAO);
                final HorizontalLayout hzl = HorizontalSegment(dKPList, itemList, raidList);

				window.addComponent(adminPanel);
        		adminPanel.login();
		        // Login button as an image
//                AdminPanel(loginButton);

                // Character List based on Character Class
                CharacterListOnCharacterClass(charList);

                // DKP Table, Item Table, Raid Table
                window.addComponent(hzl);

                // Update Button ---- TO BE REMOVED EVENTUALLY
                UpdateButton(updateButton);
//				FileResource f = new FileResource("db.properties", this);

        	window.addComponent(new Label(getContext().getBaseDirectory().getAbsolutePath()));
		}

        private void AdminPanel(final Button loginButton) {
                // Paint stuff
                HorizontalLayout hadmin = new HorizontalLayout();
                hadmin.addComponent(loginButton);
                hadmin.setSpacing(true);
                loginButton.setStyleName(Button.STYLE_LINK);
                loginButton.setIcon(new ThemeResource("../ue/img/key3.png"));
                final Object username = getMainWindow().getApplication().getUser();
                if (username != null && username.toString().equals("admin")) {
                        hadmin.addComponent(new Label("Welcome " + username.toString()));
                        final Button addUsrBtn = new Button("Add Character");
                        addUsrBtn.addListener(new Button.ClickListener() {

                                @Override
                                public void buttonClick(ClickEvent event) {
                                        CharacterAddWindow addUser = new CharacterAddWindow();
                                        addUser.printInfo();
                                        getMainWindow().addWindow(addUser);
                                }
                        });
                        hadmin.addComponent(addUsrBtn);
                        final Button addItmBtn = new Button("Add Item");
                        addItmBtn.addListener(new Button.ClickListener() {

                                @Override
                                public void buttonClick(ClickEvent event) {
                                        ItemAddWindow addItem = new ItemAddWindow();
                                        addItem.printInfo();
                                        getMainWindow().addWindow(addItem);
                                }
                        });
                        hadmin.addComponent(addItmBtn);
                        final Button addRaidBtn = new Button("Add Raid");
                        addRaidBtn.addListener(new Button.ClickListener() {

                                @Override
                                public void buttonClick(ClickEvent event) {
                                        RaidAddWindow addRaid = new RaidAddWindow();
                                        addRaid.printInfo();
                                        getMainWindow().addWindow(addRaid);
                                }
                        });
                        hadmin.addComponent(addRaidBtn);
                }
                window.addComponent(hadmin);
                loginButton.addListener(new Button.ClickListener() {

                        @Override
                        public void buttonClick(ClickEvent event) {
                                if (getMainWindow().getApplication().getUser() == null) {
                                        LoginWindow loginWindow = new LoginWindow();
										loginWindow.addLoginListener(adminPanel);
                                        getMainWindow().addWindow(loginWindow);
                                        loginWindow.attach();
                                } else                  if (username != null && username.toString().equals("admin")) {{
                                        getMainWindow().getApplication().setUser(null);
                                }}
                        }
                });
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

        private void CharacterListOnCharacterClass(final CharacterList charList) {
                HorizontalLayout hzChar = new HorizontalLayout();
                hzChar.addComponent(charList);
                charList.printList();
                window.addComponent(hzChar);
        }
}
