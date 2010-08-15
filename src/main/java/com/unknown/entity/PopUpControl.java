/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity;

import com.unknown.entity.character.SiteUser;
import com.unknown.entity.character.User;
import com.unknown.entity.character.windows.CharacterEditWindow;
import com.unknown.entity.character.windows.CharacterInfoWindow;
import com.unknown.entity.items.Items;
import com.unknown.entity.items.windows.ItemEditWindow;
import com.unknown.entity.items.windows.ItemInfoWindow;
import com.unknown.entity.raids.Raid;
import com.unknown.entity.raids.windows.RaidEditWindow;
import com.unknown.entity.raids.windows.RaidInfoWindow;
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
                        showCharEditWindow(user);
                } else {
                        showCharInfoWindow(user);
                }
        }

        private void showCharEditWindow(User user) throws IllegalArgumentException, NullPointerException {
                CharacterEditWindow info = new CharacterEditWindow(user);
                info.printInfo();
                app.getMainWindow().addWindow(info);
        }

        private void showCharInfoWindow(User user) throws NullPointerException, IllegalArgumentException {
                CharacterInfoWindow info = new CharacterInfoWindow(user);
                info.printInfo();
                app.getMainWindow().addWindow(info);
        }

        private boolean isAdmin() {
                final SiteUser siteUser = (SiteUser) app.getUser();
                return siteUser != null && siteUser.getLevel() == 1;
        }

        public void showProperItemWindow(Items item) throws NullPointerException, IllegalArgumentException {
                if (isAdmin()) {
                        showItemEditWindow(item);
                } else {
                        showItemInfoWindow(item);
                }
        }

        private void showItemInfoWindow(Items item) throws NullPointerException, IllegalArgumentException {
                ItemInfoWindow info = new ItemInfoWindow(item);
                info.printInfo();
                app.getMainWindow().addWindow(info);
        }

        private void showItemEditWindow(Items item) throws NullPointerException, IllegalArgumentException {
                ItemEditWindow info = new ItemEditWindow(item);
                info.printInfo();
                app.getMainWindow().addWindow(info);
        }

        public void showProperRaidWindow(Raid raid) {
                if (isAdmin()) {
                        showRaidEditWindow(raid);
                } else {
                        showRaidInfoWindow(raid);
                }
        }

        private void showRaidInfoWindow(Raid raid) throws IllegalArgumentException, NullPointerException {
                RaidInfoWindow info = new RaidInfoWindow(raid);
                info.printInfo();
                app.getMainWindow().addWindow(info);
        }

        private void showRaidEditWindow(Raid raid) throws NullPointerException, IllegalArgumentException {
                RaidEditWindow info = new RaidEditWindow(raid);
                info.printInfo();
                app.getMainWindow().addWindow(info);
        }
}
