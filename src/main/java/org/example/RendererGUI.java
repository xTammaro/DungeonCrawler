package org.example;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * Responsible for visually showing the game to the player. Also responsible for catching keyboard
 * inputs, and converting them into Actions the player can make.
 */
public class RendererGUI extends JFrame implements Renderer {
    /**
     * Holds the singleton object of the renderer.
     */
    private static RendererGUI instance;

    /**
     * Returns the singleton Renderer.
     *
     * @return The single Renderer instance.
     */
    public static RendererGUI getInstance() {
        if (instance == null) {
            instance = new RendererGUI();
        }
        return instance;
    }

    /**
     * The dimensions of the entire game window.
     */
    private final int WINDOW_WIDTH = 1200;
    private final int WINDOW_HEIGHT = 800;

    /**
     * The private constructor for the singleton Renderer object. Only gets called once.
     * Initialises the window and adds any required event listeners.
     *
     * @author Alex Boxall
     */
    private RendererGUI() {
        setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setTitle("COMP2120 Assignment 3");

        addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                /*
                 * Dismiss the message screen if there is one.
                 */
                if (messageScreenText != null) {
                    messageScreenText = null;
                    renderEverything();
                    return;
                }

                /*
                 * Gets called as soon as the user presses the key. Converts the keypress
                 * into an action which is then passed onto the GameState.
                 */
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT   -> GameState.getInstance().act(Action.MoveLeft);
                    case KeyEvent.VK_RIGHT  -> GameState.getInstance().act(Action.MoveRight);
                    case KeyEvent.VK_UP     -> GameState.getInstance().act(Action.MoveUp);
                    case KeyEvent.VK_DOWN   -> GameState.getInstance().act(Action.MoveDown);
                    case KeyEvent.VK_Z      -> GameState.getInstance().act(Action.UseGun);
                    case KeyEvent.VK_X      -> {GameState.getInstance().act(Action.UseSword);}
                    case KeyEvent.VK_Q      -> GameState.getInstance().act(Action.UsePotion);
                    case KeyEvent.VK_SPACE  -> GameState.getInstance().act(Action.EnterShop);
                    case KeyEvent.VK_I      -> GameState.getInstance().act(Action.OpenInventory);
                    case KeyEvent.VK_ENTER  -> GameState.getInstance().act(Action.StartGame);
                    case KeyEvent.VK_C      -> GameState.getInstance().act(Action.EnterChest);
                    case KeyEvent.VK_0      -> GameState.getInstance().act(Action.KeyPress0);
                    case KeyEvent.VK_1      -> GameState.getInstance().act(Action.KeyPress1);
                    case KeyEvent.VK_2      -> GameState.getInstance().act(Action.KeyPress2);
                    case KeyEvent.VK_3      -> GameState.getInstance().act(Action.KeyPress3);
                    case KeyEvent.VK_4      -> GameState.getInstance().act(Action.KeyPress4);
                    case KeyEvent.VK_5      -> GameState.getInstance().act(Action.KeyPress5);
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
                /*
                 * Called when the system receives a key. Required to be implemented,
                 * but we don't need it.
                 */
            }

            @Override
            public void keyReleased(KeyEvent e) {
                /*
                 * Called when the user releases a key. Required to be implemented,
                 * but we don't need it.
                 */
            }
        });
    }

    /**
     * The values of the GameState the last time the board was redrawn.
     */
    private int prevLevelNumber = -1;
    private int prevHP = -1;
    private int prevGold = -1;
    private boolean prevHasKey = false;

    /**
     * Whether or not either the level has changed since the last render, or whether
     * the HUD has updated since the last render. Gets set by calculateChanges()
     */
    private boolean levelChanged = false;
    private boolean hudChanged = false;

    /**
     * @author Alex Boxall
     * @return whether the HUD has updated since the last render
     */
    private boolean didHUDChange() {
        return hudChanged;
    }

    /**
     * @author Alex Boxall
     * @return whether the level has changed since the last render
     */
    private boolean didLevelChange() {
        return levelChanged;
    }

    /**
     * Determines whether the level or HUD has changed since the last time the screen
     * was redrawn. Should be called at the start of the redraw routine, and then
     * didHUDChange and didLevelChange will work correctly.
     *
     * @author Alex Boxall
     */
    private void calculateChanges() {
        hudChanged = false;
        levelChanged = false;

        if (prevLevelNumber != GameState.getInstance().levelNumber) {
            hudChanged = true;
            levelChanged = true;
            prevLevelNumber = GameState.getInstance().levelNumber;
        }

        if (prevHP != GameState.getInstance().player.getHp()) {
            hudChanged = true;
            prevHP = GameState.getInstance().player.getHp();
        }

        if (prevGold != GameState.getInstance().getGold()) {
            hudChanged = true;
            prevGold = GameState.getInstance().getGold();
        }

        if (prevHasKey != GameState.getInstance().hasKey) {
            hudChanged = true;
            prevHasKey = GameState.getInstance().hasKey;
        }
    }

    /**
     * Given a Tile, it gives the colour that it should be rendered as.
     *
     * @author Alex Boxall
     *
     * @param tile The tile type.
     * @return The Color that the tile should be rendered as.
     */
    private Color getTileColour(Tile tile) {
        return switch (tile) {
            case Empty -> Color.WHITE;
            case Wall -> Color.BLACK;
            case EmptyWithKey -> Color.WHITE;
            case Staircase -> Color.DARK_GRAY;
            case Shop -> Color.GRAY;
            case Chest -> new Color(153, 102, 0);
        };
    }

    /**
     * Returns how many tiles across the current board is.
     *
     * @author Alex Boxall
     *
     * @return The width, in tiles.
     */
    private int getBoardWidth() {
        return GameState.getInstance().board.getWidth();
    }

    /**
     * Returns how many tiles tall the current board is.
     *
     * @author Alex Boxall
     *
     * @return The height, in tiles.
     */
    private int getBoardHeight() {
        return GameState.getInstance().board.getHeight();
    }

    /**
     * Returns the size of a tile, in pixels. A tile is square, and so this value
     * is both the width and height of a tile.
     *
     * @author Alex Boxall
     *
     * @return The width/height of a tile, in pixels.
     */
    private int getTileSize() {
        int maxTilesWide = (WINDOW_WIDTH - 200) / getBoardWidth();
        int maxTilesHigh = WINDOW_HEIGHT / (getBoardHeight() + 2);
        return Math.max(4, Math.min(maxTilesHigh, maxTilesWide));
    }

    /**
     * Given a tile's x location, returns its x coordinate it should be drawn in pixels.
     *
     * @author Alex Boxall
     *
     * @param x The x coordinate in tiles
     * @param tileSize The width/height of a tile, in pixels
     * @return The x coordinate in pixels of the tile
     */
    private int getPixelXFromTile(int x, int tileSize) {
        return x * tileSize + (WINDOW_WIDTH - getBoardWidth() * tileSize) / 2;
    }

    /**
     * Given a tile's y location, returns its y coordinate it should be drawn in pixels.
     *
     * @author Alex Boxall
     *
     * @param y The x coordinate in tiles
     * @param tileSize The width/height of a tile, in pixels
     * @return The y coordinate in pixels of the tile
     */
    private int getPixelYFromTile(int y, int tileSize) {
        return y * tileSize + (WINDOW_HEIGHT - getBoardHeight() * tileSize) / 2;
    }

    /**
     * Draws the graphic for the key on the screen at the given position
     *
     * @author Alex Boxall
     *
     * @param g The Graphics object we use to draw to the screen.
     * @param px The x pixel to draw the key
     * @param py The x pixel to draw the key
     * @param tileSize The width/height of a tile, in pixels
     */
    public void drawKey(Graphics g, int px, int py, int tileSize) {
        /*
         * Draw the key as an orange circle.
         */
        g.setColor(Color.ORANGE);
        g.fillOval(
                px + tileSize / 4,
                py + tileSize / 4,
                tileSize / 2,
                tileSize / 2);
    }

    /**
     * Draws a tile.
     *
     * @author Alex Boxall
     *
     * @param g The Graphics object we use to draw to the screen.
     * @param x The x position of tile, as a tile offset.
     * @param y The y position of tile, as a tile offset.
     * @param tile The tile type.
     */
    private void renderTile(Graphics g, int x, int y, Tile tile) {
        int tileSize = getTileSize();
        int px = getPixelXFromTile(x, tileSize);
        int py = getPixelYFromTile(y, tileSize);

        /*
         * Render the background colour of the tile.
         */
        g.setColor(getTileColour(tile));
        g.fillRect(px, py, tileSize, tileSize);

        /*
         * Add some prettiness to some of the tiles so it doesn't just look like a
         * bunch of strangely coloured squares.
         */
        if (tile == Tile.Shop) {
            /*
             * Put some pretty blunt 'SHOP' text on the tile.
             */
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            int textWidth = g.getFontMetrics().stringWidth("SHOP");
            g.drawString("SHOP", px + (tileSize - textWidth) / 2, py + tileSize / 2);

        } else if (tile == Tile.Staircase) {
            /*
             * The staircase will be shown as a grey block if you don't have the key,
             * and will turn into a staircase.
             */
            if (GameState.getInstance().hasKey) {
                g.setColor(Color.WHITE);
                g.fillRect(px, py, tileSize / 4, 3 * tileSize / 4);
                g.fillRect(px + tileSize / 4, py, tileSize / 4, 2 * tileSize / 4);
                g.fillRect(px + tileSize / 2, py, tileSize / 4, tileSize / 4);
            }

        } else if (tile == Tile.EmptyWithKey) {
            drawKey(g, px, py, tileSize);
        }
    }

    /**
     * Draws the player.
     *
     * @author Alex Boxall
     *
     * @param g The Graphics object we use to draw to the screen.
     * @param x The x position of the player, as a tile offset.
     * @param y The y position of the player, as a tile offset.
     */
    private void renderPlayer(Graphics g, int x, int y) {
        int tileSize = getTileSize();
        int px = getPixelXFromTile(x, tileSize) + tileSize / 4;
        int py = getPixelYFromTile(y, tileSize) + tileSize / 4;
        tileSize /= 2;

        Direction direction = GameState.getInstance().player.getDirection();

        g.setColor(Color.BLUE);

        Polygon player = new Polygon();
        switch (direction) {
            case UP -> {
                player.addPoint(px + tileSize / 2, py);
                player.addPoint(px, py + tileSize);
                player.addPoint(px + tileSize, py + tileSize);
            }
            case DOWN -> {
                player.addPoint(px + tileSize / 2, py + tileSize);
                player.addPoint(px, py);
                player.addPoint(px + tileSize, py);
            }
            case LEFT -> {
                player.addPoint(px, py + tileSize / 2);
                player.addPoint(px + tileSize, py);
                player.addPoint(px + tileSize, py + tileSize);
            }
            case RIGHT -> {
                player.addPoint(px + tileSize, py + tileSize / 2);
                player.addPoint(px, py);
                player.addPoint(px, py + tileSize);
            }
        }
        g.fillPolygon(player);
    }

    /**
     * Draws an enemy.
     *
     * @author Alex Boxall
     *
     * @param g The Graphics object we use to draw to the screen.
     * @param x The x position of the enemy, as a tile offset.
     * @param y The y position of the enemy, as a tile offset.
     */
    private void renderEnemy(Graphics g, int x, int y) {
        int tileSize = getTileSize();
        int px = getPixelXFromTile(x, tileSize);
        int py = getPixelYFromTile(y, tileSize);

        g.setColor(Color.RED);
        g.fillOval(
                px + tileSize / 4,
                py + tileSize / 4,
                tileSize / 2,
                tileSize / 2);

        // Add text onto enemy to display health
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        String health = String.valueOf(GameState.getInstance().getActorAt(x,y).hp);

        // Display the health depending on how many characters there are
        switch (health.length()) {
            case 1:
                g.drawString(health,px + tileSize/3 ,py + tileSize/2 + 6);
                break;
            case 2:
                g.drawString(health,px + tileSize/3 + 3 ,py + tileSize/2 + 6);
                break;
            case 3:
                g.drawString(health,px + tileSize/3 - 2,py + tileSize/2 + 6);
                break;
        }
    }

    /**
     * Draws the heads-up display (HUD). Shows the level number, the player's health,
     * and inventory.
     *
     * @author Alex Boxall
     */
    private void renderHUD(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));

        g.drawString("Level " + (GameState.getInstance().levelNumber + 1), 30, 60);
        g.drawString("HP: " + GameState.getInstance().getPlayer().getHp() + "/" + GameState.getInstance().getPlayer().getMaxHealth(), 30, 80);
        g.drawString("Gold: " + GameState.getInstance().getGold(), 30, 100);

        if (GameState.getInstance().hasKey) {
            drawKey(g, 25, 100, 64);
        }
    }

    /**
     * Draws a specified attack animation onscreen.
     *
     * @author Alex Boxall
     * @param g The graphics object
     * @param aa The attack animation to draw
     */
    private void renderAttackAnimation(Graphics g, AttackAnimation aa) {
        g.setColor(aa.enemy ? Color.RED : Color.BLUE);
        g.fillRect(aa.x, aa.y, aa.width, aa.height);
    }

    /**
     * Adds a line to show that a melee attack has taken place. May be called by an enemy or a player.
     *
     * @param actor The actor that is making the attack
     * @param direction The direction the actor is attacking (enemies attack toward the player even though
     *                  they might not be facing that direction.
     * @param enemy True if the enemy is making an attack, otherwise false for the player.
     */
    public void addMeleeAttackAnimation(Actor actor, Direction direction, boolean enemy) {
        int tileSize = getTileSize();
        int px = getPixelXFromTile(actor.getX(), tileSize);
        int py = getPixelYFromTile(actor.getY(), tileSize);

        attackAnimations.add(switch (direction) {
            case UP     -> new AttackAnimation(px + tileSize / 2 - 2, py - tileSize / 2, 4, tileSize, enemy);
            case DOWN   -> new AttackAnimation(px + tileSize / 2 - 2, py + tileSize / 2, 4, tileSize, enemy);
            case LEFT   -> new AttackAnimation(px - tileSize / 2, py + tileSize / 2 - 2, tileSize, 4, enemy);
            case RIGHT  -> new AttackAnimation(px + tileSize / 2, py + tileSize / 2 - 2, tileSize, 4, enemy);
        });
    }

    /**
     * Adds a line to show that a ranged attack has taken place. Should be called when the attack is made.
     *
     * @author Alex Boxall
     *
     * @param player The player making the attack
     * @param minX The leftmost tile that is being attacked
     * @param minY The topmost tile that is being attacked
     * @param size How long (either wide or high, depending on direction) the attack is.
     */
    public void addRangedAttackAnimation(Player player, int minX, int minY, int size) {
        int tileSize = getTileSize();
        boolean horizontal = player.getDirection() == Direction.LEFT || player.getDirection() == Direction.RIGHT;
        int px = getPixelXFromTile(minX, tileSize) + tileSize / 2;
        int py = getPixelYFromTile(minY, tileSize) + tileSize / 2;
        int trueSize = size * getTileSize() - getTileSize() / 2;

        if (player.getDirection() == Direction.LEFT) {
            px += getTileSize() / 2;
        }
        if (player.getDirection() == Direction.UP) {
            py += getTileSize() / 2;
        }

        attackAnimations.add(new AttackAnimation(px - (horizontal ? 0 : 2), py - (horizontal ? 2 : 0), horizontal ? trueSize : 4, horizontal ? 4 : trueSize, false));
    }

    /**
     * Draws the game, when we are in gameplay mode (as opposed to e.g., in the inventory
     * or in a shop).
     *
     * @author Alex Boxall
     *
     * @param g The graphics object
     */
    private void renderGameplay(Graphics g) {
        GameState state = GameState.getInstance();

        if (state.board == null) {
            return;
        }

        /*
         * Render all of the tiles in the game board. The key is also technically
         * represented as a type of tile, so that is also handled here.
         */
        for (int y = 0; y < state.board.getHeight(); ++y) {
            for (int x = 0; x < state.board.getWidth(); ++x) {
                renderTile(g, x, y, state.board.getTile(x, y));
            }
        }

        /*
         * Now show all of the actors. Show the player on top of enemies.
         */
        for (Enemy e: state.enemies) {
            renderEnemy(g, e.x, e.y);
        }
        renderPlayer(g, state.player.x, state.player.y);

        /*
         * Draw the attack animations over the top of everything else.
         */
        for (AttackAnimation aa: attackAnimations) {
            renderAttackAnimation(g, aa);
        }
    }

    /**
     * Used to store the positions and sizes of attack animations to be drawn onscreen.
     * An attack animation is simply at thin rectangle that looks like either a horizontal
     * or vertical line.
     *
     * @author Alex Boxall
     */
    static class AttackAnimation {
        /*
         * Coordinates in pixels of the top left corner of the rectangle to draw.
         */
        int x;
        int y;

        /*
         * The dimensions, in pixels.
         */
        int width;
        int height;

        /*
         * Set to true if the enemy is making the attack, otherwise set to false for player-based
         * attacks.
         */
        boolean enemy;

        AttackAnimation(int x, int y, int width, int height, boolean enemy) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.enemy = enemy;
        }
    }

    /**
     * All of the active attack animations to be drawn when the screen gets repainted.
     */
    ArrayList<AttackAnimation> attackAnimations = new ArrayList<>();

    /**
     * Called at the start of a player's turn to remove any attack animations from the screen. This will
     * not cause the screen to update - instead that will occur next time it is redrawn.
     */
    public void removeAttackAnimations() {
        attackAnimations.clear();
    }

    /**
     * Used to perform a fade-to-block in renderGameOver(). The higher this value, the darker the screen.
     * Should range between 0 and 25. Gets reset to 0 on the title screen.
     */
    private int gameOverFadeoutCount = 0;

    /**
     * A Timer object used in renderGameOver() to perform the fade-to-black. Gets initialised on game
     * over.
     */
    private Timer gameOverFadeoutTimer;

    /**
     * The screen to be displayed when the player dies (i.e. loses all of their health).
     *
     * @author Alex Boxall
     *
     * @param g The graphics object
     */
    private void renderGameOver(Graphics g) {
        if (gameOverFadeoutCount == 0) {
            /*
             * Start a timer for the fade to black that forces the game-over screen to be redrawn
             * frequently. Each time, the counter is incremented, so the screen will get darker.
             */
            ++gameOverFadeoutCount;
            renderGameplay(g);
            gameOverFadeoutTimer = new Timer(50, (e) -> {
                renderEverything();
            });
            gameOverFadeoutTimer.setRepeats(true);
            gameOverFadeoutTimer.start();

        } else if (gameOverFadeoutCount < 25) {
            /*
             * Draw the fade-to-black, based on how far through the effect we are. Draws a transparent
             * black rectangle over the top of the regular game screen.
             */
            ++gameOverFadeoutCount;
            renderGameplay(g);
            g.setColor(new Color(0, 0, 0, 16 + gameOverFadeoutCount * 8));
            g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        } else if (gameOverFadeoutCount == 25) {
            /*
             * The fade-to-black is black enough that it's time to actually show the game over screen.
             * Stop the timer to stop any additional updates.
             */
            gameOverFadeoutTimer.setRepeats(false);
            gameOverFadeoutTimer.stop();

            String errorMessage = String.format("GAME OVER!");
            String continueMessage = String.format("Press ENTER to return to the title screen.");

            g.setColor(Color.BLACK);
            g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 100));
            g.drawString(errorMessage, (WINDOW_WIDTH - g.getFontMetrics().stringWidth(errorMessage)) / 2, WINDOW_HEIGHT / 3);

            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.drawString(continueMessage, (WINDOW_WIDTH - g.getFontMetrics().stringWidth(continueMessage)) / 2, WINDOW_HEIGHT * 2 / 3);
        }
    }

    /**
     * A screen that appears when the user beats the game.
     *
     * @author Alex Boxall
     *
     * @param g The graphics object
     */
    private void renderEnding(Graphics g) {
        String errorMessage = "YOU WIN!";

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 72));
        g.drawString(errorMessage, (WINDOW_WIDTH - g.getFontMetrics().stringWidth(errorMessage)) / 2, WINDOW_HEIGHT / 2);
    }

    /**
     * Draws a screen of text on a plain background. Useful for shops, chests, and messages.
     * Handles newlines in the text correctly.
     *
     * @author Alex Boxall
     *
     * @param g The graphics object
     * @param background The colour of the background
     * @param foreground The colour of the text
     * @param text The text to draw.
     */
    private void renderTextScreen(Graphics g, Color background, Color foreground, String text) {
        g.setColor(background);
        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        g.setColor(foreground);
        g.setFont(new Font("Arial", Font.BOLD, 24));

        int ypos = WINDOW_HEIGHT / 3;
        for (String line: text.split("\n")) {
            g.drawString(line, WINDOW_WIDTH / 3, ypos);
            ypos += 27;
        }
    }

    /**
     * Used to display full screen messages to the player that can be dismissed with any key.
     * A message is displayed if the value of 'messageScreenText' is non-null.
     */
    Color messageScreenBg;
    Color messageScreenFg;
    String messageScreenText;

    /**
     * Causes the game to show a full-screen message on a plain-coloured background. The message will
     * remain onscreen until the next key is pressed. The GameMode will be set to the specified value
     * after the message is dismissed. No actions will be processed during the display of the message,
     * as it bypasses sending normal actions.
     *
     * @param background The background colour of the screen when the message is active.
     * @param foreground The colour of the message text.
     * @param message The message to display.
     * @param mode The GameMode to return to. If this is NULL, it will be the mode the game was in before
     *             this call.
     */
    public void displayMessageScreen(Color background, Color foreground, String message, GameState.GameMode mode) {
        /*
         * We're fine to set GameMode early, as the game will not be playing while the message is active.
         */
        if (mode != null) {
            GameState.getInstance().setGameMode(mode);
        }

        messageScreenText = message;
        messageScreenBg = background;
        messageScreenFg = foreground;

        renderEverything();
    }

    /**
     * Draws the shop screen when the player is in a shop.
     * @author Alex Boxall
     * @param g The graphics object
     */
    private void renderShop(Graphics g) {
        renderTextScreen(g, Color.LIGHT_GRAY, Color.BLACK, GameState.getInstance().getShop().printInventory());
    }

    /**
     * Draws the inventory screen when the player has their inventory open.
     * @author Alex Boxall
     * @param g The graphics object
     */
    private void renderInventory(Graphics g) {
        renderTextScreen(g, Color.BLACK, Color.LIGHT_GRAY,
                "Current Melee Weapon = " + GameState.getInstance().getCurrentMeleeWeapon().getName()
                        + " Damage = " + GameState.getInstance().getCurrentMeleeWeapon().getDamage()
                        + "\n"
                        + "Current Ranged Weapon = " + GameState.getInstance().getCurrentRangedWeapon().getName()
                        + " Damage = " + GameState.getInstance().getCurrentRangedWeapon().getDamage()
                        + "\n"
                        + GameState.getInstance().printInventory());
    }

    /**
     * Draws the chest screen when the player is in a chest.
     *
     * @author Will Baird
     * @author Alex Boxall
     *
     * @param g The graphics object
     */
    private void renderChest(Graphics g) {
        renderTextScreen(g, Color.DARK_GRAY, Color.BLACK, GameState.getInstance().getChest().printLoot());
    }

    /**
     * Draws the game's title screen.
     *
     * @author Alex Boxall
     *
     * @param g The graphics object
     */
    private void renderTitleScreen(Graphics g) {
        /*
         * So the next game over will work.
         */
        gameOverFadeoutCount = 0;

        removeAttackAnimations();

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 48));

        String titleMsg = "REALLY AWESOME COMP2120 GAME";

        /*
         * Need to save this here, as changing the font to the small one will change the font metrics.
         */
        int titleMsgWidth = g.getFontMetrics().stringWidth(titleMsg);
        g.drawString(titleMsg, (WINDOW_WIDTH - titleMsgWidth) / 2, WINDOW_HEIGHT / 3);

        g.fillRect((WINDOW_WIDTH - titleMsgWidth) / 2, WINDOW_HEIGHT / 3 + 15, g.getFontMetrics().stringWidth("REALLY"), 5);

        g.setFont(new Font("Arial", Font.BOLD, 12));
        g.drawString("TM", (WINDOW_WIDTH + titleMsgWidth) / 2, WINDOW_HEIGHT / 3 - 32);

        g.setColor(Color.LIGHT_GRAY);


        g.setFont(new Font("Courier New", Font.BOLD, 30));
        String instructionMsg  = "ENTER     Start new game";
        String instructionMsg2 = "L         Load existing game";
        String instructionMsg3 = withArrow("1         Easy Difficulty", "easy");
        String instructionMsg4 = withArrow("2         Medium Difficulty", "normal");
        String instructionMsg5 = withArrow("3         Hard Difficulty", "hard");
        String instructionMsg6 = withArrow("4         Insane Difficulty", "insane");
        int instructionMsgWidth = g.getFontMetrics().stringWidth(instructionMsg);
        String arrow = "->  ";
        int arrowWidth = g.getFontMetrics().stringWidth(arrow);
        g.drawString(instructionMsg, (WINDOW_WIDTH - instructionMsgWidth) / 2, 2 * WINDOW_HEIGHT / 3);
        g.drawString(instructionMsg2, (WINDOW_WIDTH - instructionMsgWidth) / 2, 2 * WINDOW_HEIGHT / 3 + 40);
        g.drawString(instructionMsg3, (WINDOW_WIDTH - instructionMsgWidth - arrowWidth) / 2, 2 * WINDOW_HEIGHT / 3 + 80);
        g.drawString(instructionMsg4, (WINDOW_WIDTH - instructionMsgWidth - arrowWidth) / 2, 2 * WINDOW_HEIGHT / 3 + 120);
        g.drawString(instructionMsg5, (WINDOW_WIDTH - instructionMsgWidth - arrowWidth) / 2, 2 * WINDOW_HEIGHT / 3 + 160);
        g.drawString(instructionMsg6, (WINDOW_WIDTH - instructionMsgWidth - arrowWidth) / 2, 2 * WINDOW_HEIGHT / 3 + 200);
    }

    // Helper function to add the arrow based on selected difficulty
    String withArrow(String label, String difficulty) {
        if (difficulty.equals(GameState.getInstance().difficulty)) {
            return "->" + label;
        }
       return "  " + label;
    }

    /**
     * Redraws the window. Will be automatically called by the system/window manager when
     * required.
     *
     * @author Alex Boxall
     *
     * @param g the specified Graphics window
     */
    @Override
    public void paint(Graphics g) {
        GameState state = GameState.getInstance();

        if (messageScreenText != null) {
            renderTextScreen(g, messageScreenBg, messageScreenFg, messageScreenText);
            return;
        }

        calculateChanges();

        if (didLevelChange()) {
            /*
             * Need to clear the entire screen as the game board may change in size/position.
             */
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
            renderHUD(g);

        } else if (didHUDChange()) {
            /*
             * Need to clear the old HUD before drawing the new one.
             */
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, getPixelXFromTile(0, getTileSize()) + 100, 250);
            renderHUD(g);
        }

        switch (GameState.getInstance().getGameMode()) {
            case Gameplay -> renderGameplay(g);
            case TitleScreen -> renderTitleScreen(g);
            case Shop -> renderShop(g);
            case Chest -> renderChest(g);
            case Inventory -> renderInventory(g);
            case GameOverScreen -> renderGameOver(g);
            case Ending -> renderEnding(g);
        }
    }

    /**
     * Causes the game to be re-rendered.
     *
     * @author Alex Boxall
     */
    public void render() {
        /*
         * Tells the window manager that the entire window is dirty, and so it needs to be repainted.
         */
        repaint();
    }

    /**
     * Forcefully redraws the entire screen.
     */
    public void renderEverything() {
        /*
         * This means everything will get redrawn, as it thinks we have moved to a different level.
         */
        prevHP = -2;
        prevLevelNumber = -2;

        repaint();
    }

}
