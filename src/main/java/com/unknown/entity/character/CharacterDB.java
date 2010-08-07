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
                PreparedStatement ps = c.prepareStatement("SELECT * FROM rewards JOIN character_rewards JOIN characters WHERE character_rewards.reward_id=rewards.id AND characters.id=?");
                PreparedStatement ploot = c.prepareStatement("SELECT * FROM loots JOIN characters where loots.character_id=characters.id");
                int shares = 0;
                double dkp_earned = 0.0;
                double dkp_spent = 0.0;
                double dkp = 0.0;
                double loot_value = 0.0;
                double share_value = 0.0;
                ps.setInt(1, rs.getInt("characters.id"));
                ResultSet rss = ps.executeQuery();
                ResultSet rsloot = ploot.executeQuery();
                while (rsloot.next()) {
                        if (rsloot.getInt("loots.character_id") == rs.getInt("characters.id")) {
                                dkp_spent = dkp_spent + rsloot.getDouble("loots.price");
                        }
                        loot_value = loot_value + rsloot.getDouble("loots.price");
                }
                while (rss.next()) {
                        if (rs.getInt("characters.id") == rss.getInt("character_rewards.character_id")) {
                                shares = shares + rss.getInt("rewards.number_of_shares");
                        }
                }
                if (shares != 0) {
                        share_value = loot_value / shares;
                }
                dkp_earned = shares * share_value;
                dkp = dkp_earned - dkp_spent;
                Role role = Role.valueOf(rs.getString("character_classes.name").replace(" ", ""));
                User user = new User(rs.getInt("id"), rs.getString("characters.name"), role, rs.getBoolean("characters.active"), shares, dkp_earned, dkp_spent, dkp);
                user.addCharItems(getItemsForCharacter(rs.getInt("id")));
                users.add(user);

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
}
