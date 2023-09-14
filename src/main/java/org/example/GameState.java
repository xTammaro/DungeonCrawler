package org.example;

import shop.ShopItem;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    /**
     * The player object.
     */
    Player player;



    /**
     * All of the enemies that are on the current board, and still alive.
     */
    List<Enemy> enemies;

    /**
     * The layout of the current floor.
     */
    Board board;

    /**
     * Which floor we are on.
     */
    int levelNumber = 0;

    /**
     * Whether or not the player is holding a key.
     */
    boolean hasKey = false;


    /**
     * the players inventory as a list of Items
     */

    List<ShopItem> inventory;



    /**
     * The amount of gold the player has
     */
    int gold;

    /**
     * @author Will Baird
     * empties the players  inventory
     * used for testing
     */
    public void clearInventory(){
        this.inventory = new ArrayList<>();
    }

    /**
     * @author Will Baird
     * makes a String message to be displayed to the player listing
     * all the Items in the players inventory has in it
     * @return String list of ShopItems
     */
    public String printInventory() {
        StringBuilder s = new StringBuilder();
        if (inventory.isEmpty()){
            return s.append("Inventory Empty").toString();
        }
        int i = 1;
        for (ShopItem shopItem: inventory) {
            s.append(String.format("%d. %s", i, shopItem.chestDescription())).append("\n");
            i++;
        }
        return s.toString();
    }
     /**
     * @author Will Baird
     * @return a list of the players Items
     */
    public List<ShopItem> getInventory() {
        return inventory;
    }

    /**
     * @author Will Baird
     * @return the players gold
     */

    public int getGold() {
        return gold;
    }

    /**
     * @author Will Baird
     * sets the players gold to new int
     * @param gold the new amount of gold
     */
    public void setGold(int gold) {
        this.gold = gold;
    }

    /**
     * @author Will Baird
     * adds an item to the players inventory
     * @param shopItem the item to be added
     */
    public void addToInventory(ShopItem shopItem){
        inventory.add(shopItem);
    }
    private static GameState instance;

    /**
     * The private constructor for the GameState singleton.
     * Should initialise the game for the very first level.
     *
     * @author Alex Boxall
     */
    private GameState() {
        this.inventory = new ArrayList<>();
    }

    /**
     * Returns the global GameState object. It creates the object if it hasn't
     * been created yet.
     *
     * @author Alex Boxall
     *
     * @return The global GameState object.
     */
    public static GameState getInstance() {
        if (instance == null) {
            instance = new GameState();
        }
        return instance;
    }

    /**
     * Loads a given floor of the dungeon. It is located in this file so it can update all of the other
     * parts of the GameState, e.g. enemies, player, etc.
     *
     * @param num The level number to load
     *
     * @author Alex Boxall
     */
    void loadFloor(int num) {
        // TODO: load level

        // TODO: set the values here correctly based on the map
        // TODO: need to set player health correctly
        player = new Player(0, 0, 10);
        enemies = new ArrayList<>();
    }

    /**
     * Called at the end of the player's turn. Handles logic such as finishing a level, or picking up a
     * key, and then makes all of the enemies move.
     *
     * @author Alex Boxall
     */
    void endOfPlayerTurn() {
        if (board.getTile(player.x, player.y) == Tile.Staircase && hasKey) {
            /*
             * Move onto the next floor. Return early as the rest of the code here will assume
             * we haven't moved floors. We must remember to update the screen due to this early
             * return.
             */
            levelNumber++;
            hasKey = false;
            loadFloor(levelNumber);
            Renderer.getInstance().render();
            return;
        }

        if (board.getTile(player.x, player.y) == Tile.EmptyWithKey && !hasKey) {
            /*
             * Make the player pick up the key.
             */
            hasKey = true;
            board.setTile(player.x, player.y, Tile.Empty);
        }

        /*
         * Now all of the enemies get their turn to make a move.
         */
        for (Enemy enemy: enemies) {
            enemy.act();
        }

        Renderer.getInstance().render();
    }

    /**
     * Makes the player take an action. This should be called by the keyboard handler.
     * Depending on the action, time may progress (i.e. enemies will act afterwards).
     *
     * @author Alex Boxall
     * @author Tal Shy-Tielen
     * @param action The action that the user should take.
     */
    void act(Action action) {
        if (action.isMove()) {
            if (player.canMoveInDirection(action.getDirection())) {
                player.moveInDirection(action.getDirection());
            }
            endOfPlayerTurn();
        } else {
            System.out.printf("ACTION %s NOT IMPLEMENTED!\n", action);
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
