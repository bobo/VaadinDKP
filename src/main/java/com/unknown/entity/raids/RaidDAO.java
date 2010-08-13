/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity.raids;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author alde
 */
public interface RaidDAO {

        public List<Raid> getRaids();

        public List<String> getRaidZoneList();

        public int addNewRaid(String zone, String comment, String date);

        public int doRaidUpdate(Raid raid, String raidzoneName, String raidcomment, String raiddate) throws SQLException;

        public int doUpdateReward(RaidReward reward, List<String> newAttendants, int newShares, String newComment) throws SQLException;

        public List<String> getBossesForRaid(Raid raid) throws SQLException;

        public void addLootToRaid(Raid raid, String boss, String name, String loot, boolean heroic, double price) throws SQLException;

        public int removeReward(RaidReward reward) throws SQLException;

        public int addReward(String comment, Integer shares, List<String> attendantlist, Raid raid) throws SQLException;

        public int removeLootFromRaid(RaidItem item) throws SQLException;
}
