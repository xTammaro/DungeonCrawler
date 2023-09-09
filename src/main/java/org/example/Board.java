package org.example;

/**
 * The game board. Represented as an array of tiles.
 */
public class Board {
    Tile[][] tiles;



    /**
     * @author Jake Tammaro
     * Checks if a given tile is empty.
     * @param tile
     * @return true if the tile is empty, false otherwise
     */

    boolean isEmptyTile(Tile tile) {
        return tile == Tile.Empty || tile == Tile.EmptyWithKey;
    }

    boolean isOutOfBounds(int x, int y) {
        int width = tiles[0].length;  // Width is based on the length of the first row.
        int height = tiles.length;    // Height is based on the number of rows.
        return x < 0 || x >= width || y < 0 || y >= height;
    }



    public Tile[][] getTiles() {
        return tiles;
    }
}
