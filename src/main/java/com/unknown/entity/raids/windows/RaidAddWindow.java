/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity.raids.windows;

import com.unknown.entity.dao.RaidDAO;
import com.unknown.entity.database.RaidDB;
import com.unknown.entity.raids.RaidInfoListener;
import com.vaadin.data.Property.ConversionException;
import com.vaadin.data.Property.ReadOnlyException;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 *
 * @author alde
 */
public class RaidAddWindow extends Window {

        private List<RaidInfoListener> listeners = new ArrayList<RaidInfoListener>();

        public RaidAddWindow() {
                this.setCaption("Add Raid");
                this.center();
                this.addStyleName("opaque");
                this.getContent().setSizeUndefined();
        }

        public void printInfo() {

                RaidDAO raidDAO = new RaidDB();
                List<String> zoneList = raidDAO.getRaidZoneList();

                VerticalLayout addItem = new VerticalLayout();

                ComboBox zone = raidAddWindowZoneComboBox(zoneList);
                addItem.addComponent(zone);

                TextField comment = raidAddWindowCommentField();
                addItem.addComponent(comment);

                TextField datum = raidAddWindowDateField();
                addItem.addComponent(datum);

                Button addButton = raidAddWindowAddButton(zone, comment, datum);
                Button closeButton = RaidAddWindowCloseButton();

                HorizontalLayout hzl = new HorizontalLayout();
                hzl.setSpacing(true);
                hzl.addComponent(addButton);
                hzl.addComponent(closeButton);
                addItem.addComponent(hzl);
                addComponent(addItem);
        }

        private Button RaidAddWindowCloseButton() {
                final Button cbtn = new Button("Close");
                cbtn.addListener(new Button.ClickListener() {

                        @Override
                        public void buttonClick(ClickEvent event) {
                                close();
                        }
                });
                return cbtn;
        }

        private Button raidAddWindowAddButton(final ComboBox zone, final TextField comment, final TextField datum) {
                final Button btn = new Button("Add");
                btn.addListener(new AddButtonClickListener(zone, comment, datum));
                return btn;
        }

        private TextField raidAddWindowDateField() throws ConversionException, ReadOnlyException {
                final TextField datum = new TextField("Date");
                datum.setImmediate(true);
                Date date = new Date();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                datum.setValue(dateFormat.format(date));
                return datum;
        }

        private TextField raidAddWindowCommentField() {
                final TextField comment = new TextField("Comment");
                comment.focus();
                comment.setImmediate(true);
                return comment;
        }

        public void addRaidInfoListener(RaidInfoListener listener) {
                listeners.add(listener);
        }

        private void notifyListeners() {
                for (RaidInfoListener raidListener : listeners) {
                        raidListener.onRaidInfoChanged();
                }
        }

        private ComboBox raidAddWindowZoneComboBox(List<String> zoneList) throws ReadOnlyException, ConversionException, UnsupportedOperationException {
                final ComboBox zone = new ComboBox("Zone");
                for (String zones : zoneList) {
                        zone.addItem(zones);
                }
                zone.setImmediate(true);
                zone.setNullSelectionAllowed(false);
                Collection<?> itemIds = zone.getItemIds();
                zone.setValue(itemIds.iterator().next());
                return zone;
        }

        private int addRaid(String zone, String comment, String date) {
                RaidDAO raidDao = new RaidDB();
                return raidDao.addNewRaid(zone, comment, date);
        }

        private class AddButtonClickListener implements ClickListener {

                private final ComboBox zone;
                private final TextField comment;
                private final TextField datum;

                public AddButtonClickListener(ComboBox zone, TextField comment, TextField datum) {
                        this.zone = zone;
                        this.comment = comment;
                        this.datum = datum;
                }

                @Override
                public void buttonClick(ClickEvent event) {
                        String rzone = zone.getValue().toString();
                        String rcomment = comment.getValue().toString();
                        String rdate = datum.getValue().toString();
                        int success = addRaid(rzone, rcomment, rdate);
                        addComponent(new Label("Update :" + success));
                        notifyListeners();
                }
        }
}
