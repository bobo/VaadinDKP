/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity.character;

import com.unknown.entity.character.windows.CharacterInfoWindow;
import com.unknown.entity.character.windows.CharacterEditWindow;
import com.unknown.entity.dao.CharacterDAO;
import com.unknown.entity.Armor;
import com.unknown.entity.PopUpControl;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Table;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bobo
 */
public class DkpList extends Table {

        private CharacterDAO characterDAO;
        IndexedContainer ic = new IndexedContainer();

        public DkpList(CharacterDAO characterDAO) {
                this.characterDAO = characterDAO;

                dkpListSetColumnHeaders();
                this.setWidth("180px");
                this.setHeight("500px");

                this.addListener(new dkpListClickListener());
        }

        private void dkpListSetColumnHeaders() throws UnsupportedOperationException {
                ic.addContainerProperty("Name", String.class, "");
                ic.addContainerProperty("Armor", Armor.class, "");
                this.setContainerDataSource(ic);
                this.setColumnCollapsingAllowed(true);
                try {
                        this.setColumnCollapsed("Armor", true);
                } catch (IllegalAccessException ex) {
                        Logger.getLogger(DkpList.class.getName()).log(Level.SEVERE, null, ex);
                }
                addContainerProperty("DKP", Double.class, 0);
        }

        public void clear() {
                this.removeAllItems();
        }

        public void printList() {
                clear();
                List<User> users = characterDAO.getUsers();
                Collections.sort(users, new Comparator<User>() {

                        @Override
                        public int compare(User t, User t1) {
                                return t.getDKP() < t1.getDKP() ? 1 : 0;
                        }
                });
                for (final User user : users) {
                        Item addItem = addItem(user);
                        dkpListAddRow(addItem, user);
                }
        }

        private void dkpListAddRow(Item addItem, final User user) throws ConversionException, ReadOnlyException {
                addItem.getItemProperty("Name").setValue(user.getUsername());
                addItem.getItemProperty("Armor").setValue(user.getArmor());
                addItem.getItemProperty("DKP").setValue(user.getDKP());
        }

        public void filter(Object value) {
                ic.removeAllContainerFilters();
                ic.addContainerFilter("Armor", filterString(value), true, false);
        }

        private String filterString(Object value) {
                if (value == null) {
                        return "";
                } else {
                        return value.toString();
                }
        }

        private static class dkpListClickListener implements ItemClickListener {

                public dkpListClickListener() {
                }

                @Override
                public void itemClick(ItemClickEvent event) {
                        if (event.isCtrlKey()) {
                                PopUpControl pop = new PopUpControl();
                                pop.showProperCharWindow(event);
                        }
                }
        }
}
