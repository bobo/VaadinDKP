/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import com.unknown.entity.DBConnection;
import com.unknown.entity.Slots;
import com.unknown.entity.Type;
import com.unknown.entity.items.ItemDAO;
import com.unknown.entity.items.ItemLooter;
import com.unknown.entity.items.ItemPrices;
import com.unknown.entity.items.Items;
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
                                String temp = rs.getString("type");
                                Type tempType;
                                if (temp.toString().equals("Hunter, Shaman, Warrior")) {
                                        tempType = Type.protector;
                                } else if (temp.toString().equals("Death Knight, Druid, Mage, Rogue")) {
                                        tempType = Type.vanquisher;
                                } else if (temp.toString().equals("Paladin, Priest, Warlock")) {
                                        tempType = Type.conqueror;
                                } else {
                                        tempType = Type.valueOf(temp);
                                }
                                Items tempitem = new Items(rs.getInt("id"), rs.getString("name"), rs.getInt("wowid_normal"), rs.getDouble("price_normal"), rs.getInt("wowid_heroic"), rs.getDouble("price_heroic"), rs.getString("slot"), tempType, rs.getBoolean("isLegendary"));
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

        @Override
        public int updateItem(Items item, String newname, Slots newslot, Type newtype, int newwowid, int newwowidhc, double newprice, double newpricehc, boolean legendary) {
                Connection c = null;
                int success = 0;
                try {
                        c = new DBConnection().getConnection();
                        PreparedStatement p = c.prepareStatement("UPDATE items SET name=? , wowid_normal=? , wowid_heroic=? , price_normal=? , price_heroic=? , slot=? , type=? , isLegendary=? WHERE id=?");
                        p.setString(1, newname);
                        p.setInt(2, newwowid);
                        p.setInt(3, newwowidhc);
                        p.setDouble(4, newprice);
                        p.setDouble(5, newpricehc);
                        p.setString(6, newslot.toString());
                        p.setString(7, newtype.toString());
                        p.setBoolean(8, legendary);
                        p.setInt(9, item.getID());

                        success = p.executeUpdate();

                } catch (SQLException e) {
                        e.printStackTrace();
                } finally {
                        if (c != null) {
                                try {
                                        c.close();
                                } catch (SQLException ex) {
                                        Logger.getLogger(ItemDB.class.getName()).log(Level.SEVERE, null, ex);
                                }
                        }
                }
                return success;
        }

        @Override
        public int addItem(String name, int wowid, int wowid_hc, double price, double price_hc, String slot, String type, boolean isLegendary) throws SQLException {
                Connection c = null;
                int result = 0;

                try {
                        c = new DBConnection().getConnection();
                        PreparedStatement ps = c.prepareStatement("INSERT INTO items (name, wowid_normal, wowid_heroic, price_normal, price_heroic, slot, type, isLegendary) VALUES(?,?,?,?,?,?,?,?)");
                        ps.setString(1, name);
                        ps.setInt(2, wowid);
                        ps.setInt(3, wowid_hc);
                        ps.setDouble(4, price);
                        ps.setDouble(5, price_hc);
                        ps.setString(6, slot);
                        ps.setString(7, type);
                        ps.setBoolean(8, isLegendary);

                        result = ps.executeUpdate();
                } catch (SQLException e) {
                        e.printStackTrace();
                } finally {
                        if (c != null) {
                                c.close();
                        }
                }
                return result;
        }

        @Override
        public Object getItemPrice(String itemname, boolean heroic) throws SQLException {
                Double price = 0.0;
                Connection c = null;
                try {
                        c = new DBConnection().getConnection();
                        PreparedStatement p = c.prepareStatement("SELECT * FROM items WHERE name=?");
                        p.setString(1, itemname);
                        ResultSet rs = p.executeQuery();
                        if (rs.next()) {
                                if (heroic) {
                                        price = rs.getDouble("price_heroic");
                                } else {
                                        price = rs.getDouble("price_normal");
                                }
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                } finally {
                        if (c != null) {
                                c.close();
                        }
                }
                return price;
        }

        @Override
        public int getItemId(Connection c, String loot) throws SQLException {
                PreparedStatement p = c.prepareStatement("SELECT * FROM items WHERE name=?");
                p.setString(1, loot);
                ResultSet rs = p.executeQuery();
                int itemid = 0;
                while (rs.next()) {
                        itemid = rs.getInt("id");
                }
                return itemid;
        }
}
