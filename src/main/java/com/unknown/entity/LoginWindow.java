/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity;

import com.unknown.entity.character.SiteUser;
import com.unknown.entity.character.User;
import com.unknown.entity.dao.LoginDao;
import com.unknown.entity.panel.MyLoginListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import java.util.ArrayList;
import java.util.List;

public class LoginWindow extends Window {

	private TextField userName = new TextField("Username:");
	private TextField password = new TextField("Password");
	private Button submit = new Button("Login");
	private List<MyLoginListener> listeners = new ArrayList<MyLoginListener>();
	private final LoginDao loginDao = new LoginDao();

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

		submit.addListener(new LoginClickListener());

	}

	public void addLoginListener(MyLoginListener listener) {
		listeners.add(listener);
	}

	private class LoginClickListener implements ClickListener {

		@Override
		public void buttonClick(ClickEvent event) {
			SiteUser user = loginDao.checkLogin(userName.getValue().toString(), password.getValue().toString());
			if (user != null) {
				getApplication().setUser(user);
				notifyListeners();
				close();
			} else {
			}
		}
	}

	private void notifyListeners() {
		for (MyLoginListener loginListener : listeners) {
			loginListener.onLogin();
		}
	}
}
