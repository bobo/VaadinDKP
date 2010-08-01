/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity;

/**
 *
 * @author bobo
 */

public class User {

    private final String username;
    private final Role role;
    private int shares;
    private double  dkp;
    private boolean active = true;

    public User(String username, Role role, boolean active) {
	this.username = username;
	this.role = role;
	this.shares = 0;
	this.dkp = dkp;
        this.active = active;
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

    public Double getDKP() {
	return dkp;
    }
}
