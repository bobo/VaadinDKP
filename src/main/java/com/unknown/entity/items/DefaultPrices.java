/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity.items;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author alde
 */
public class DefaultPrices {

    public List<ItemPrices> getPrices() throws SQLException {
           ItemDAO itemDao = new ItemDB();
           return itemDao.getDefaultPrices();
    }
}
