package org.example;

/**
 * The game board. Represented as an array of tiles.
 */
public class Board {
    private Tile[][] tiles;

    public Board() {
        tiles = null;
    }

    /**
     * @author Tal Shy-Tielen
     * @param x width of the new board.
     * @param y height of new board.
     */
    public Board(int x, int y) {
        this.tiles = new Tile[y][x];
    }

    /**
     * @author Alex Boxall
     * @param tiles all of the tiles to load
     */
    public void setAllTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    /**
     * @author Alex Boxall
     * @return the width of the board, in tiles
     */
    public int getWidth() {
        return tiles[0].length;
    }

    /**
     * @author Alex Boxall
     * @return the height of the board, in tiles
     */
    public int getHeight() {
        return tiles.length;
    }

    /**
     * @author Tal Shy-Tielen
     * @param x x coordinate of tile.
     * @param y y coordinate of tile.
     * @return the tile at that position. If the tile is not on the board it will return null.
     * Note: function assumes the board is at least one tile tall.
     */
    public Tile getTile(int x, int y) {
        if (x >= getWidth() || x < 0) {
            return null;
        }
        if (y >= getHeight() || y < 0) {
            return null;
        }
        return tiles[y][x];
    }

    /**
     * @author Tal Shy-Tielen, Alex Boxall
     * @param x x coordinate of tile.
     * @param y y coordinate of tile.
     * @param tile the tile to put at that position
     * Note: function assumes the board is at least one tile tall.
     */
    public void setTile(int x, int y, Tile tile) {
        if (x >= getWidth() || x < 0) {
            return;
        }
        if (y >= getHeight() || y < 0) {
            return;
        }
        tiles[y][x] = tile;
    }
}
