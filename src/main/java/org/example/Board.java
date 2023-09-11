package org.example;

/**
 * The game board. Represented as an array of tiles.
 */
public class Board {
    Tile[][] tiles;

    public Board(int x, int y) {
        this.tiles = new Tile[y][x];
    }

    // Returns a tile given the x and y coordinates.
    // Note: this function assumes that the map is at least 1 tile tall.
    public Tile getTile(int x, int y) {
        if (x >= tiles[0].length || x < 0) {
            return null;
        }
        if (y >= tiles.length || y < 0) {
            return null;
        }
        return tiles[y][x];
    }

}
