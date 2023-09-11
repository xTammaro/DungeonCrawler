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
     * @param x The initial X position.
     * @param y The initial Y position.
     * @param hp The initial health.
     */
    Player(int x, int y, int hp) {
        super(x, y, hp);
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


    int getX() {
    	return x;
    }

    int getY() {
    	return y;
    }

}
