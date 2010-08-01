package com.unknown.entity;

import java.util.Arrays;
import java.util.List;

public enum Role {
    Druid(Armor.leather),
    Hunter(Armor.mail),
    Mage(Armor.cloth),
    Paladin(Armor.plate),
    Priest(Armor.cloth),
    Rogue(Armor.leather),
    Shaman(Armor.mail),
    Warlock(Armor.cloth),
    Warrior(Armor.plate),
    DeathKnight(Armor.plate);
    private final List<Armor> armor;

    private Role(Armor... armor) {
	this.armor = Arrays.asList(armor);
    }
}