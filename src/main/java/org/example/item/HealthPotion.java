package org.example.item;

import org.example.GameState;

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

    /**
     * heals the player health number of hp
     * does not heal more than player max health
     */
    public void heal(){
        var player = GameState.getInstance().getPlayer();
        var hpToMax = player.getMaxHealth() - player.getHp();
        if (hpToMax < health){
            player.setHp(player.getMaxHealth());
        } else {
            player.setHp(player.getHp() + health);
        }

    }

}
