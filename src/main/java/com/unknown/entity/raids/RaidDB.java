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
				raid.addRaidItems(getItemsForRaid(raid.getID()));
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
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return raidItems;
	}
}
