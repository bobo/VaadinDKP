/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity.character;

import com.unknown.entity.Armor;
import com.unknown.entity.Role;

/**
 *
 * @author bobo
 */

public class User {

    private final int id;
    private final String username;
    private final Role role;
    private int shares;
    private double dkp_earned;
    private double dkp_spent;
    private double dkp;
    private boolean active = true;
    private Armor armor;

    public User(int id, String username, Role role, boolean active, int shares, double dkp_earned, double dkp_spent, double dkp) {
	this.id = id;
        this.username = username;
	this.role = role;
	this.shares = shares;
	this.dkp = dkp;
        this.dkp_earned = dkp_earned;
        this.dkp_spent = dkp_spent;
        this.active = active;
        this.armor = role.getArmor();
    }	
    
    public int getID() {
        return id;
    }

    public int getShares() {
	return shares;
    }
    public void setShares(int x) {
        shares = x;
    }

    public void inactivate() {
	active=false;
    }
    public void activate() {
	active=true;
    }

    public boolean isActive() {
	return active;
    }
    
    
    public Role getRole() {
	return role;
    }

    public String getUsername() {
	return username;
    }

    @Override
    public String toString() {
	return username;
    }

    public double getDKP() {
	return dkp;
    }

    public double getDKPSpent() {
        return dkp_spent;
    }

    public double getDKPEarned() {
        return dkp_earned;
    }

    public Armor getArmor() {
        return armor;
    }
}
