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

        private final Button loginBtn = new Button();
		private final Button addUsrBtn = new Button("Add Character");
        private final Button addRaidBtn = new Button("Add Raid");
        private final Button addItmBtn = new Button("Add Item");
		private final Button logOutButton = new Button("");

		public AdminPanel() {
                loginBtn.setIcon(new ThemeResource("../ue/img/key3.png"));
                this.addComponent(loginBtn);
                this.setSpacing(true);
                loginBtn.setStyle(Button.STYLE_LINK);
                loginBtn.addListener(new LoginClickListener());
				addUsrBtn.addListener(new AddUserListener());
                addRaidBtn.addListener(new AddRaidListener());
				addItmBtn.addListener(new AddItemListener());
                logOutButton.addListener(new LogOutListener());
				logOutButton.setStyle(Button.STYLE_LINK);
        }
		
		public void init() {
			addComponent(loginBtn);
		}

        private void login() {
                this.removeAllComponents();
                if (validUser()) {
                        this.addComponent(logOutButton);                        
                        this.addComponent(addUsrBtn);
                        this.addComponent(addItmBtn);
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

                @Override
                public void buttonClick(ClickEvent event) {
                        ItemAddWindow addItem = new ItemAddWindow();
                        addItem.printInfo();
                        getMainWindow().addWindow(addItem);
                }
        }

        private class AddRaidListener implements ClickListener {

                @Override
                public void buttonClick(ClickEvent event) {
                        RaidAddWindow addRaid = new RaidAddWindow();
                        addRaid.printInfo();
                        getMainWindow().addWindow(addRaid);
                }
        }

        private class AddUserListener implements ClickListener {

                @Override
                public void buttonClick(ClickEvent event) {
                        CharacterAddWindow addUser = new CharacterAddWindow();
                        addUser.printInfo();
                        getMainWindow().addWindow(addUser);
                }
        }

        private class LoginClickListener implements ClickListener {

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

                @Override
                public void buttonClick(ClickEvent event) {
                        if (getMainWindow().getApplication().getUser() != null) {
                                 getMainWindow().getApplication().setUser(null);
                                 login();
                        }
                }
        }
}
