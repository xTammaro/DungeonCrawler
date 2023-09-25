package org.example;

import java.awt.*;
import java.util.Scanner;

public class RendererConsole implements Renderer {
    /**
     * Holds the singleton object of the renderer.
     */
    private static RendererConsole instance;

    /**
     * Returns the singleton Renderer.
     *
     * @return The single Renderer instance.
     */
    public static RendererConsole getInstance() {
        if (instance == null) {
            instance = new RendererConsole();
        }
        return instance;
    }

    /**
     * The private constructor for the console singleton.
     */
    private RendererConsole() {

    }

    @Override
    public void addMeleeAttackAnimation(Actor actor, Direction direction, boolean enemy) {
        /*
         * The console version doesn't support attack animations, so nothing to do here.
         */
    }

    @Override
    public void addRangedAttackAnimation(Player player, int minX, int minY, int size) {
        /*
         * The console version doesn't support attack animations, so nothing to do here.
         */
    }

    @Override
    public void removeAttackAnimations() {
        /*
         * The console version doesn't support attack animations, so nothing to do here.
         */
    }

    @Override
    public void displayMessageScreen(Color background, Color foreground, String message, GameState.GameMode mode) {
        drawMessage(message);
        waitEnter();
    }

    private Scanner scanner = new Scanner(System.in);

    /**
     * Waits for the player to press a character and then ENTER.
     * @author Alex Boxall
     * @return The character that was entered
     */
    private char getCharacter() {
        while (!scanner.hasNextLine()) {
            ;
        }
        return (scanner.nextLine() + "\n").charAt(0);
    }

    /**
     * Waits for the player to press ENTER.
     * @author Alex Boxall
     */
    private void waitEnter() {
        while (!scanner.hasNextLine()) {
            ;
        }
        scanner.nextLine();
    }

    /**
     * Draws a message on screen.
     * @author Alex Boxall
     * @param message The text to display.
     */
    private void drawMessage(String message) {
        System.out.printf("------------------------------------------\n%s\n------------------------------------------\n", message);
    }

    /**
     * Given a tile type, returns a character to represent that tile.
     * @author Alex Boxall
     * @param t The tile
     * @return The character representing the tile
     */
    private char getCharacterForTile(Tile t) {
        return switch (t) {
            case Wall -> '#';
            case Empty -> ' ' ;
            case EmptyWithKey -> 'K';
            case Staircase -> 'X';
            case Shop -> 'S';
            case Chest -> 'C';
        };
    }

    /**
     * Draws the gameplay screen onto the terminal.
     * @author Alex Boxall
     */
    private void drawGameplay() {
        GameState state = GameState.getInstance();

        /*
         * Render all of the tiles in the game board. The key is also technically
         * represented as a type of tile, so that is also handled here.
         */
        for (int y = 0; y < state.board.getHeight(); ++y) {
            for (int x = 0; x < state.board.getWidth(); ++x) {
                if (state.isOccupiedbyEnemy(x, y)) {
                    System.out.print("E");

                } else if (state.isOccupied(x, y)) {
                    System.out.print(switch (state.player.getDirection()) {
                        case UP -> '^';
                        case DOWN -> 'v';
                        case LEFT -> '<';
                        case RIGHT -> '>';
                    });

                } else {
                    System.out.print(getCharacterForTile(state.board.getTile(x, y)));
                }
            }
            System.out.print('\n');
        }

        System.out.printf("\tHP: %d/%d\n\tGold: %d\n\tLevel: %d\n\t%s\n",
                state.player.getHP(),
                GameState.getInstance().getPlayer().getMaxHealth(),
                state.getGold(),
                state.levelNumber,
                state.hasKey ? "Got key" : ""
        );
    }

    /**
     * Draws the console inventory.
     * @author Alex Boxall
     */
    private void drawInventory() {
        drawMessage(
                "Current Melee Weapon = " + GameState.getInstance().getCurrentMeleeWeapon().getName()
                        + " Damage = " + GameState.getInstance().getCurrentMeleeWeapon().getDamage()
                        + "\n"
                        + "Current Ranged Weapon = " + GameState.getInstance().getCurrentRangedWeapon().getName()
                        + " Damage = " + GameState.getInstance().getCurrentRangedWeapon().getDamage()
                        + "\n"
                        + GameState.getInstance().printInventory());
    }

    /**
     * Redraws the entire screen, depending on what the game mode currently is.
     * @author Alex Boxall
     */
    @Override
    public void renderEverything() {
        switch (GameState.getInstance().getGameMode()) {
            case Chest -> drawMessage(GameState.getInstance().getChest().printLoot());
            case Shop -> drawMessage(GameState.getInstance().getShop().printInventory());
            case GameOverScreen -> drawMessage("GAME OVER!\n\nPress ENTER to restart.");
            case TitleScreen -> drawMessage("*REALLY* AWESOME COMP2120 GAME (TM)\n\nPress ENTER to start\n");
            case Inventory -> drawInventory();
            case Gameplay -> drawGameplay();
        }
    }

    /**
     * Draws the parts of the screen that need updating. This is a console application, so the whole
     * thing will be redrawn each time.
     * @author Alex Boxall
     */
    @Override
    public void render() {
        renderEverything();
    }

    /**
     * The main body of the game. Gets a character from the keyboard and acts upon it.
     * To be called in a loop.
     *
     * @author Alex Boxall
     */
    public void run() throws Exception {
        switch (Character.toUpperCase(getCharacter())) {
            case 'A'  -> GameState.getInstance().act(Action.MoveLeft);
            case 'D'  -> GameState.getInstance().act(Action.MoveRight);
            case 'W'  -> GameState.getInstance().act(Action.MoveUp);
            case 'S'  -> GameState.getInstance().act(Action.MoveDown);
            case 'Z'  -> GameState.getInstance().act(Action.UseGun);
            case 'X'  -> GameState.getInstance().act(Action.UseSword);
            case 'Q'  -> GameState.getInstance().act(Action.UsePotion);
            case ' '  -> GameState.getInstance().act(Action.EnterShop);
            case 'I'  -> GameState.getInstance().act(Action.OpenInventory);
            case '\n' -> GameState.getInstance().act(Action.StartGame);
            case 'C'  -> GameState.getInstance().act(Action.EnterChest);
            case '1'  -> GameState.getInstance().act(Action.KeyPress1);
            case '2'  -> GameState.getInstance().act(Action.KeyPress2);
            case '3'  -> GameState.getInstance().act(Action.KeyPress3);
            case '4'  -> GameState.getInstance().act(Action.KeyPress4);
            case '5'  -> GameState.getInstance().act(Action.KeyPress5);
            default -> {
                displayMessageScreen(null, null, "Not a valid action. Press ENTER to continue.", null);
                renderEverything();
            }
        }
    }
}
