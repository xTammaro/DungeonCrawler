package org.example.item;

public abstract class WeaponItem extends Item{


    /**
     * damage the amount of damage done to an enemy evey attack
     */
    private final int damage;
    /**
     * @param name  the name of the Item
     * @param value the value of the Item in a shop in gold
     * @param rarity how rare the item is
     * @param damage the amount of damage the weapon does
     * @author Will Baird
     * Constructer for WeaponItem
     */
    public WeaponItem(String name, int value,Rarity rarity, int damage) {
        super(name, value, rarity);
        this.damage = damage;
    }

    /**
     * @return the damage this Weapon does
     */
    public int getDamage() {
        return damage;
    }
}

