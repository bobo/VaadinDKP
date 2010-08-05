/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity;

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
public class DefaultPrices {

    public List<ItemPrices> getPrices() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CharacterDB.class.getName()).log(Level.SEVERE, null, ex);
        }

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
                try {
                    c.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CharacterDB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return prices;
    }
}
