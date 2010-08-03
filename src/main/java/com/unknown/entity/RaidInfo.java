/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unknown.entity;

import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

/**
 *
 * @author alde
 */
public class RaidInfo extends Window {

    private final Raids raid;

    public RaidInfo(Raids raid) {
        this.raid = raid;
    }

    public void printInfo() {
        addComponent(new Label("Raid information"));

        addComponent(new Label("Zone: " + raid.getName()));
        addComponent(new Label("Comment: " + raid.getComment()));
        addComponent(new Label("Date: " + raid.getDate()));
    }
}
