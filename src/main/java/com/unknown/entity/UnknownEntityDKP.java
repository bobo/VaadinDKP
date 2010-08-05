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

import com.unknown.entity.raids.RaidDB;
import com.unknown.entity.raids.RaidDAO;
import com.unknown.entity.raids.RaidList;
import com.unknown.entity.raids.RaidAdd;
import com.unknown.entity.character.CharacterAdd;
import com.unknown.entity.character.DKPList;
import com.unknown.entity.character.CharacterDB;
import com.unknown.entity.character.CharacterDAO;
import com.unknown.entity.character.Characters;
import com.unknown.entity.items.ItemDB;
import com.unknown.entity.items.ItemList;
import com.unknown.entity.items.ItemDAO;
import com.unknown.entity.items.ItemAdd;
import com.vaadin.Application;
import com.vaadin.data.Property.ValueChangeEvent;
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

    public HorizontalLayout HorizontalSegment(final DKPList dKPList, ItemList itemList, RaidList raidList ) {
         final HorizontalLayout hzl = new HorizontalLayout();
        hzl.setSpacing(true);

        VerticalLayout vertDKP = new VerticalLayout();
        vertDKP.addComponent(new Label("DKP"));
	vertDKP.addComponent(dKPList);
        final ComboBox filterDKP = new ComboBox("Filter");
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
                getMainWindow().addWindow(addUser);

            }
        });
        filterDKP.addListener(new ComboBox.ValueChangeListener() {

            @Override
            public void valueChange(ValueChangeEvent event) {
                dKPList.printList();
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
        HorizontalLayout hRaid = new HorizontalLayout();
        final Button addRaidBtn = new Button("Add Raid");
        addRaidBtn.addListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                RaidAdd addRaid = new RaidAdd();
                addRaid.printInfo();
                addRaid.setCaption("Add Raid");
                getMainWindow().addWindow(addRaid);
                addRaid.center();
                addRaid.setWidth("300px");
                addRaid.setHeight("420px");
            }
        });
        final Button editRaidBtn = new Button("Edit Raid");
        editRaidBtn.addListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
            }
        });
        
        hRaid.addComponent(addRaidBtn);
        hRaid.addComponent(editRaidBtn);
        vertRaid.addComponent(hRaid);
        hzl.addComponent(vertRaid);

       return hzl;
    }

    @Override
    public void init()
    {
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

        final Button loginButton = new Button("Login");
        final Button updateButton = new Button("Update");
        final Characters charList = new Characters(characterDAO);
        final DKPList dKPList = new DKPList(characterDAO);
        final RaidList raidList = new RaidList(raidDAO);
        final ItemList itemList = new ItemList(itemDAO);
        final HorizontalLayout hzl = HorizontalSegment(dKPList, itemList, raidList);

        // Paint stuff

        window.addComponent(loginButton);

        // Character List based on Class
        HorizontalLayout hzChar = new HorizontalLayout();
        hzChar.addComponent(charList);
        charList.printList();
        window.addComponent(hzChar);

        // DKP Table, Item Table, Raid Table
        window.addComponent(hzl);

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
