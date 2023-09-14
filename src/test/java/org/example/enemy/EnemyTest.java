package org.example.enemy;
import org.example.*;
import org.junit.Before;
import org.junit.Test;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class EnemyTest {
    Tile[][] testMap = {
            {Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty},
            {Tile.Empty, Tile.Wall,  Tile.Wall,  Tile.Wall,  Tile.Empty},
            {Tile.Empty, Tile.Empty, Tile.Empty, Tile.Wall,  Tile.Empty},
            {Tile.Empty, Tile.Wall,  Tile.Empty, Tile.Empty, Tile.Empty},
            {Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty}
    };

    Player player;
    Player player2 = new Player(2, 0, 100);
    Board board;

    Enemy enemy;

    @Before
    public void setUp() {
        player = new Player(2, 4, 100);
        board = new Board(5,5);
        GameState.getInstance().setBoard(board);
        board.setAllTiles(testMap);
        enemy = new Enemy(2, 2, 100);
    }




    @Test
    public void testGetPossibleLocations() {

        // Test for distance 0
        List<Point> result = enemy.getPossibleLocations(0);
        assertEquals(1, result.size());
        assertTrue(result.contains(new Point(2, 2)));

        // Test for distance 1
        result = enemy.getPossibleLocations(1);
        assertEquals(2, result.size());
        assertTrue(result.contains(new Point(2, 3)));
        assertTrue(result.contains(new Point(1, 2)));

        // Test for distance 2
        result = enemy.getPossibleLocations(2);
        assertEquals(5, result.size());
        assertTrue(result.contains(new Point(2, 0)));
        assertTrue(result.contains(new Point(4, 2)));
        assertTrue(result.contains(new Point(2, 4)));
        assertTrue(result.contains(new Point(0, 2)));
        assertTrue(result.contains(new Point(3, 3)));
    }

    @Test
    public void testGetNewRandomLocation() {

        // Test getNewRandomLocation method
        for (int i = 0; i < 100; i++) {  // Repeat multiple times because it's a random output
            Point result = enemy.getNewRandomLocation(3);  //
            assertNotNull(result);
            int distance = Enemy.manhattanDistance(enemy.getX(), enemy.getY(), result.x, result.y);
            assertEquals(3, distance);
            assertEquals(Tile.Empty, board.getTile(result.x, result.y));
        }
    }

    @Test
    public void testGetDirectionToNextTile() {

        // Test RIGHT direction
        AStarAlgorithm.Node rightNode = new AStarAlgorithm.Node(3, 2, null);
        assertEquals(Direction.RIGHT, enemy.getDirectionToNextTile(rightNode));

        // Test LEFT direction
        AStarAlgorithm.Node leftNode = new AStarAlgorithm.Node(1, 2, null);
        assertEquals(Direction.LEFT, enemy.getDirectionToNextTile(leftNode));

        // Test DOWN direction
        AStarAlgorithm.Node downNode = new AStarAlgorithm.Node(2, 3, null);
        assertEquals(Direction.DOWN, enemy.getDirectionToNextTile(downNode));

        // Test UP direction
        AStarAlgorithm.Node upNode = new AStarAlgorithm.Node(2, 1, null);
        assertEquals(Direction.UP, enemy.getDirectionToNextTile(upNode));


        AStarAlgorithm.Node sameNode = new AStarAlgorithm.Node(2, 2, null);
        assertNull(enemy.getDirectionToNextTile(sameNode));
    }

    @Test
    public void testMoveToNextTileInPath() {

        // Create a mock path for the enemy
        ArrayList<AStarAlgorithm.Node> mockPath = new ArrayList<>();
        mockPath.add(new AStarAlgorithm.Node(2, 3, null));  // DOWN
        mockPath.add(new AStarAlgorithm.Node(3, 3, null));  // RIGHT
        enemy.setPath(mockPath);

        // Move to the next tile
        enemy.moveToNextTileInPath();

        // Validate that enemy has moved DOWN
        assertEquals(2, enemy.getX()); // Expected X to remain the same
        assertEquals(3, enemy.getY()); // Expected Y to increase by 1 for DOWN direction

        // Move to the next tile
        enemy.moveToNextTileInPath();

        // Validate that enemy has moved RIGHT
        assertEquals(3, enemy.getX()); // Expected X to increase by 1 for RIGHT direction
        assertEquals(3, enemy.getY()); // Expected Y to remain the same

        // Test with no path
        enemy.setPath(new ArrayList<>());  // Set empty path
        enemy.moveToNextTileInPath();

        // Validate that enemy has not moved
        assertEquals(3, enemy.getX());
        assertEquals(3, enemy.getY());
    }

    @Test
    public void testIsWithinFieldOfView() {
        Enemy enemy = new Enemy(4, 4, 100);

        // 1. Player is exactly at the enemy's position
        assertTrue(enemy.isWithinFieldOfView(4, 4, 3));

        // 2. Player is within the enemy's field of view
        assertTrue(enemy.isWithinFieldOfView(5, 5, 3));
        assertTrue(enemy.isWithinFieldOfView(3, 3, 3));
        assertTrue(enemy.isWithinFieldOfView(6, 4, 3));

        // 3. Player is outside the enemy's field of view
        assertFalse(enemy.isWithinFieldOfView(9, 9, 3));
        assertFalse(enemy.isWithinFieldOfView(2, 2, 3));
    }

    @Test
    public void testHasLineOfSight() {
        // 1. Player is in direct line of sight (No obstacles)
        assertTrue(enemy.hasLineOfSight(player));
        // 2. Player is behind a wall (Obstacle in the way)
        assertFalse(enemy.hasLineOfSight(player2));

    }

    @Test
    public void testActWithPlayerInViewAndLineOfSight() {
        GameState.getInstance().setPlayer(player);
        enemy.act();
        assertEquals(EnemyMode.AttackMode, enemy.getCurrentMode());
        // Enemy should move towards player
        assertEquals(2, enemy.getX());
        assertEquals(3, enemy.getY());

    }

    @Test
    public void testActWithPlayerInViewAndOutOfLineOfSight() {
        GameState.getInstance().setPlayer(player2);
        enemy.act();
        assertEquals(EnemyMode.RoamMode, enemy.getCurrentMode());

    }

    @Test
    public void testActWithPlayerOutOfView() {
        Player player3 = new Player(0, 0, 100);
        GameState.getInstance().setPlayer(player3);
        enemy.act();
        assertEquals(EnemyMode.RoamMode, enemy.getCurrentMode());
    }

    @Test
    public void testActWithEnemyAtTargetLocationInRoamMode() {
        Player player3 = new Player(0, 0, 100);
        GameState.getInstance().setPlayer(player3);
        enemy.setTargetLocation(new Point(2, 2));
        enemy.act();
        // Assertion to check if enemy picked a new target location
        assertNotEquals(new Point(2, 2), enemy.getTargetLocation());
    }


}









