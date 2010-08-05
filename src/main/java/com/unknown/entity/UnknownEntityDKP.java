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

import com.vaadin.Application;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import java.util.List;

/**
 * The Application's "main" class
 */
@SuppressWarnings("serial")
public class UnknownEntityDKP extends Application
{
    private Window window;

    public HorizontalLayout HorizontalSegment(DKPList dKPList, ItemList itemList, RaidList raidList ) {
         final HorizontalLayout hzl = new HorizontalLayout();
        hzl.setSpacing(true);

        VerticalLayout vertDKP = new VerticalLayout();
        vertDKP.addComponent(new Label("DKP"));
	vertDKP.addComponent(dKPList);
        ComboBox filterDKP = new ComboBox("Filter");
        vertDKP.addComponent(filterDKP);
        for (Armor armor : Armor.values()) {
            filterDKP.addItem(armor);
        }
	dKPList.printList();
        final Button addUsrBtn = new Button("Add Character");
        addUsrBtn.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                CharacterAdd addUser = new CharacterAdd();
                addUser.printInfo();
                addUser.setCaption("Add Character");
                getMainWindow().addWindow(addUser);
                addUser.center();
                addUser.setWidth("300px");
                addUser.setHeight("250px");
            }
        });
        vertDKP.addComponent(addUsrBtn);

        hzl.addComponent(vertDKP);

        VerticalLayout vertItem = new VerticalLayout();
        vertItem.addComponent(new Label("Items"));
        vertItem.addComponent(itemList);
        itemList.printList();
        final Button addItmBtn = new Button("Add Item");
        addItmBtn.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                ItemAdd addItem = new ItemAdd();
                addItem.printInfo();
                addItem.setCaption("Add Item");
                getMainWindow().addWindow(addItem);
                addItem.center();
                addItem.setWidth("300px");
                addItem.setHeight("420px");
            }
        });

        vertItem.addComponent(addItmBtn);
        hzl.addComponent(vertItem);


        VerticalLayout vertRaid = new VerticalLayout();
        vertRaid.addComponent(new Label("Raids"));
        vertRaid.addComponent(raidList);
        raidList.printList();
        final Button addRaidBtn = new Button("Add Raid");
        addRaidBtn.addListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
            }
        });

        vertRaid.addComponent(addRaidBtn);
        hzl.addComponent(vertRaid);

       return hzl;
    }

    @Override
    public void init()
    {
        window = new Window("Unknown Entity DKP");
//        window.setTheme("ue");
        ItemDAO itemDAO = new ItemDB();
        HorizontalLayout hzChar = new HorizontalLayout();
	CharacterDAO characterDAO = new CharacterDB();
        final Characters charList = new Characters(characterDAO);
        RaidDAO raidDAO = new RaidDB();
        hzChar.addComponent(charList);
	charList.printList();
	window.addComponent(hzChar);
        setMainWindow(window);
        final DKPList dKPList = new DKPList(characterDAO);
        final RaidList raidList = new RaidList(raidDAO);
	final ItemList itemList = new ItemList(itemDAO);
        final HorizontalLayout hzl = HorizontalSegment(dKPList, itemList, raidList);
        window.addComponent(hzl);
        
        final Button updateButton = new Button("Update");
        updateButton.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                raidList.printList();
                dKPList.printList();
                charList.printList();
                itemList.printList();
            }
        });

        window.addComponent(updateButton);
    }
    
}
