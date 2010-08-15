/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity.raids.windows;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.unknown.entity.dao.CharacterDAO;
import com.unknown.entity.dao.RaidDAO;
import com.unknown.entity.database.CharacterDB;
import com.unknown.entity.database.RaidDB;
import com.unknown.entity.character.*;
import com.unknown.entity.raids.Raid;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author alde
 */
class RaidRewardAddWindow extends Window {

	private Raid raid;
	private final TextField attendants = new TextField("Attendants");
	private final TextField shares = new TextField("Shares");
	private final TextField comment = new TextField("Comment");
	Button addButton = new Button("Add");
	RaidDAO raidDao = new RaidDB();
	CharacterDAO chardao = new CharacterDB();

	public RaidRewardAddWindow(Raid raid) {
		this.raid = raid;
		this.getContent().setSizeUndefined();
		this.addStyleName("opaque");
		this.setCaption("Add reward for raid " + raid.getComment() + " (id " + raid.getId() + ")");
		attendants.setRows(20);
		attendants.setImmediate(true);
		shares.setImmediate(true);
		comment.setImmediate(true);

	}

	public void printInfo() {
		VerticalLayout vert = new VerticalLayout();
		vert.addComponent(comment);
		vert.addComponent(shares);
		vert.addComponent(attendants);
		vert.addComponent(addButton);
		addComponent(vert);
		addButton.addListener(new AddRewardListener());
	}


	private void addReward(String comment, Integer shares, List<String> attendantlist, Raid raid) {
		try {
			List<String> invalidchars = findInvalidCharacters(attendantlist);
			if (invalidchars.isEmpty()) {
				raidDao.addReward(comment, shares, attendantlist, raid);
			} else {
				showInvalidUsers(invalidchars);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}
	private void showInvalidUsers(List<String> invalidchars) {
		addComponent(new Label("Invalid characters"));
		for (String s : invalidchars) {
			addComponent(new Label(s));
		}
	}

	private ImmutableList<String> findInvalidCharacters(List<String> attendantlist) {
		List<String> invalidUsers = new ArrayList<String>(attendantlist);
		invalidUsers.removeAll(chardao.getUserNames());
		return ImmutableList.copyOf(invalidUsers);
	}

	private class AddRewardListener implements ClickListener {

		@Override
		public void buttonClick(ClickEvent event) {
			final ImmutableList<String> attendantlist = splitCharsToArray(attendants.getValue().toString());
			addReward(comment.getValue().toString(), Integer.parseInt(shares.getValue().toString()), attendantlist, raid);
		}

		private ImmutableList<String> splitCharsToArray(String attendants) {
			String[] parts = attendants.split("\n");
			return ImmutableList.of(parts);
		}
	}
}
