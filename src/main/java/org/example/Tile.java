package org.example;

/**
 * Represents a tile on the board.
 */
public enum Tile {
    Empty,
    Wall,
    EmptyWithKey,
    Staircase,
    Shop,
    Chest;

    // Player should only be able to move towards an Empty tile.
    // The Player should only not be able to move into a wall.
    public boolean isMovable() {
        return this != Wall;
    }
}
