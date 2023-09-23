package org.example.item;

import org.example.GameState;

public class StatBoostItem extends Item{


    private final PlayerStatType playerStatType;

    private final int statBoostAmount;


    /**
     * @param name   the name of the Item
     * @param value  the value of the Item in a shop in gold
     * @param rarity how rare the item is
     * @param playerStatType the player stat to be increased
     * @param statBoostAmount the amount the stat is increased
     *               Constructor for Item
     * @author Will Baird
     */
    public StatBoostItem(String name, int value, Rarity rarity, PlayerStatType playerStatType, int statBoostAmount) {
        super(name, value, rarity);
        this.playerStatType = playerStatType;
        this.statBoostAmount = statBoostAmount;
    }

    /**
     * @author Will Baird
     * makes and returns a new StatBoostItem with the same attributes
     * @return the new StatBoostItem
     */
    @Override
    public Item makeClone() {
        return new StatBoostItem(getName(), getValue(), getRarity(), getPlayerStatType(), getStatBoostAmount());
    }

    /**
     * apples a permanent boost to the player stat
     * based on playerStatType
     */
    public void applyStat(){
        var player = GameState.getInstance().getPlayer();
        switch (playerStatType){
            case HEALTH -> {
                player.setMaxHealth(player.getMaxHealth() + statBoostAmount);
            }
            case MELEE_DAMAGE -> {
                player.setMeleeBoost(player.getMeleeBoost() + statBoostAmount);
            }
            case RANGE_DAMAGE -> {
                player.setRangedBoost(player.getRangedBoost() + statBoostAmount);
            }
        }
    }

    public PlayerStatType getPlayerStatType() {
        return playerStatType;
    }

    public int getStatBoostAmount() {
        return statBoostAmount;
    }
}
