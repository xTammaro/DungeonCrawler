package org.example.item;

public abstract class ConsumableItem extends Item{

    /**
     * @param name   the name of the Item
     * @param value  the value of the Item in a shop in gold
     * @param rarity how rare the item is
     *               Constructor for Item
     * @author Will Baird
     */
    public ConsumableItem(String name, int value, Rarity rarity) {
        super(name, value, rarity);
    }
}
