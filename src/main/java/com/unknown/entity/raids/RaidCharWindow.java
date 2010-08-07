/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity.raids;

import com.vaadin.data.Item;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import java.util.List;

/**
 *
 * @author alde
 */
public class RaidCharWindow extends Window {

	private final List<RaidChar> chars;

	public RaidCharWindow(List<RaidChar> chars) {

		this.chars = chars;

		center();
		setWidth("600px");
		setHeight("320px");
	}

	public void printInfo() {
		HorizontalLayout hzl = new HorizontalLayout();
		hzl.setSpacing(true);
		Table Attendants = charList();
		if (Attendants.size() > 0) {
			hzl.addComponent(Attendants);
		} else {
			hzl.addComponent(new Label("No members in this raid."));
		}
		addComponent(hzl);
	}

	private Table charList() {
		Table tbl = new Table();
		tbl.addContainerProperty("Name", String.class, "");
		tbl.addContainerProperty("Shares", Integer.class, "");
		tbl.setHeight(150);
		for (RaidChar rchar : chars) {
			Item addItem = tbl.addItem(rchar);
			addItem.getItemProperty("Name").setValue(rchar.getName());
			addItem.getItemProperty("Shares").setValue(rchar.getShares());

		}
		return tbl;
	}
}
