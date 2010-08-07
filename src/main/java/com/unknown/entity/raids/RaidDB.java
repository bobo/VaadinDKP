/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity.raids;

import com.unknown.entity.DBConnection;
import com.unknown.entity.character.CharacterDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alde
 */
public class RaidDB implements RaidDAO {

    @Override
    public List<Raid> getRaids() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CharacterDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        Connection c = null;
        List<Raid> raids = new ArrayList<Raid>();
        try {
            c = new DBConnection().getConnection();
            PreparedStatement p = c.prepareStatement("SELECT * FROM raids JOIN zones ON raids.zone_id=zones.id");
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                final Raid raid = new Raid(rs.getString("zones.name"), rs.getString("raids.comment"), rs.getString("raids.date"), rs.getInt("raids.id"));
				System.out.println("getting stuff for raid: "+raid.getID());
				raid.addRaidItems(getItemsForRaid(raid.getID()));
                raid.addRaidChars(getCharsForRaid(raid.getID()));
                raid.addRaidRewards(getRewardsForRaid(raid.getID()));
                raids.add(raid);
            }
        } catch (SQLException e) {
        }
        return raids;
    }

    public List<RaidItem> getItemsForRaid(int raidId) {

        Connection c = null;
        List<RaidItem> raidItems = new ArrayList<RaidItem>();
        try {
            c = new DBConnection().getConnection();
            PreparedStatement p = c.prepareStatement("SELECT * FROM loots JOIN raids JOIN characters JOIN items WHERE loots.raid_id=raids.id AND character_id=characters.id AND loots.item_id=items.id AND raids.id=?");
            p.setInt(1, raidId);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                RaidItem item = new RaidItem();
                item.setId(new Integer(rs.getInt("raids.id")));
                item.setLooter(rs.getString("characters.name"));
                item.setName(rs.getString("items.name"));
                item.setPrice(rs.getDouble("loots.price"));
                item.setHeroic(rs.getBoolean("loots.heroic"));
                raidItems.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return raidItems;
    }

    public List<RaidChar> getCharsForRaid(int raidId) {
        Connection c = null;
        List<RaidChar> raidChars = new ArrayList<RaidChar>();
        try {
            c = new DBConnection().getConnection();
            PreparedStatement p = c.prepareStatement("SELECT * FROM rewards JOIN character_rewards JOIN characters ON rewards.raid_id=? AND rewards.id=character_rewards.reward_id AND character_rewards.character_id=characters.id");
            p.setInt(1, raidId);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                RaidChar rchar = new RaidChar();
                rchar.setId(rs.getInt("rewards.id"));
                rchar.setName(rs.getString("characters.name"));
                rchar.setShares(rs.getInt("rewards.number_of_shares"));
                rchar.setComment(rs.getString("rewards.comment"));
                rchar.setRaidId(rs.getInt("rewards.raid_id"));
                raidChars.add(rchar);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return raidChars;
    }

    @Override
    public List<String> getRaidZoneList() {
        List<String> zones = new ArrayList<String>();
        Connection c = null;
        try {
            c = new DBConnection().getConnection();
            PreparedStatement ps = c.prepareStatement("INSERT INTO raids (zone_id, date, comment) VALUES(?,?,?)");
            PreparedStatement pzone = c.prepareStatement("SELECT * FROM zones");
            ResultSet rzone = pzone.executeQuery();
            while (rzone.next()) {
                zones.add(rzone.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CharacterDB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return zones;
    }

    @Override
    public int addNewRaid(String zone, String comment, String date) {

        Connection c = null;
        int result = 0;
        int zoneId = 0;

        try {
            c = new DBConnection().getConnection();
            PreparedStatement ps = c.prepareStatement("INSERT INTO raids (zone_id, date, comment) VALUES(?,?,?)");
            PreparedStatement pzone = c.prepareStatement("SELECT * FROM zones WHERE name=?");
            pzone.setString(1, zone);
            ResultSet rzone = pzone.executeQuery();
            while (rzone.next()) {
                zoneId = rzone.getInt("id");
            }
            ps.setInt(1, zoneId);
            ps.setString(2, date);
            ps.setString(3, comment);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CharacterDB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return result;
    }

    private Collection<RaidReward> getRewardsForRaid(int raidId) {
        System.out.println("getting rewards for raid: "+raidId);
		Connection c = null;
        List<RaidReward> raidRewards = new ArrayList<RaidReward>();
        try {
            c = new DBConnection().getConnection();
            PreparedStatement p = c.prepareStatement("SELECT * FROM rewards WHERE rewards.raid_id=?");
            p.setInt(1, raidId);
	            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                RaidReward rrewards = new RaidReward();
                rrewards.setId(rs.getInt("rewards.id"));
                rrewards.setComment(rs.getString("rewards.comment"));
                rrewards.setShares(rs.getInt("rewards.number_of_shares"));
                raidRewards.add(rrewards);
				System.out.println("rreward"+rrewards.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return raidRewards;

    }
}
