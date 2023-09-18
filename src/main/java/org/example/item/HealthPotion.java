package org.example.item;

public class HealthPotion extends ConsumableItem{

    private final int health;
    /**
     * @param name  the name of the Item
     * @param value the value of the Item in a shop in gold
     * @param rarity how rare the item is
     * @param health the amount of health this potion can heal
     * @author Will Baird
     * Constructer for PotionItem
     */
    public HealthPotion(String name, int value, Rarity rarity, int health) {
        super(name, value, rarity);
        this.health = health;
    }

    /**
     * @author Will Baird
     * makes and returns a new PotionItem with the same attributes
     * @return the new PotionItem
     */
    @Override
    public Item makeClone() {
        return new HealthPotion(this.getName(),this.getValue(),this.getRarity(),this.getHealth());
    }
    /**
     * @author Will Baird
     * @return the amount of health this potion can heal
     */
    public int getHealth() {
        return health;
    }
}
