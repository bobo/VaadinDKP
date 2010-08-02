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
    private int wowid;
    private double price;
    private String slot;
    private String type;
    private int wowid_hc;
    private double price_hc;
    private boolean isLegendary = false;

    public Items(String itemname, int wowid, double price, int wowid_hc, double price_hc, String slot, String type, boolean isLegendary) {
            this.itemname = itemname;
            this.wowid = wowid;
            this.price = price;
            this.wowid_hc = wowid_hc;
            this.price_hc = price_hc;
            this.slot = slot;
            this.type = type;
            this.isLegendary = isLegendary;
    }

    public String getName() {
        return itemname;
    }
    public int getWowID() {
        return wowid;
    }
    public int getWowID_hc() {
        return wowid_hc;
    }
    public double getPrice_hc() {
        return price_hc;
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
    public boolean isLegendary() {
        return isLegendary;
    }
}
