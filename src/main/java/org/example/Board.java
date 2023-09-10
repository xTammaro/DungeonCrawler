package org.example;

/**
 * The game board. Represented as an array of tiles.
 */
public class Board {
    Tile[][] tiles;

    public Board(int x, int y) {
        this.tiles = new Tile[x][y];
    }

    public Tile getTile(int x, int y) {
        if (x >= tiles.length || x < 0) {
            return null;
        }
        if (y >= tiles[0].length || y < 0) {
            return null;
        }
        return tiles[x][y];
    }

}