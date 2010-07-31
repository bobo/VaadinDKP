/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity;

import com.vaadin.data.Item;
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

    }

    public void printList() {
	List<User> users = characherDAO.getUsers();
	Collections.sort(users, new Comparator<User>() {

	    @Override
	    public int compare(User t, User t1) {
		return t.getDKP() < t1.getDKP() ? 1 : 0;
	    }
	});
	for (User user : users) {
	    Item addItem = addItem(user);
	    addItem.getItemProperty("Name").setValue(user.getUsername());
	    addItem.getItemProperty("DKP").setValue(user.getDKP());
	}
    }
}
