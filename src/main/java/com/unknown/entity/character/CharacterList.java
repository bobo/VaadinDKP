/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity.character;

import com.unknown.entity.Role;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author bobo
 */
public class CharacterList extends HorizontalLayout {

    CharacterDAO characterDAO;

    public CharacterList(CharacterDAO characherDAO) {
        this.characterDAO = characherDAO;
    }
    private void clear() {
        this.removeAllComponents();
    }

    public void printList() {
        clear();
        List<Role> roles = Arrays.asList(Role.values());
        Collections.sort(roles, new ToStringComparator());

        for (Role r : roles) {
            VerticalLayout roleList = new VerticalLayout();
            addComponent(roleList);
            Embedded e = new Embedded("", new ThemeResource("../ue/img/"+r.toString().toLowerCase()+".png"));
            roleList.addComponent(e);
            addUsersForRole(r, roleList);
        }
    }

    private void addUsersForRole(Role r, VerticalLayout roleList) {
        for (final Character user : characterDAO.getUsersWithRole(r)) {
            final Button userBtn = new Button(user.toString());
            userBtn.setStyleName(Button.STYLE_LINK);
            userBtn.addListener(new Button.ClickListener() {

                @Override
                public void buttonClick(ClickEvent event) {
                    CharacterInfoWindow info = new CharacterInfoWindow(user);
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
