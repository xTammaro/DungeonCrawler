package org.example.enemy;
import org.example.Board;
import org.example.Tile;
import org.junit.Test;
import org.example.AStarAlgorithm;
import org.example.AStarAlgorithm.Node;

import java.util.List;

import static org.junit.Assert.*;

public class AStarAlgorithmTest {

    @Test
    public void testBuildPath() {
        Board board = new Board(10, 10);
        // Setup
        AStarAlgorithm aStar = new AStarAlgorithm(board);

        Node start = new Node(0, 0, null);
        Node first = new Node(1, 1, start);
        Node second = new Node(2, 2, first);
        Node goal = new Node(3, 3, second);

        // Execution
        List<Node> result = aStar.buildPath(goal);

        // Assertions
        assertEquals(4, result.size()); // Check if the path contains 4 nodes
        assertEquals(start, result.get(0)); // Check if the first node in the result is the start node
        assertEquals(first, result.get(1)); // Check if the second node in the result is the first node
        assertEquals(second, result.get(2)); // Check the third
        assertEquals(goal, result.get(3)); // Check if the last node in the result is the goal node
    }

    @Test
    public void testAllNeighborsAreEmptyTiles() {
        // Set up the board such that the node at (1,1) has all empty neighbors
        Tile[][] map = {
                {Tile.Empty, Tile.Empty, Tile.Empty},
                {Tile.Empty, Tile.Empty, Tile.Empty},
                {Tile.Empty, Tile.Empty, Tile.Empty}
        };
        Board board = new Board(3, 3);
        board.setAllTiles(map);
        AStarAlgorithm aStar = new AStarAlgorithm(board);

        Node node = new Node(1, 1, null);

        List<Node> neighbors = aStar.getNeighbors(node);

        assertEquals(4, neighbors.size());  // Expecting 4 neighbors

        // Assertions to check the coordinates of the 4 neighbors
        assertTrue(neighbors.contains(new Node(2, 1, node)));
        assertTrue(neighbors.contains(new Node(0, 1, node)));
        assertTrue(neighbors.contains(new Node(1, 2, node)));
        assertTrue(neighbors.contains(new Node(1, 0, node)));
    }

    @Test
    public void testNoNeighborsAreEmptyTiles() {
        // Set up the board such that the node at (1,1) has no empty neighbors
        Tile[][] map = {
                {Tile.Wall, Tile.Wall, Tile.Wall},
                {Tile.Wall, Tile.Empty, Tile.Wall},
                {Tile.Wall, Tile.Wall, Tile.Wall}
        };
        Board board = new Board(3, 3);
        board.setAllTiles(map);
        AStarAlgorithm aStar = new AStarAlgorithm(board);
        Node node = new Node(1, 1, null);


        List<Node> neighbors = aStar.getNeighbors(node);

        assertEquals(0, neighbors.size());  // Expecting 0 neighbors
    }

    @Test
    public void testBasicPathfinding() {
        // Create a 3x3 grid with all tiles being empty.
        Tile[][] map = {
                {Tile.Empty, Tile.Empty, Tile.Empty},
                {Tile.Empty, Tile.Empty, Tile.Empty},
                {Tile.Empty, Tile.Empty, Tile.Empty}
        };

        Board board = new Board(3, 3);
        board.setAllTiles(map);

        AStarAlgorithm aStar = new AStarAlgorithm(board);

        // Find path from (0,0) to (2,2)
        List<Node> path = aStar.findPath(0, 0, 2, 2);

        // Ensure a path was found
        assertNotNull(path);

        // Ensure the path starts at (0,0) and ends at (2,2)
        assertEquals(0, path.get(0).x);
        assertEquals(0, path.get(0).y);
        assertEquals(2, path.get(path.size() - 1).x);
        assertEquals(2, path.get(path.size() - 1).y);
        assertEquals(5, path.size()); // Ensure the path contains 5 nodes

        // Print path
        aStar.printPath(board, path);
    }

    @Test
    public void testPathfindingWithObstacles() {
        // Set up the board with obstacles
        Tile[][] map = {
                {Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty},
                {Tile.Empty, Tile.Wall, Tile.Wall, Tile.Wall, Tile.Wall},
                {Tile.Empty, Tile.Empty, Tile.Empty, Tile.Wall, Tile.Empty},
                {Tile.Wall, Tile.Wall, Tile.Empty, Tile.Empty, Tile.Empty},
                {Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty}
        };

        Board board = new Board(5,5); // Assuming Board has a constructor to set tiles directly
        board.setAllTiles(map);
        AStarAlgorithm aStar = new AStarAlgorithm(board);

        List<Node> path = aStar.findPath(0, 0, 4, 4);

        // Basic assertion to make sure a path is found
        assertNotNull(path);

        // Make sure the path doesn't pass through any blocked tiles
        for (Node node : path) {
            assertNotEquals(Tile.Wall, board.getTile(node.x, node.y));
        }

        // Optional: Print the path for visualization
        aStar.printPath(board, path);
    }

    @Test
    public void testPathFindingImpossiblePath() {
        // Set up the board with obstacles
        Tile[][] map = {
                {Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty},
                {Tile.Empty, Tile.Empty, Tile.Wall, Tile.Wall, Tile.Empty},
                {Tile.Empty, Tile.Empty, Tile.Wall, Tile.Wall, Tile.Empty},
                {Tile.Wall, Tile.Wall, Tile.Wall, Tile.Wall, Tile.Wall},
                {Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty}
        };

        Board board = new Board(5,5); // Assuming Board has a constructor to set tiles directly
        board.setAllTiles(map);
        AStarAlgorithm aStar = new AStarAlgorithm(board);

        List<Node> path = aStar.findPath(0, 0, 0, 4);

        // Assert no path is found
        assertNull(path);
    }

    @Test
    public void testDifficultPathfindingWithObstacles() {
        // Set up the board with obstacles
        Tile[][] map = {
                {Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty},
                {Tile.Empty, Tile.Empty, Tile.Wall, Tile.Wall, Tile.Empty},
                {Tile.Empty, Tile.Empty, Tile.Wall, Tile.Wall, Tile.Empty},
                {Tile.Wall, Tile.Wall, Tile.Wall, Tile.Wall, Tile.Empty},
                {Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty}
        };

        Board board = new Board(5,5); // Assuming Board has a constructor to set tiles directly
        board.setAllTiles(map);
        AStarAlgorithm aStar = new AStarAlgorithm(board);

        List<Node> path = aStar.findPath(0, 0, 0, 4);

        // Basic assertion to make sure a path is found
        assertNotNull(path);

        // Make sure the path doesn't pass through any blocked tiles
        for (Node node : path) {
            assertNotEquals(Tile.Wall, board.getTile(node.x, node.y));
        }

        // Optional: Print the path for visualization
        aStar.printPath(board, path);
    }

    @Test
    public void testLargeDifficultMapPathfindingWithObstacles() {
        // Set up the board with obstacles
        Tile[][] map = {
                {Tile.Empty, Tile.Empty, Tile.Empty,  Tile.Empty,  Tile.Empty,  Tile.Wall,  Tile.Wall,  Tile.Wall,  Tile.Wall,  Tile.Empty},
                {Tile.Empty,  Tile.Wall,  Tile.Wall,  Tile.Wall,  Tile.Wall,  Tile.Wall,  Tile.Wall,  Tile.Wall,  Tile.Wall,  Tile.Empty},
                {Tile.Empty, Tile.Wall, Tile.Wall, Tile.Wall, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty,  Tile.Empty},
                {Tile.Empty,  Tile.Wall,  Tile.Empty,  Tile.Empty,  Tile.Wall,  Tile.Wall,  Tile.Empty,  Tile.Wall, Tile.Wall,  Tile.Empty},
                {Tile.Empty,  Tile.Wall, Tile.Empty, Tile.Empty, Tile.Wall, Tile.Empty, Tile.Empty,  Tile.Wall, Tile.Wall,  Tile.Empty},
                {Tile.Empty,  Tile.Wall,  Tile.Empty,  Tile.Empty,  Tile.Empty,  Tile.Empty, Tile.Empty,  Tile.Empty, Tile.Wall,  Tile.Empty},
                {Tile.Empty,  Tile.Wall,  Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Wall,  Tile.Wall, Tile.Empty, Tile.Empty},
                {Tile.Empty,  Tile.Wall,  Tile.Empty,  Tile.Empty,  Tile.Wall,  Tile.Wall,  Tile.Empty,  Tile.Wall,  Tile.Wall,  Tile.Empty},
                {Tile.Empty, Tile.Wall, Tile.Empty, Tile.Empty,  Tile.Empty, Tile.Empty, Tile.Empty, Tile.Wall, Tile.Wall, Tile.Empty},
                {Tile.Empty, Tile.Empty,  Tile.Empty, Tile.Empty,  Tile.Empty,  Tile.Empty,  Tile.Empty,  Tile.Empty,  Tile.Wall,  Tile.Empty}
        };

        Board board = new Board(10,10); // Assuming Board has a constructor to set tiles directly
        board.setAllTiles(map);
        AStarAlgorithm aStar = new AStarAlgorithm(board);

        List<Node> path = aStar.findPath(0, 0, 9, 9);

        // Basic assertion to make sure a path is found
        assertNotNull(path);

        // Make sure the path doesn't pass through any blocked tiles
        for (Node node : path) {
            assertNotEquals(Tile.Wall, board.getTile(node.x, node.y));
        }

        // Optional: Print the path for visualization
        aStar.printPath(board, path);
    }









}
