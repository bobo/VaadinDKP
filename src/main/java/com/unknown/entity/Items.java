/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unknown.entity;

/**
 *
 * @author alde
 */
public class Items {

    private final String itemname;
    private final int wowid;
    private double price;
    private String slot;
    private String type;
    private boolean heroic = false;

    public Items(String itemname, int wowid, double price, String slot, String type, boolean heroic) {
            this.itemname = itemname;
            this.wowid = wowid;
            this.price = price;
            this.slot = slot;
            this.type = type;
            this.heroic = heroic;
    }

    public String getName() {
        return itemname;
    }
    public int getWowID() {
        return wowid;
    }
    public double getPrice() {
        return price;
    }
    public String getSlot() {
        return slot;
    }
    public String getType() {
        return type;
    }
    public boolean isHeroic() {
         return heroic;
    }
}
