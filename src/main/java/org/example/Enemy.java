package org.example;

/**
 * Represents an enemy on the board.
 */
public class Enemy extends Actor {
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
     * @author Tal Shy-Tielen
     */
    @Override
    void onZeroHealth() {
        GameState.getInstance().enemies.remove(this);

        // This makes it so the last enemy will drop a key
        if (GameState.getInstance().enemies.size() == 0) {
            GameState.getInstance().board.tiles[y][x] = Tile.EmptyWithKey;
        }
    }
}
