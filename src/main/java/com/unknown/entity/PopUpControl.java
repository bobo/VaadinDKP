/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity;

import com.unknown.entity.character.SiteUser;
import com.unknown.entity.character.User;
import com.unknown.entity.character.windows.CharacterEditWindow;
import com.unknown.entity.character.windows.CharacterInfoWindow;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Window;

/**
 *
 * @author alde
 */
public class PopUpControl extends Window {

        public PopUpControl() {
        }

        public void showProperCharWindow(User user) throws IllegalArgumentException, NullPointerException {
                if (isAdmin()) {
                        CharacterEditWindow info = new CharacterEditWindow(user);
                        info.printInfo();
                        getApplication().getMainWindow().addWindow(info);
                } else {
                        CharacterInfoWindow info = new CharacterInfoWindow(user);
                        info.printInfo();
                        getApplication().getMainWindow().addWindow(info);
                }
        }

        private boolean isAdmin() {
                final SiteUser siteUser = (SiteUser) getApplication().getUser();
                return siteUser != null && siteUser.getLevel() == 1;
        }
}
