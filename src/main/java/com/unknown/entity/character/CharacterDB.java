/*
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity.character;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.unknown.entity.DBConnection;
import com.unknown.entity.Role;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alde
 */
public class CharacterDB implements CharacterDAO {

        @Override
        public List<User> getUsers() {
                try {
                        Class.forName("com.mysql.jdbc.Driver");
                } catch (ClassNotFoundException ex) {
                        Logger.getLogger(CharacterDB.class.getName()).log(Level.SEVERE, null, ex);
                }

                Connection c = null;
                List<User> users = new ArrayList<User>();
                try {

                        c = new DBConnection().getConnection();
                        PreparedStatement p = c.prepareStatement("SELECT * FROM characters JOIN character_classes ON characters.character_class_id=character_classes.id");


                        ResultSet rs = p.executeQuery();

                        while (rs.next()) {
                                DoSQLMagicForCharacters(c, rs, users);
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

                return users;
        }

        private User CreateCharacter(ResultSet rs, int shares, double dkp_earned, double dkp_spent, double dkp) throws SQLException {
                Role role = Role.valueOf(rs.getString("character_classes.name").replace(" ", ""));
                User user = new User(rs.getInt("id"), rs.getString("characters.name"), role, rs.getBoolean("characters.active"), shares, dkp_earned, dkp_spent, dkp);
                user.addCharItems(getItemsForCharacter(rs.getInt("id")));
                return user;
        }

        @Override
        public int GetCharacterClassId(Connection c, String charclass) throws SQLException {
                PreparedStatement pclass = c.prepareStatement("SELECT * FROM character_classes WHERE name=?");
                pclass.setString(1, fixRole(charclass));
                ResultSet rclass = pclass.executeQuery();
                int classid = 0;
                while (rclass.next()) {
                        classid = rclass.getInt("id");
                }
                return classid;
        }

        @Override
        public int GetCharacterId(Connection c, String charname) throws SQLException {
                PreparedStatement pclass = c.prepareStatement("SELECT * FROM characters WHERE name=?");
                pclass.setString(1, charname);
                ResultSet rclass = pclass.executeQuery();
                int charid = 0;
                while (rclass.next()) {
                        charid = rclass.getInt("id");
                }
                return charid;
        }

        private int GetSharesForCharacterByID(ResultSet rs, ResultSet rss, int shares) throws SQLException {
                if (rs.getInt("characters.id") == rss.getInt("character_rewards.character_id")) {
                        shares = shares + rss.getInt("rewards.number_of_shares");
                }
                return shares;
        }

        private double GetDkpSpentForCharacterByID(ResultSet rsloot, ResultSet rs, double dkp_spent) throws SQLException {
                if (rsloot.getInt("loots.character_id") == rs.getInt("characters.id")) {
                        dkp_spent = dkp_spent + rsloot.getDouble("loots.price");
                }
                return dkp_spent;
        }

        private List<CharacterItem> getItemsForCharacter(int charId) {
                Connection c = null;
                List<CharacterItem> itemlist = new ArrayList<CharacterItem>();
                try {
                        c = new DBConnection().getConnection();
                        PreparedStatement p = c.prepareStatement("SELECT * FROM loots JOIN items WHERE loots.character_id=? AND loots.item_id=items.id");
                        p.setInt(1, charId);
                        ResultSet rs = p.executeQuery();
                        while (rs.next()) {
                                CharacterItem charitem = new CharacterItem();
                                charitem.setId(rs.getInt("loots.id"));
                                charitem.setName(rs.getString("items.name"));
                                charitem.setPrice(rs.getDouble("loots.price"));
                                charitem.setHeroic(rs.getBoolean("loots.heroic"));
                                itemlist.add(charitem);
                        }
                } catch (SQLException e) {
                }
                return itemlist;
        }

        private void DoSQLMagicForCharacters(Connection c, ResultSet rs, List<User> users) throws SQLException {
                PreparedStatement ploot = c.prepareStatement("SELECT * FROM loots JOIN characters where loots.character_id=characters.id");
                int shares = 0;
                double dkp_earned = 0.0;
                double dkp_spent = 0.0;
                double dkp = 0.0;
                double loot_value = 0.0;
                double share_value = 0.0;
                int totalshares = 0;
                ResultSet rsloot = ploot.executeQuery();
                while (rsloot.next()) {
                        dkp_spent = GetDkpSpentForCharacterByID(rsloot, rs, dkp_spent);
                        loot_value = loot_value + rsloot.getDouble("loots.price");
                }
                PreparedStatement ps = c.prepareStatement("SELECT * FROM rewards JOIN character_rewards JOIN characters WHERE character_rewards.reward_id=rewards.id AND characters.id=?");
                ps.setInt(1, rs.getInt("characters.id"));

                ResultSet rss = ps.executeQuery();
                while (rss.next()) {
                        shares = GetSharesForCharacterByID(rs, rss, shares);
                        totalshares += rss.getInt("rewards.number_of_shares");
                }
                if (totalshares != 0) {
                        share_value = loot_value / totalshares;
                } else {
                        share_value = 0;
                }
                dkp_earned = shares * share_value;
                dkp_earned = roundTwoDecimals(dkp_earned);
                dkp_spent = roundTwoDecimals(dkp_spent);
                dkp = dkp_earned - dkp_spent;
                dkp = roundTwoDecimals(dkp);
                User user = CreateCharacter(rs, shares, dkp_earned, dkp_spent, dkp);
                users.add(user);

        }
        
        double roundTwoDecimals(double d) {
        	DecimalFormat twoDForm = new DecimalFormat("#.##");
		return Double.valueOf(twoDForm.format(d));
        }
        @Override
        public Collection<User> getUsersWithRole(final Role role) {
                return Collections2.filter(getUsers(), new HasRolePredicate(role));
        }

        private static class HasRolePredicate implements Predicate<User> {

                private final Role role;

                public HasRolePredicate(Role role) {
                        this.role = role;
                }

                @Override
                public boolean apply(User user) {
                        return user.getRole().equals(role);
                }
        }

        @Override
        public int addNewCharacter(String name, String role, Boolean isActive) throws SQLException {
                Connection c = null;
                int class_id = 0, update = 0;

                try {
                        c = new DBConnection().getConnection();
                        PreparedStatement ps = c.prepareStatement("INSERT INTO characters (name, character_class_id, active, user_id) VALUES(?,?,?,NULL)");
                        PreparedStatement pclass = c.prepareStatement("SELECT * FROM character_classes WHERE name=?");
                        pclass.setString(1, fixRole(role));
                        ResultSet rsclass = pclass.executeQuery();

                        while (rsclass.next()) {
                                class_id = rsclass.getInt("id");
                        }
                        ps.setString(1, name);
                        ps.setInt(2, class_id);
                        ps.setBoolean(3, isActive);
                        update = ps.executeUpdate();

                } catch (SQLException e) {
                        e.printStackTrace();
                } finally {
                        if (c != null) {
                                c.close();
                        }
                }
                return update;
        }

        private String fixRole(String role) {
                if (role.equals("DeathKnight")) {
                        return "Death Knight";
                } else {
                        return role;
                }
        }

        @Override
        public int updateCharacter(User user, String name, String charclass, boolean active) {
                Connection c = null;
                int success = 0;

                try {
                        c = new DBConnection().getConnection();
                        int classid = GetCharacterClassId(c, charclass);

                        PreparedStatement p = c.prepareStatement("UPDATE characters SET name=? , character_class_id=? , active=? , user_id=NULL WHERE id=?");
                        p.setString(1, name);
                        p.setInt(2, classid);
                        p.setBoolean(3, active);
                        p.setInt(4, user.getID());

                        success = p.executeUpdate();

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
                return success;
        }
}
