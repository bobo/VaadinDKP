/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity.raids;

import com.vaadin.data.Item;
import com.vaadin.data.Property.ConversionException;
import com.vaadin.data.Property.ReadOnlyException;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import java.util.List;

/**
 *
 * @author alde
 */
public class RewardAttendantsWindow extends Window {

	private final List<RaidChar> chars;

	public RewardAttendantsWindow(List<RaidChar> chars) {
		this.chars = chars;
                this.setCaption("Attendants");
		this.center();
                this.addStyleName("opaque");
                this.getContent().setSizeUndefined();
	}

	public void printInfo() {
		HorizontalLayout hzl = new HorizontalLayout();
		hzl.setSpacing(true);
		Table Attendants = charList();
                hzl.addComponent(getAttendants(Attendants));
		addComponent(hzl);
	}

	private Table charList() {
		Table tbl = new Table();
                tbl.addStyleName("small");
		RaidCharWindowCharListSetHeaders(tbl);
		tbl.setHeight("270px");
                tbl.setWidth("180px");
		for (RaidChar rchar : chars) {
			Item addItem = tbl.addItem(rchar);
			RaidCharWindowCharListAddRow(addItem, rchar);
		}
		return tbl;
	}

        private void RaidCharWindowCharListSetHeaders(Table tbl) throws UnsupportedOperationException {
                tbl.addContainerProperty("Name", String.class, "");
                tbl.addContainerProperty("Shares", Integer.class, "");
        }

        private void RaidCharWindowCharListAddRow(Item addItem, RaidChar rchar) throws ReadOnlyException, ConversionException {
                addItem.getItemProperty("Name").setValue(rchar.getName());
                addItem.getItemProperty("Shares").setValue(rchar.getShares());
        }

        private Component getAttendants(Table Attendants) {
                if (Attendants.size() > 0) {
			return Attendants;
		} else {
			return new Label("No members in this reward.");
		}
        }
}
