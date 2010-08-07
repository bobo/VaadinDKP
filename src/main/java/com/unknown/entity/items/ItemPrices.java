/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unknown.entity.items;

import com.unknown.entity.Slots;

/**
 *
 * @author alde
 */
public class ItemPrices {

    private Slots slot;
    private double price;
    private double price_heroic;

    public ItemPrices(Slots slot, double price, double price_heroic) {
        this.slot = slot;
        this.price = price;
        this.price_heroic = price_heroic;
    }

    public double getPrice() {
        return price;
    }

    public double getPriceHeroic() {
        return price_heroic;
    }

    public String getSlotString() {
        return slot.toString();
    }

}
