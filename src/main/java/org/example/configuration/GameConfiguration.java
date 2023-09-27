package org.example.configuration;
import org.example.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This class is responsible for configuring the game based on a JSON file.
 */
public class GameConfiguration {
    public JSONObject configData;


    public GameConfiguration(String path) throws Exception {
        this.configData = loadJsonFromFile(path);
    }

    /**
     * Loads a JSON file from the given path.
     * @author Jake Tammaro
     * @param path The path to the JSON file.
     * @return A JSONObject representing the JSON file.
     * @throws Exception If the file cannot be found or parsed.
     */

    public JSONObject loadJsonFromFile(String path) throws Exception {
        // Step 1: Read the file content into a string
        String content = new String(Files.readAllBytes(Paths.get(path)));

        // Step 2: Parse the string into a JSONObject
        return new JSONObject(content);
    }


    /**
     * Gets the board string layout for a given floor.
     * @author Jake Tammaro
     * @param floor The floor to get the board layout for.
     * @return A string array representing the board layout for the given floor.
     */
    public String[] getBoardLayout(int floor) {
        JSONArray boardArray = configData.getJSONArray("floors")
                .getJSONObject(floor)
                .getJSONArray("board");
        String[] boardLayout = new String[boardArray.length()];
        for (int i = 0; i < boardArray.length(); i++) {
            boardLayout[i] = boardArray.getString(i);
        }
        return boardLayout;
    }

    /**
     * Uses the string array board layout to initialize the game.
     * @param boardLayout The string array board layout.
     * @author Jake Tammaro
     */
    public void initializeGame(String[] boardLayout) {
        int height = boardLayout.length;
        int width = boardLayout[0].length(); // assuming all strings in the boardLayout have the same length

        Tile[][] tiles = new Tile[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                char currentChar = boardLayout[y].charAt(x);
                if (Character.isDigit(currentChar)) {
                    // Create an enemy at this position of this level
                    initializeEnemy(x, y, Character.toString(currentChar));
                    tiles[y][x] = Tile.Empty;
                } else if (currentChar == '@') {
                    // Initialize the player's here
                    initializePlayer(x,y);
                    tiles[y][x] = Tile.Empty;
                } else {
                    tiles[y][x] = charToTile(currentChar);
                }
            }
        }
        // Initialize the board with all the tiles
        Board board = new Board(width, height);
        board.setAllTiles(tiles);
        GameState.getInstance().setBoard(board);
        GameState.getInstance().allEnemiesPathFind();
    }

    /**
     * Initializes the player at the given position.
     * @author Jake Tammaro
     * @param x The x position of the player.
     * @param y The y position of the player.
     */
    private void initializePlayer(int x, int y) {
        int health;
        // If first level, use the config data, otherwise use the player's current health
        if (GameState.getInstance().getPlayer() == null) {
            health = configData.getJSONObject("player").getInt("health");
        }
        else {
            health = GameState.getInstance().getPlayer().getHp();
        }

        Player player = new Player(x, y, health);

        GameState.getInstance().setPlayer(player);
    }

    /**
     * Creates an enemy at the given position of the given level.
     * @author Jake Tammaro
     * @param level The level of the enemy.
     * @param x The x position of the enemy.
     * @param y The y position of the enemy.
     */
    private void initializeEnemy(int x, int y, String level) {
        int health = configData.getJSONObject("enemies").getJSONObject(level).getInt("health");
        int damage = configData.getJSONObject("enemies").getJSONObject(level).getInt("attack");

        Enemy enemy = new Enemy(x, y, health, damage);
        if (GameState.getInstance().getEnemies() == null) {
            GameState.getInstance().setEnemies(new java.util.ArrayList<>());
        }
        GameState.getInstance().getEnemies().add(enemy);
    }

    private Tile charToTile(char ch) {
        switch (ch) {
            case '#':
                return Tile.Wall;
            case 'S':
                return Tile.Shop;
            case 'C':
                return Tile.Chest;
            case 'E':
                return Tile.Staircase;
            // ... handle other characters like enemies, items, etc.
            default:
                return Tile.Empty;
        }
    }



    // Other methods to extract enemy configurations, board layouts, etc.
}

