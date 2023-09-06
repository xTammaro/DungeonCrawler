package org.example;

import java.util.Random;

/**
 * Represents an enemy on the board.
 */
public class Enemy extends Actor {
    /**
     * The target location that the enemy is moving towards.
     */
    private int targetX;
    private int targetY;

    /**
     * Set distance for how far the enemy randomly moves towards.
     */
    private static final int LOCATION_DISTANCE = 5;

    /**
     * Current mode of the enemy.
     */
    private EnemyMode currentMode;

    /**
     * Enemy constructor.
     *
     * @param x  The initial X position
     * @param y  The initial Y position
     * @param hp The initial health
     * @author Alex Boxall
     */
    Enemy(int x, int y, int hp) {
        super(x, y, hp);
        this.currentMode = EnemyMode.RoamMode;
        newRandomLocation();
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
     * Called when the enemy is in RoamMode. Should set the enemy towards the target location.
     *
     * @author Jake Tammaro
     */

    void newRandomLocation() {
        Random rand = new Random();
        int direction = rand.nextInt(8);  // 0 to 7 representing 8 possible directions

        switch (direction) {
            case 0:  // North
                targetX = getX();
                targetY = getY() - LOCATION_DISTANCE;
                break;
            case 1:  // South
                targetX = getX();
                targetY = getY() + LOCATION_DISTANCE;
                break;
            case 2:  // East
                targetX = getX() + LOCATION_DISTANCE;
                targetY = getY();
                break;
            case 3:  // West
                targetX = getX() - LOCATION_DISTANCE;
                targetY = getY();
                break;
            case 4:  // North-East
                targetX = getX() + LOCATION_DISTANCE;
                targetY = getY() - LOCATION_DISTANCE;
                break;
            case 5:  // North-West
                targetX = getX() - LOCATION_DISTANCE;
                targetY = getY() - LOCATION_DISTANCE;
                break;
            case 6:  // South-East
                targetX = getX() + LOCATION_DISTANCE;
                targetY = getY() + LOCATION_DISTANCE;
                break;
            case 7:  // South-West
                targetX = getX() - LOCATION_DISTANCE;
                targetY = getY() + LOCATION_DISTANCE;
                break;
        }
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
            GameState.getInstance().board.setTile(x, y, Tile.EmptyWithKey);
        }
    }


    private int getY() {
        return y;
    }

    private int getX() {
        return x;
    }
}