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
    public Player(int x, int y, int hp) {
        super(x, y, hp);
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
     * Calculates the attack damage.
     * This should use hte player's weapon
     * @return Attack damage
     * @author Tal Shy-Tielen
     */
    public int attackDamage() {
        return 50;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
