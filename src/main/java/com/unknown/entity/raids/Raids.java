/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unknown.entity.raids;

/**
 *
 * @author alde
 */
public class Raids {

    private final String raidname;
    private final String comment;
    private final String date;
    private final int id;

    public Raids(String raidname, String comment, String date, int id) {
            this.raidname = raidname;
            this.comment = comment;
            this.date = date;
            this.id = id;
    }

    public String getName() {
        return raidname;
    }
    public String getComment() {
        return comment;
    }
    public String getDate() {
        return date;
    }
    public int getID() {
        return id;
    }
}
