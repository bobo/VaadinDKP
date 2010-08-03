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
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * The Application's "main" class
 */
@SuppressWarnings("serial")
public class UnknownEntityDKP extends Application
{
    private Window window;

    @Override
    public void init()
    {
        window = new Window("Unknown Entity DKP");
//        window.setTheme("ue");
        ItemDAO itemDAO = new ItemDB();
        HorizontalLayout hzChar = new HorizontalLayout();
	CharacterDAO characterDAO = new CharacterDB();
	Characters charachters = new Characters(characterDAO);
	hzChar.addComponent(charachters);
	charachters.printList();
	window.addComponent(hzChar);
        setMainWindow(window);

        HorizontalLayout hzl = new HorizontalLayout();
        window.addComponent(hzl);
        hzl.setSpacing(true);

        VerticalLayout vertDKP = new VerticalLayout();
        vertDKP.addComponent(new Label("DKP"));
	DKPList dKPList = new DKPList(characterDAO);
	vertDKP.addComponent(dKPList);
	dKPList.printList();
        hzl.addComponent(vertDKP);

        VerticalLayout vertItem = new VerticalLayout();
        vertItem.addComponent(new Label("Items"));
	ItemList itemList = new ItemList(itemDAO);
        vertItem.addComponent(itemList);
        itemList.printList();
        hzl.addComponent(vertItem);

        RaidDAO raidDAO = new RaidDB();
        VerticalLayout vertRaid = new VerticalLayout();
        vertRaid.addComponent(new Label("Raids"));
        RaidList raidList = new RaidList(raidDAO);
        vertRaid.addComponent(raidList);
        raidList.printList();
        hzl.addComponent(vertRaid);
        
        final Button adduserBtn = new Button("Add Character");
        adduserBtn.addListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                AddUser addUser = new AddUser();
                addUser.printInfo();
                getMainWindow().addWindow(addUser);
                addUser.center();
                addUser.setWidth("400px");
                addUser.setHeight("500px");
            }
        });
        VerticalLayout selection = new VerticalLayout();
        selection.addComponent(adduserBtn);
        window.addComponent(selection);

    }
    
}
