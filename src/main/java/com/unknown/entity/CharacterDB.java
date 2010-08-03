/*
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import java.sql.Connection;
import java.sql.DriverManager;
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

    private Connection connect() throws SQLException {
        String userName = "root", userPassword = "piccolo", databaseURL = "jdbc:mysql://unknown-entity.com:3306/dkp";
        Connection conn = null;
        conn = DriverManager.getConnection(databaseURL, userName, userPassword);
        return conn;
    }

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
            c = connect();
            PreparedStatement p = c.prepareStatement("SELECT * FROM characters JOIN character_classes ON characters.character_class_id=character_classes.id");
            PreparedStatement ps = c.prepareStatement("SELECT * FROM rewards JOIN character_rewards JOIN characters WHERE character_rewards.reward_id=rewards.id AND characters.id=?");
            PreparedStatement ploot = c.prepareStatement("SELECT * FROM loots JOIN characters where loots.character_id=characters.id");

            ResultSet rs = p.executeQuery();

            while (rs.next()) {
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
                users.add(new User(rs.getInt("id"), rs.getString("characters.name"), role, rs.getBoolean("characters.active"), shares, dkp_earned, dkp_spent, dkp));
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
}
