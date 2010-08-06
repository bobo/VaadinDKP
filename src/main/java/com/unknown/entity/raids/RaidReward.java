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
public class RaidReward {
	private int id;
	private String comment;
	private String name;
        private int shares;
        private final List<RaidChar> raidChars = new ArrayList<RaidChar>();
        private int raidId;

	public void setId(Integer integer) {
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

        public int getShares() {
                return shares;
        }

        public void setShares(int shares) {
                this.shares = shares;
        }

        public void addRewardChars(Collection<RaidChar> chars) {
                raidChars.addAll(chars);
        }

        public ImmutableList<RaidChar> getRewardChars() {
                return ImmutableList.copyOf(raidChars);
        }

    public int getRaidId() {
                return raidId;
    }
    public void setRaidId(int raidId) {
            this.raidId = raidId;
    }

    }