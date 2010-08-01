/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity;

import com.vaadin.data.Item;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author bobo
 */
public class DKPList extends Table {

    private CharacherDAO characherDAO;

    public DKPList(CharacherDAO characherDAO) {
	this.characherDAO = characherDAO;
	addContainerProperty("Name", String.class, "");
	addContainerProperty("DKP", Double.class, 0);
	this.addListener(new ItemClickListener() {

	    @Override
	    public void itemClick(ItemClickEvent event) {
		User user = (User) event.getItemId();
		CharachterInfo info = new CharachterInfo(user);
		info.printInfo();
		getApplication().getMainWindow().addWindow(info);
		info.center();
		info.setWidth("400px");
		info.setHeight("400px");
	    }
	});
    }

    public void printList() {
	List<User> users = characherDAO.getUsers();
	Collections.sort(users, new Comparator<User>() {

	    @Override
	    public int compare(User t, User t1) {
		return t.getDKP() < t1.getDKP() ? 1 : 0;
	    }
	});
	for (final User user : users) {
	    Item addItem = addItem(user);
	    addItem.getItemProperty("Name").setValue(user.getUsername());
	    addItem.getItemProperty("DKP").setValue(user.getDKP());

	}
    }
}
