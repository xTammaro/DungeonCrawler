package org.example;

/**
 * The game board. Represented as an array of tiles.
 */
public class Board {
    Tile[][] tiles;

    /**
     * @author Tal Shy-Tielen
     * @param y height of new board.
     * @param x width of the new board.
     */
    public Board(int y, int x) {
        this.tiles = new Tile[y][x];
    }
    /**
     * @author Tal Shy-Tielen
     * @param y y coordinate of tile.
     * @param x x coordinate of tile.
     * @return the tile at that position. If the tile is not on the board it will return null.
     * Note: function assumes the board is at least one tile tall.
     */
    public Tile getTile(int y, int x) {
        if (x >= tiles[0].length || x < 0) {
            return null;
        }
        if (y >= tiles.length || y < 0) {
            return null;
        }
        return tiles[x][y];
    }

}