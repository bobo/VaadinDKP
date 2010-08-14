/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity;

import com.unknown.entity.character.SiteUser;
import com.unknown.entity.character.User;
import com.unknown.entity.character.windows.CharacterEditWindow;
import com.unknown.entity.character.windows.CharacterInfoWindow;
import com.vaadin.Application;
import com.vaadin.ui.Window;

/**
 *
 * @author alde
 */
public class PopUpControl extends Window {

	private final Application app;

	public PopUpControl(Application app) {
		this.app = app;
	}

	public void showProperCharWindow(User user) throws IllegalArgumentException, NullPointerException {
		if (isAdmin()) {
			showEditWindow(user);
		} else {
			showInfoWindow(user);
		}
	}

	private void showEditWindow(User user) throws IllegalArgumentException, NullPointerException {
		CharacterEditWindow info = new CharacterEditWindow(user);
		info.printInfo();
		app.getMainWindow().addWindow(info);
	}


	private void showInfoWindow(User user) throws NullPointerException, IllegalArgumentException {
		CharacterInfoWindow info = new CharacterInfoWindow(user);
		info.printInfo();
		app.getMainWindow().addWindow(info);
	}

	private boolean isAdmin() {
		final SiteUser siteUser = (SiteUser) app.getUser();
		return siteUser != null && siteUser.getLevel() == 1;
	}
}
