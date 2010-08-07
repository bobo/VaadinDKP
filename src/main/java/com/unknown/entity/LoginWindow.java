/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unknown.entity;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;


public class LoginWindow extends Window{



    private TextField userName = new TextField("Username:");
    private TextField password = new TextField("Password");
    private Button submit = new Button("Login");

    public LoginWindow() {
        this.center();
        this.setModal(true);
        this.setCaption("Login...");
        password.setSecret(true);
        addComponent(userName);
        userName.focus();
        addComponent(password);
        addComponent(submit);
        submit.setClickShortcut(KeyCode.ENTER);
        submit.addStyleName("primary");
        this.setWidth("200px");
        this.setHeight("200px");

        super.attach();
        submit.addListener(new LoginListener(this));

    }

    private class LoginListener implements ClickListener {

        final private Window window;

        public LoginListener(Window comp) {
            this.window = comp;
        }
        @Override
        public void buttonClick(ClickEvent event) {
            if (userName.getValue().toString().equals("admin") && password.getValue().toString().equals("admin")) {
                getApplication().setUser("admin");
                close();
            } else {

            }
        }
    }
}
