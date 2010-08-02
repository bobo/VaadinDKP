/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity;

import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.FileResource;
import com.vaadin.terminal.Resource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Embedded;
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
public class Characters extends HorizontalLayout {

    CharacterDAO characterDAO;

    public Characters(CharacterDAO characherDAO) {
        this.characterDAO = characherDAO;
        setMargin(true);
        setSpacing(true);

    }

    public void printList() {
        List<Role> roles = Arrays.asList(Role.values());
        Collections.sort(roles, new ToStringComparator());
        final Button adduserBtn = new Button("Add Character");
        adduserBtn.addListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                AddUser addUser = new AddUser();
                addUser.printInfo();
                getApplication().getMainWindow().addWindow(addUser);
                addUser.center();
                addUser.setWidth("400px");
                addUser.setHeight("500px");
            }
        });
//        VerticalLayout selection = new VerticalLayout();
//        addComponent(selection);
//        selection.addComponent(adduserBtn);

        for (Role r : roles) {
            VerticalLayout roleList = new VerticalLayout();
            addComponent(roleList);
            /*            String foo = r.toString();
            if (foo.equalsIgnoreCase("DeathKnight")) { foo = "Death Knight"; }
            Label label = new Label(foo);       */
            Label label = new Label(r.toString());
            String foo = "";
            if (r.toString().equals("DeathKnight")) {
                foo = "dk";
            } else if (r.toString().equals("Paladin")) {
                foo = "pala";
            } else {
                foo = r.toString();
            }

            Embedded e = new Embedded("", new ThemeResource("../ue/img/"+r.toString().toLowerCase()+".png"));
            roleList.addComponent(e);
            addUsersForRole(r, roleList);
        }
    }

    private void addUsersForRole(Role r, VerticalLayout roleList) {
        for (final User user : characterDAO.getUsersWithRole(r)) {
            final Button userBtn = new Button(user.toString());
            userBtn.setStyleName(Button.STYLE_LINK);
            userBtn.addListener(new Button.ClickListener() {


                @Override
                public void buttonClick(ClickEvent event) {
                    CharacterInfo info = new CharacterInfo(user);
                    info.printInfo();
                    info.setCaption(user.getUsername());
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
