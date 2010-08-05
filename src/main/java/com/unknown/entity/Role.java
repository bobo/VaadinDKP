package com.unknown.entity;

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
    private final Armor armor;

    private Role(Armor armor) {
	this.armor = armor;
    }
    public Armor getArmor() {
        return this.armor;
    }
}