/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unknown.entity;

import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 *
 * @author bobo
 */
public class CharachterInfo extends Window{
private final User user;

    public CharachterInfo(User user) {
	this.user = user;
    }


    public void printInfo() {
	addComponent(new Label("Charachter information"));
	addComponent(new Label("Name: "+user.getUsername()));
	addComponent(new Label("Status: "+(user.isActive() ? "Active" : "Inactive")));

	addComponent(new Label("DKP"));
	GridLayout dkpGrid = new GridLayout(2, 4);
	dkpGrid.addComponent(new Label("Shares"), 0, 0);
	dkpGrid.addComponent(new Label("DKP Earned"), 0, 1);
	dkpGrid.addComponent(new Label("DKP Spent"), 0, 2);
	dkpGrid.addComponent(new Label("DKP Left"), 0, 3);

	dkpGrid.addComponent(new Label(""+user.getShares()), 1, 0);
	dkpGrid.addComponent(new Label(""+user.getDKP()), 1, 1);
	dkpGrid.addComponent(new Label(""+user.getDKP()), 1, 2);
	dkpGrid.addComponent(new Label(""+user.getDKP()), 1, 3);
	addComponent(dkpGrid);


    }



}
