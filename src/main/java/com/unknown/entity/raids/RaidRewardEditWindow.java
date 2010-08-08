/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity.raids;

import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.List;

/**
 *
 * @author alde
 */
class RaidRewardEditWindow extends Window {

        private final List<RaidChar> chars;
        private final int shares;

        RaidRewardEditWindow(List<RaidChar> chars, String comment, int shares) {
                this.chars = chars;
                this.shares = shares;
                setPositionX(600);
                setPositionY(200);
                this.setCaption("Edit reward: "+ comment);
                setWidth("300px");
                setHeight("400px");
        }
        	public void printInfo() {
		HorizontalLayout hzl = new HorizontalLayout();
		hzl.setSpacing(true);

                TextField attendants = charList();
                hzl.addComponent(attendants);

                TextField share = new TextField("Shares");
                share.setValue(shares);
                share.setImmediate(true);
                hzl.addComponent(share);
                
		addComponent(hzl);
	}

        private TextField charList() {
                TextField characters = new TextField("Characters");
                characters.setRows(20);
                for (RaidChar character : chars) {
                        characters.setValue(characters.getValue().toString()+character.getName()+"\n");
                }
                return characters;
        }
}
