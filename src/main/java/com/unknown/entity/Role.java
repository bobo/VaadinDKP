package com.unknown.entity;

import java.util.Arrays;
import java.util.List;

public enum Role {

    priest(Armor.cloth),
    rogue(Armor.leather),
    warrior(Armor.plate),
    paladin(Armor.plate),
    deathknight(Armor.plate),
    mage(Armor.cloth),
    warlock(Armor.cloth),
    hunter(Armor.mail),
    shaman(Armor.mail),
    druid(Armor.cloth, Armor.leather);
    private final List<Armor> armor;

    private Role(Armor... armor) {
	this.armor = Arrays.asList(armor);
    }
}