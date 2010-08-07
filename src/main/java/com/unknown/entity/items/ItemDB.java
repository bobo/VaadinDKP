/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity.items;

import com.unknown.entity.character.CharacterDB;
import com.unknown.entity.DBConnection;
import com.unknown.entity.Slots;
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
 * @author bobo
 */
public class ItemDB implements ItemDAO {

        @Override
        public List<Items> getItems() {
                try {
                        Class.forName("com.mysql.jdbc.Driver");
                } catch (ClassNotFoundException ex) {
                        Logger.getLogger(CharacterDB.class.getName()).log(Level.SEVERE, null, ex);
                }
                Connection c = null;
                List<Items> items = new ArrayList<Items>();
                try {
                        c = new DBConnection().getConnection();
                        PreparedStatement p = c.prepareStatement("SELECT * FROM items");
                        ResultSet rs = p.executeQuery();
                        while (rs.next()) {
                                Items tempitem = new Items(rs.getInt("id"), rs.getString("name"), rs.getInt("wowid_normal"), rs.getDouble("price_normal"), rs.getInt("wowid_heroic"), rs.getDouble("price_heroic"), rs.getString("slot"), rs.getString("type"), rs.getBoolean("isLegendary"));
                                tempitem.addItemList(getLootersFormItems(rs.getInt("id")));
                                items.add(tempitem);
                        }
                } catch (SQLException e) {
                }
                return items;
        }

        private Collection<ItemLooter> getLootersFormItems(int itemId) {
                List<ItemLooter> looters = new ArrayList<ItemLooter>();
                Connection c = null;
                try {
                        c = new DBConnection().getConnection();
                        PreparedStatement p = c.prepareStatement("SELECT * FROM loots JOIN characters JOIN items JOIN raids WHERE loots.character_id=characters.id AND loots.item_id=? AND items.id=loots.item_id AND raids.id=loots.raid_id");
                        p.setInt(1, itemId);
                        ResultSet rs = p.executeQuery();
                        while (rs.next()) {
                                ItemLooter templooter = new ItemLooter();
                                templooter.setName(rs.getString("characters.name"));
                                templooter.setPrice(rs.getDouble("loots.price"));
                                templooter.setRaid(rs.getString("raids.comment"));
                                templooter.setDate(rs.getString("raids.date"));
                                templooter.setId(rs.getInt("loots.id"));
                                looters.add(templooter);
                        }
                } catch (SQLException e) {
                }

                return looters;
        }

        @Override
        public List<ItemPrices> getDefaultPrices() throws SQLException {
                Connection c = null;
                List<ItemPrices> prices = new ArrayList<ItemPrices>();
                try {
                        c = new DBConnection().getConnection();
                        PreparedStatement p = c.prepareStatement("SELECT * FROM default_prices");

                        ResultSet rs = p.executeQuery();

                        while (rs.next()) {
                                Slots slot = Slots.valueOf(rs.getString("slot"));
                                prices.add(new ItemPrices(slot, rs.getDouble("price_normal"), rs.getDouble("price_heroic")));
                        }

                } catch (SQLException e) {
                        e.printStackTrace();
                } finally {
                        if (c != null) {
                                c.close();
                        }
                }
                return prices;
        }
}
