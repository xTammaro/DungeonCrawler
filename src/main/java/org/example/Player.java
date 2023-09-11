package org.example;

/**
 * The playable character. Stores only their position and health, the rest (e.g. inventory) is stored
 * as part of the GameState.
 */
public class Player extends Actor {
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

    private int gold;

    Player(int x, int y, int hp) {
        super(x, y, hp);
        this.gold = 0;
    }

    /**
     * This gets called when the Player dies, i.e. on a game over.
     *
     * @author Alex Boxall
     */
    @Override
    void onZeroHealth() {
        System.out.printf("GAME OVER!\n");
    }


    /** Functions to edit the player's gold.
     * These functions should be called when the player buys or gains gold.
     */
    public boolean canBuy(int x) {
        return this.gold >= x;
    }
    public void buy(int x) {
        this.gold -= x;
    }
    public void editGold(int x) {
        this.gold += x;
    }
    public void setGold(int x) {
        this.gold = x;
    }
}