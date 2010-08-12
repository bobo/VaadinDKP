/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity.panel;

import com.unknown.entity.LoginWindow;
import com.unknown.entity.character.CharacterAddWindow;
import com.unknown.entity.items.ItemAddWindow;
import com.unknown.entity.raids.RaidAddWindow;
import com.vaadin.terminal.FileResource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

/**
 *
 * @author bobo
 */
public class AdminPanel extends HorizontalLayout implements MyLoginListener {

        private Button loginBtn = new Button();

        public AdminPanel() {
                loginBtn.setIcon(new ThemeResource("../ue/img/key3.png"));
                this.addComponent(loginBtn);
                this.setSpacing(true);
                loginBtn.setStyle(Button.STYLE_LINK);
                loginBtn.addListener(new LoginClickListener());
        }

        public void login() {
                this.removeAllComponents();
                if (validUser()) {
                        final Button logOutButton = new Button("");
                        logOutButton.setStyle(Button.STYLE_LINK);
                        logOutButton.addListener(new LogOutListener());
                        this.addComponent(logOutButton);
                        final Button addUsrBtn = new Button("Add Character");
                        addUsrBtn.addListener(new AddUserListener());
                        this.addComponent(addUsrBtn);
                        final Button addItmBtn = new Button("Add Item");
                        addItmBtn.addListener(new AddItemListener());
                        this.addComponent(addItmBtn);
                        final Button addRaidBtn = new Button("Add Raid");
                        addRaidBtn.addListener(new AddRaidListener());
                        this.addComponent(addRaidBtn);
                } else {
                        addComponent(loginBtn);
                }

        }

        private boolean validUser() {
                final Object username = getApplication().getUser();
                return username != null && username.toString().equals("admin");

        }

        private Window getMainWindow() {
                return getApplication().getMainWindow();
        }

        @Override
        public void onLogin() {
                login();
        }

        private class AddItemListener implements ClickListener {

                public AddItemListener() {
                }

                @Override
                public void buttonClick(ClickEvent event) {
                        ItemAddWindow addItem = new ItemAddWindow();
                        addItem.printInfo();
                        getMainWindow().addWindow(addItem);
                }
        }

        private class AddRaidListener implements ClickListener {

                public AddRaidListener() {
                }

                @Override
                public void buttonClick(ClickEvent event) {
                        RaidAddWindow addRaid = new RaidAddWindow();
                        addRaid.printInfo();
                        getMainWindow().addWindow(addRaid);
                }
        }

        private class AddUserListener implements ClickListener {

                public AddUserListener() {
                }

                @Override
                public void buttonClick(ClickEvent event) {
                        CharacterAddWindow addUser = new CharacterAddWindow();
                        addUser.printInfo();
                        getMainWindow().addWindow(addUser);
                }
        }

        private class LoginClickListener implements ClickListener {

                public LoginClickListener() {
                }

                @Override
                public void buttonClick(ClickEvent event) {
                        if (getMainWindow().getApplication().getUser() == null) {
                                LoginWindow loginWindow = new LoginWindow();
                                loginWindow.addLoginListener(AdminPanel.this);
                                getMainWindow().addWindow(loginWindow);
                                loginWindow.attach();
                        } else {
                                getMainWindow().addComponent(new Label("User: " + (getApplication() != null ? getApplication().getUser() : "")));
                        }
                }
        }

        private class LogOutListener implements ClickListener {

                public LogOutListener() {
                }

                @Override
                public void buttonClick(ClickEvent event) {
                        if (getMainWindow().getApplication().getUser() != null) {
                                 getMainWindow().getApplication().setUser(null);
                                 login();
                        }
                }
        }
}
