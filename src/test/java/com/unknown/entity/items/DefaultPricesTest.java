/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unknown.entity.items;

import java.util.Collections;
import com.unknown.entity.Slots;
import com.unknown.entity.Type;
import java.sql.SQLException;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author bobo
 */
public class DefaultPricesTest {

    public DefaultPricesTest() {
    }

	@Test
	public void testGetPrices() throws Exception {
	DefaultPrices prices = new DefaultPrices(new ItemDAOImpl1());
	assertEquals(Collections.emptyList(),prices.getPrices());
	}

	static class ItemDAOImpl1 implements ItemDAO {

		public ItemDAOImpl1() {
		}

		@Override
		public List<Items> getItems() {
			throw new UnsupportedOperationException("Not supported yet.");
		
		}

		@Override
		public List<ItemPrices> getDefaultPrices() throws SQLException {
			return Collections.emptyList();
		}

		@Override
		public int updateItem(Items item, String newname, Slots newslot, Type newtype, int newwowid, int newwowidhc, double newprice, double newpricehc, boolean legendary) {
			throw new UnsupportedOperationException("Not supported yet.");
		}

		@Override
		public int addItem(String name, int wowid, int wowid_hc, double price, double price_hc, String slot, String type, boolean isLegendary) throws SQLException {
			throw new UnsupportedOperationException("Not supported yet.");
		}

		@Override
		public Object getItemPrice(String itemname, boolean heroic) throws SQLException {
			throw new UnsupportedOperationException("Not supported yet.");
		}
	}

}