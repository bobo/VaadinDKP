/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author bobo
 */
public class Charachters extends HorizontalLayout {
CharacherDAO characherDAO;

    public Charachters(CharacherDAO characherDAO) {
	this.characherDAO = characherDAO;
	setMargin(true);
	setSpacing(true);

    }

    public void printList() {
	List<Role> roles = Arrays.asList(Role.values());
	Collections.sort(roles, new ToStringComparator());
	for (Role r : roles) {
	    VerticalLayout roleList = new VerticalLayout();
	    addComponent(roleList);
	    Label label = new Label(r.toString());
	    roleList.addComponent(label);
	    addUsersForRole(r, roleList);
	}
    }

   



    private void addUsersForRole(Role r, VerticalLayout roleList) {
	for (final User user : characherDAO.getUsersWithRole(r)) {
	    final Button userBtn = new Button(user.toString());
	    userBtn.addListener(new Button.ClickListener() {

		@Override
		public void buttonClick(ClickEvent event) {
		    CharachterInfo info = new CharachterInfo(user);
		    info.printInfo();
		    getApplication().getMainWindow().addWindow(info);
		    info.center();
		    info.setWidth("400px");
		    info.setHeight("400px");


		}
	    });
	    roleList.addComponent(userBtn);
	}
    }

    

    private static class ToStringComparator implements Comparator<Role> {

	public ToStringComparator() {
	}

	@Override
	public int compare(Role t, Role t1) {
	    return t.toString().compareTo(t1.toString());
	}
    }
}



    
