/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unknown.entity.raids;

/**
 *
 * @author alde
 */
public class RaidChar {
	private int id;
        private int raidid;
	private String name;
        private int shares;

        public int getRaidId() {
                return raidid;
        }

        public void setRaidId(int raidid) {
                this.raidid = raidid;
        }
		public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
}
