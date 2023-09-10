package org.example;

/**
 * Represents a tile on the board.
 */
public enum Tile {
    Empty,
    Wall,
    EmptyWithKey,
    Staircase,
    Shop;

    public boolean isMovable() {
        return this == Empty || this == EmptyWithKey;
    }
}
