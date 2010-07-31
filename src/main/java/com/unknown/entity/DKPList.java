/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity;

import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author bobo
 */
public class DKPList extends VerticalLayout {

    private CharacherDAO characherDAO;

    public DKPList(CharacherDAO characherDAO) {
	this.characherDAO = characherDAO;
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
	    addComponent(new Label(user.toString()+":"+user.getDKP()));
	}
    }
}
