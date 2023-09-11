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
     * Set field of view for the enemy.
     */
    private static final int FIELD_OF_VIEW = 5;
    /**
     * Current mode of the enemy.
     */
    private EnemyMode currentMode;
    /**
     * The path that the enemy is following.
     */
    private List<Node> path;
    /**
     * The pathfinding algorithm used by the enemy.
     */
    private final AStarAlgorithm pathFinder;

    /**
     * Enemy constructor.
     *
     * @param x  The initial X position
     * @param y  The initial Y position
     * @param hp The initial health
     * @author Alex Boxall
     * @author Jake Tammaro
     */
    Enemy(int x, int y, int hp) {
        super(x, y, hp);
        // Initialize enemy in roam mode
        this.currentMode = EnemyMode.RoamMode;
        // Initialize pathfinder
        this.pathFinder = new AStarAlgorithm(GameState.getInstance().board.getTiles());
        // Generate new random location for enemy to move
        Point target_coordinate = getNewRandomLocation(LOCATION_DISTANCE);
        setTargetLocation(target_coordinate);
    }

    /**
     * Called when it is this enemy's turn to move. It should run the pathfinding algorithm,
     * and then move in a valid direction based on this.
     * @author Jake Tammaro
     * @author Alex Boxall
     */
    void act() {
        Player player = GameState.getInstance().getPlayer();
        // Check if the player is within the enemy's field of view and has line of sight.
        if (isWithinFieldOfView(player.getX(), player.getY(), FIELD_OF_VIEW) && hasLineOfSight(player)) {
            currentMode = EnemyMode.AttackMode;
        } else {
            currentMode = EnemyMode.RoamMode;
        }
        // If the enemy is in roam mode, move towards the target location.
        if (currentMode == EnemyMode.RoamMode) {
            // If the enemy is at the target location, generate a new random location.
            if (targetX == x && targetY == y) {
                Point target_coordinate = getNewRandomLocation(LOCATION_DISTANCE);
                setTargetLocation(target_coordinate);
                // Generate path to target location
                path = pathFinder.findPath(x, y, targetX, targetY);
            }
            moveToNextTileInPath();
        }
        else {
            // If the enemy is in attack mode, move towards the player.
            path = pathFinder.findPath(x, y, player.getX(), player.getY());
            moveToNextTileInPath();
        }

    }

    /**
     * Method to generate a random location for enemy to move to.
     * @param initialDistance The initial distance to check for a valid location
     * @return A random valid location for the enemy to move to.
     * @author Jake Tammaro
     */

    public Point getNewRandomLocation(int initialDistance) {
        List<Point> locations = getPossibleLocations(initialDistance);
        // If no valid locations are found, decrease the distance and try again.
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
     * Method to generate all possible locations that the enemy can move to, given a distance.
     * @param distance The Manhattan distance from the enemy to the possible locations
     * @return A list of all possible locations
     * @author Jake Tammaro
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
     * Sets the target location for the enemy to move to.
     * @param target The target location
     * @author Jake Tammaro
     */
    private void setTargetLocation(Point target) {
        this.targetX = target.x;
        this.targetY = target.y;
    }

    /**
     * Calculates the Manhattan distance between two points.
     * @author Jake Tammaro
     * @param x1 The x coordinate of the first point
     * @param y1 The y coordinate of the first point
     * @param x2 The x coordinate of the second point
     * @param y2 The y coordinate of the second point
     * @return The Manhattan distance between the two points
     */

    public static int manhattanDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    /**
     * Moves the enemy to the next tile in their path.
     * @author Jake Tammaro
     */
    void moveToNextTileInPath() {
        if (path != null && !path.isEmpty()) {
            Node nextTile = path.remove(0);
            Direction dir = getDirectionToNextTile(nextTile);
            if (dir != null) {
                moveInDirection(dir);
            }
        }
    }

    /**
     * Gets the direction that the enemy should move in to get to the next tile in their path.
     * @param nextTile The next tile in the enemy's path
     * @return The direction that the enemy should move in
     * @author Jake Tammaro
     */
    Direction getDirectionToNextTile(Node nextTile) {
        if (nextTile.x > x) return Direction.RIGHT;
        if (nextTile.x < x) return Direction.LEFT;
        if (nextTile.y > y) return Direction.DOWN;
        if (nextTile.y < y) return Direction.UP;
        return null;  // This should never happen if the nodes are neighbors
    }

    /**
     * Creates a line of sight from the enemy to the player.
     * @param x0 The x coordinate of the enemy
     * @param y0 The y coordinate of the enemy
     * @param x1 The x coordinate of the player
     * @param y1 The y coordinate of the player
     * @return A list of points that make up the line of sight
     * @author Jake Tammaro
     */

    public List<Point> bresenhamLine(int x0, int y0, int x1, int y1) {
        List<Point> line = new ArrayList<>();

        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);

        int sx = (x0 < x1) ? 1 : -1;
        int sy = (y0 < y1) ? 1 : -1;

        int err = dx - dy;

        while (true) {
            line.add(new Point(x0, y0));

            if (x0 == x1 && y0 == y1) {
                break;
            }

            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x0 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y0 += sy;
            }
        }

        return line;
    }

    /**
     * Checks if the enemy can see the player.
     * @param player The player
     * @return True if the enemy can see the player, false otherwise
     * @author Jake Tammaro
     */
    private Boolean hasLineOfSight(Player player) {
        Tile[][] map = GameState.getInstance().board.getTiles();
        // Create line from enemy to player
        List<Point> line = bresenhamLine(x, y, player.getX(), player.getY());
        // If any of the tiles in the line are not empty, the enemy cannot see the player.
        for (Point p : line) {
            if (!GameState.getInstance().board.isEmptyTile(map[p.x][p.y])) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if the enemy is within the player's field of view.
     * @param targetX The x coordinate of the player
     * @param targetY The y coordinate of the player
     * @param distance The distance from the player that the enemy should be able to see
     * @return True if the enemy is within the player's field of view, false otherwise
     * @author Jake Tammaro
     */
    boolean isWithinFieldOfView(int targetX, int targetY, int distance) {
        int d = Math.abs(targetX - x) + Math.abs(targetY - y);
        return d <= distance;
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
