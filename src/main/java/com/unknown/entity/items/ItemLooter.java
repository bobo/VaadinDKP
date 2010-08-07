/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unknown.entity.items;

/**
 *
 * @author alde
 */
public class ItemLooter {
        private int id;
        private String name;
        private double price;
        private String raid;
        private String date;

        public void setName(String name) {
                this.name = name;
        }
        public String getName() {
                return name;
        }
        public void setPrice(double price) {
                this.price = price;
        }
        public double getPrice() {
                return price;
        }
        public void setRaid(String raid) {
                this.raid = raid;
        }
        public String getRaid() {
                return raid;
        }
        public void setDate(String date) {
                this.date = date;
        }
        public String getDate() {
                return date;
        }
        public int getId() {
                return id;
        }
        public void setId(int id) {
                this.id = id;
        }
}
