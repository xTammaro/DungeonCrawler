package org.example;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
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
     * Set distance for how far away a random location is chosen.
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
        // Initialize enemy in roam mode
        this.currentMode = EnemyMode.RoamMode;
        // Generate new random location for enemy to move
        Point target_coordinate = getNewRandomLocation(LOCATION_DISTANCE);
        this.targetX = target_coordinate.x;
        this.targetY = target_coordinate.y;
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
     * @author Jake Tammaro
     * Method to generate a random location for enemy to move to.
     * @param initialDistance The initial distance to check for a valid location
     * @return A random valid location for the enemy to move to.
     */

    public Point getNewRandomLocation(int initialDistance) {
        List<Point> locations = getPossibleLocations(initialDistance);
        while (locations.isEmpty() && initialDistance > 0) {
            initialDistance--;
            locations = getPossibleLocations(initialDistance);
        }

        if (!locations.isEmpty()) {
            Random rand = new Random();
            return locations.get(rand.nextInt(locations.size()));
        }

        return null;  // Return null if no valid location is found (very unlikely but just in case)
    }

    /**
     * @author Jake Tammaro
     * Method to generate all possible locations that the enemy can move to, given a distance.
     * @param distance The Manhattan distance from the enemy to the possible locations
     * @return A list of all possible locations
     */
    private List<Point> getPossibleLocations(int distance) {
        List<Point> possibleLocations = new ArrayList<>();
        Board board = GameState.getInstance().board;
        Tile[][] tiles = board.getTiles();

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (manhattanDistance(getX(), getY(), i, j) == distance && board.isEmptyTile(tiles[i][j])) {
                    possibleLocations.add(new Point(i, j));
                }
            }
        }

        return possibleLocations;
    }

    /**
     * @author Jake Tammaro
     * Calculates the Manhattan distance between two points.
     * @param x1 The x coordinate of the first point
     * @param y1 The y coordinate of the first point
     * @param x2 The x coordinate of the second point
     * @param y2 The y coordinate of the second point
     * @return The Manhattan distance between the two points
     */

    private int manhattanDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }



    /**
     * Called when the enemy dies. Should remove the enemy from the GameState.
     *
     * @author Alex Boxall
     */
    @Override
    public void onZeroHealth() {
        GameState.getInstance().enemies.remove(this);
    }

    private int getY() {
        return y;
    }

    private int getX() {
        return x;
    }




}
