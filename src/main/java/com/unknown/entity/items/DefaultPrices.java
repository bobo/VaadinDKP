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

	private final ItemDAO dAO;

	public DefaultPrices(ItemDAO dAO) {
		this.dAO = dAO;
	}
	
    public List<ItemPrices> getPrices() throws SQLException {
           return dAO.getDefaultPrices();
    }
}
