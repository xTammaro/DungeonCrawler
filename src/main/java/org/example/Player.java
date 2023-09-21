package org.example;

/**
 * The playable character. Stores only their position and health, the rest (e.g. inventory) is stored
 * as part of the GameState.
 */
public class Player extends Actor {

    private int gold;
    /**
     * the Max amount of Health the player can have
     */
    private int maxHealth;
    /**
     * extra melee damage the player does
     */
    private int meleeBoost = 0;

    /**
     * extra ranged damage the player does
     */
    private int rangedBoost = 0;

    /**
     * @return the player maxHealth
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * @param maxHealth the players new maxHealth
     */

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    /**
     * @return extra melee damage the player does
     */
    public int getMeleeBoost() {
        return meleeBoost;
    }

    /**
     * @param meleeBoost the new amount of extra melee damage
     */
    public void setMeleeBoost(int meleeBoost) {
        this.meleeBoost = meleeBoost;
    }
    /**
     * @return extra ranged damage the player does
     */

    public int getRangedBoost() {
        return rangedBoost;
    }

    /**
     * @param rangedBoost the new amount of extra ranged damage
     */
    public void setRangedBoost(int rangedBoost) {
        this.rangedBoost = rangedBoost;
    }

    /**
     * The player constructor.
     *
     * @author Alex Boxall
     *
     * Initial gold value is set to zero.
     * @param x The initial X position.
     * @param y The initial Y position.
     * @param hp The initial health.
     */
    public Player(int x, int y, int hp) {
        super(x, y, hp);
        this.gold = 0;
        this.maxHealth = hp;
    }

    /**
     * This gets called when the Player dies, i.e. on a game over.
     *
     * @author Alex Boxall
     */
    @Override
    void onZeroHealth() {
        GameState.getInstance().setGameMode(GameState.GameMode.GameOverScreen);
    }


    /**
     * Called when the actor uses a gun attack.
     *
     * @author Alex Boxall
     */
    void useGun() {
        // Moved to player because Enemy dont have range weapons
        //TODO: get players range damage by calling rangeAttackDamage
    }

    /**
     * Calculates the attack damage.
     * This should use the player's melee weapon
     * @return MeleeWeapon Attack damage
     * @author Tal Shy-Tielen
     * @author Will Baird
     */
    public int meleeAttackDamage() {
        return GameState.getInstance().getCurrentMeleeWeapon().getDamage() + meleeBoost;
    }

    /**
     * Calculates the attack damage.
     * This should use the player's ranged weapon
     * @return RangedWeapon Attack damage
     * @author Will Baird
     */
    public  int rangeAttackDamage(){
        return GameState.getInstance().getCurrentRangedWeapon().getDamage() + rangedBoost;
    }

    /**
     * @author Tal Shy-Tielen
     * Function should be called whenever the player receives gold.
     * @param x the amount of gold recieved.
     */
    public void receiveGold(int x) {
        this.gold += x;
    }

    /**
     * @author Tal Shy-Tielen
     * Checks whether the player can buy an item.
     * @param x price of item.
     * @return true if the player can afford it, false otherwise.
     */
    public boolean canBuy(int x) {
        return this.gold >= x;
    }

    /**
     * @author Tal Shy-Tielen
     * Buys an item at a given price.
     * @param x price of the item.
     */
    public void buy(int x) {
        this.gold -= x;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
