/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity.raids;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author alde
 */
public class Raid {

	private final String raidname;
	private final String comment;
	private final String date;
	private final int id;
	private final List<RaidItem> raidItems = new ArrayList<RaidItem>();
        private final List<RaidChar> raidChars = new ArrayList<RaidChar>();

	public Raid(String raidname, String comment, String date, int id) {
		this.raidname = raidname;
		this.comment = comment;
		this.date = date;
		this.id = id;
	}

	public String getName() {
		return raidname;
	}

	public String getComment() {
		return comment;
	}

	public String getDate() {
		return date;
	}

	public int getID() {
		return id;
	}

	public void addRaidItems(Collection<RaidItem> items){
		raidItems.addAll(items);
	}

	public ImmutableList<RaidItem> getRaidItems() {
		return ImmutableList.copyOf(raidItems);
	}

        public void addRaidChars(Collection<RaidChar> chars) {
                raidChars.addAll(chars);
        }

        public ImmutableList<RaidChar> getRaidChars() {
                return ImmutableList.copyOf(raidChars);
        }
}
