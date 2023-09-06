package org.example;

/**
 * Represents an enemy on the board.
 */
public class Enemy extends Actor {
    private int targetX;
    private int targetY;
    private static final int MOVE_DISTANCE = 5;
    private EnemyMode currentMode;
    /**
     * Enemy constructor.
     *
     * @author Alex Boxall
     *
     * @param x The initial X position
     * @param y The initial Y position
     * @param hp The initial health
     */
    Enemy(int x, int y, int hp) {
        super(x, y, hp);
    }

    /**
     * Called when it is this enemy's turn to move. It should run the pathfinding algorithm,
     * and then move in a valid direction based on this.
     *
     * @author Alex Boxall
     */
    void act() {
        // pick a direction and move
    }

    /**
     * Called when the enemy dies. Should remove the enemy from the GameState.
     *
     * @author Alex Boxall
     */
    @Override
    void onZeroHealth() {
        GameState.getInstance().enemies.remove(this);
    }
}
