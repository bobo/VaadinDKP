/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity.raids;

import database.CharacterDB;
import database.RaidDB;
import com.unknown.entity.character.*;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alde
 */
class RaidRewardAddWindow extends Window {

        private Raid raid;

        public RaidRewardAddWindow(Raid raid) {
                this.raid = raid;
                this.setWidth("250px");
                this.setHeight("550px");
                this.setCaption("Add reward for raid " + raid.getComment() + " (id " + raid.getId() + ")");
        }

        public void printInfo() {
                final TextField attendants = new TextField("Attendants");
                attendants.setRows(20);
                attendants.setImmediate(true);

                final TextField shares = new TextField("Shares");
                shares.setImmediate(true);

                final TextField comment = new TextField("Comment");
                comment.setImmediate(true);

                Button addButton = new Button("Add");

                VerticalLayout vert = new VerticalLayout();
                vert.addComponent(comment);
                vert.addComponent(shares);
                vert.addComponent(attendants);
                vert.addComponent(addButton);

                addComponent(vert);

                addButton.addListener(new Button.ClickListener() {

                        @Override
                        public void buttonClick(ClickEvent event) {
                                final List<String> attendantlist = new ArrayList<String>();
                                attendantlist.addAll(splitCharsToArray(attendants.getValue().toString()));
                                int success = addReward(comment.getValue().toString(), Integer.parseInt(shares.getValue().toString()), attendantlist, raid);
                        }
                });
        }

        private List<String> splitCharsToArray(String attendants) {
                System.out.println(attendants);
                List<String> list = new ArrayList<String>();
                String[] parts = attendants.split("\n");
                list.addAll(Arrays.asList(parts));
                return list;
        }

        private int addReward(String comment, Integer shares, List<String> attendantlist, Raid raid) {
                RaidDAO raidDao = new RaidDB();
                try {
                        List<String> invalidchars = findInvalidCharacters(attendantlist);
                        if (invalidchars.size() == 0) {
                                return raidDao.addReward(comment, shares, attendantlist, raid);
                        } else {
                                addComponent(new Label("Invalid characters"));
                                for (String s : invalidchars) {
                                        addComponent(new Label(s));
                                }
                        }
                } catch (SQLException ex) {
                        Logger.getLogger(RaidRewardAddWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
                return 0;
        }

        private List<String> findInvalidCharacters(List<String> attendantlist) {
                CharacterDAO chardao = new CharacterDB();
                List<User> userlist = chardao.getUsers();
                List<String> charname = new ArrayList<String>();
                for (User u : userlist) {
                        charname.add(u.getUsername());
                }
                for (String s : charname) {
                        attendantlist.remove(s);
                }
                return attendantlist;
        }
}
