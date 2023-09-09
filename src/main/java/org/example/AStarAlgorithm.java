package org.example;

/**
 * Represents the A* Algorithm, used to generate enemy paths.
 */
public class AStarAlgorithm {
    private Tile[][] map;
    private int width, height;

    public AStarAlgorithm(Tile[][] map) {
        this.map = map;
        this.width = map.length;
        this.height = map[0].length;
    }
}
