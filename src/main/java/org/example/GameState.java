package org.example;

import org.example.item.*;
import org.example.shop.*;
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
     * The player's inventory as a list of Items
     */

    List<ShopItem> inventory;

    /**
     * A list of all Items that have a chance
     * of spawning in game
     */
    private List<Item> gameItems;

    /**
     * The amount of gold the player has
     */
    int gold;
    /**
     * the MeleeWeapon the player has equipped
     */
     MeleeWeapon currentMeleeWeapon;

    /**
     * the RangedWeapon the player has equipped
     */
     RangedWeapon currentRangedWeapon;

    /**
     * Determines whether we're in normal gameplay, or in the shop/inventory/title screen, etc.
     */
    enum GameMode {
        Gameplay,
        TitleScreen,
        GameOverScreen,
        Inventory,
        Shop,
        Ending
    }
    GameMode mode = GameMode.TitleScreen;

    /**
     * Changes what game mode that we're in. Used to, for example, determine if we are in the inventory/shop
     * and to use the control scheme for that. The UI also uses this to determine what it should display.
     * Should be called when switching from title screen to gameplay, when toggling the inventory or shop modes,
     * and when the game is won or lost.
     *
     * @param mode The GameMode to switch into.
     */
    public void setGameMode(GameMode mode) {
        this.mode = mode;
        Renderer.getInstance().renderEverything();
    }

    /**
     * @return Returns the current game mode. See comment for setGameMode()
     */
    public GameMode getGameMode() {
        return mode;
    }

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
     * gives the player starting weapons
     * adds Items to gameItems
     *
     * @author Alex Boxall
     * @author Will Baird
     */
    private GameState() {
        inventory = new ArrayList<>();
        currentMeleeWeapon = new MeleeWeapon("Stick",5, Rarity.COMMON,1);
        currentRangedWeapon = new RangedWeapon("Sling",5,Rarity.COMMON,1,2);
        //TODO: Move adding Items to somewhere else
        createGameItems();

    }

    private void createGameItems(){
        gameItems = new ArrayList<>();
        gameItems.add(new HealthPotion("Healing Potion",25,Rarity.COMMON,10));
        gameItems.add(new HealthPotion("Healing Potion",40,Rarity.UNCOMMON,20));
        gameItems.add(new HealthPotion("Greater Healing Potion",50,Rarity.RARE,30));
        gameItems.add(new HealthPotion("Supreme Healing Potion",75,Rarity.VERY_RARE,5));
        gameItems.add(new MeleeWeapon("Wooden Sword",20,Rarity.COMMON,2));
        gameItems.add(new MeleeWeapon("Copper Sword",50,Rarity.UNCOMMON,4));
        gameItems.add(new MeleeWeapon("Iron Sword",100,Rarity.RARE,8));
        gameItems.add(new MeleeWeapon("Steel Sword",300,Rarity.VERY_RARE,1));
        gameItems.add(new RangedWeapon("Slingshot",25,Rarity.COMMON,3,2));
        gameItems.add(new RangedWeapon("Bow",60,Rarity.UNCOMMON,8,2));
        gameItems.add(new RangedWeapon("Longbow",120,Rarity.RARE,25,4));
        gameItems.add(new RangedWeapon("Crossbow",350,Rarity.VERY_RARE,30,2));
        gameItems.add(new StatBoostItem("Health Ring",100,Rarity.COMMON,PlayerStatType.HEALTH,10));
        gameItems.add(new StatBoostItem("Health Brooch",150,Rarity.UNCOMMON,PlayerStatType.HEALTH,15));
        gameItems.add(new StatBoostItem("Health Amulet",200,Rarity.RARE,PlayerStatType.HEALTH,20));
        gameItems.add(new StatBoostItem("Health Necklace",300,Rarity.VERY_RARE,PlayerStatType.HEALTH,30));

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
     * Returns the current shop that the player is in. If the player isn't in a shop, null is returned.
     *
     * @author Alex Boxall
     *
     * @return The Shop object, or null if the player isn't in a shop.
     */
    Shop getShop() {
        if (mode != GameMode.Shop) {
            return null;
        }
        return ShopFactory.getInstance().getShop(levelNumber, player.x, player.y);
    }

    /**
     * Action handler for when in regular gameplay. Used to allow the player to move,
     * open inventory/shops, etc.
     *
     * @author Alex Boxall
     * @author Tal Shy-Tielen
     * @param action The action that the user should take.
     */
    void actGameplay(Action action) {
        if (action.isMove() && mode == GameMode.Gameplay) {
            if (player.canMoveInDirection(action.getDirection())) {
                player.moveInDirection(action.getDirection());
            }
            endOfPlayerTurn();

        } else if (action == Action.OpenInventory) {
            GameState.getInstance().setGameMode(GameMode.Inventory);

        } else if (action == Action.EnterShop && board.getTile(player.x, player.y) == Tile.Shop) {
            GameState.getInstance().setGameMode(GameMode.Shop);
        }
    }

    /**
     * Action handler for the title screen. Used to start and load games.
     *
     * @author Alex Boxall
     *
     * @param action The action that the user should take.
     */
    void actTitleScreen(Action action) {
        if (action == Action.StartGame) {
            GameState.getInstance().setGameMode(GameMode.Gameplay);
        }
    }

    /**
     * Action handler for the shop. Allows users to buy items, and/or exit
     * the shop.
     *
     * @author Alex Boxall
     *
     * @param action The action that the user should take.
     */
    void actShop(Action action) {
        if (action == Action.EnterShop) {
            GameState.getInstance().setGameMode(GameMode.Gameplay);
        }
    }

    /**
     * Action handler for the inventory screen. Allows users to buy items, and/or exit
     * the shop.
     *
     * @author Alex Boxall
     *
     * @param action The action that the user should take.
     */
    void actInventory(Action action) {
        if (action == Action.OpenInventory) {
            GameState.getInstance().setGameMode(GameMode.Gameplay);
        }
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
        /*
         * TODO: State design pattern?
         */
        switch (mode) {
            case Gameplay       -> actGameplay(action);
            case Inventory      -> actInventory(action);
            case Shop           -> actShop(action);
            case TitleScreen    -> actTitleScreen(action);
            default             -> System.out.printf("Action cannot be used here.\n");
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

    public List<Item> getGameItems() {
        return gameItems;
    }

}
