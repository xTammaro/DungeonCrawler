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
public class Renderer extends JFrame {
    /**
     * Holds the singleton object of the renderer.
     */
    private static Renderer instance;

    /**
     * Returns the singleton Renderer.
     *
     * @return The single Renderer instance.
     */
    public static Renderer getInstance() {
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
     */
    private Renderer() {
        setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setTitle("COMP2120 Assignment 3");

        addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
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
                    case KeyEvent.VK_X      -> GameState.getInstance().act(Action.UseSword);
                    case KeyEvent.VK_Q      -> GameState.getInstance().act(Action.UsePotion);
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
     * Given a Tile, it gives the colour that it should be rendered as.
     *
     * @param tile The tile type.
     * @return The Color that the tile should be rendered as.
     */
    private Color getTileColour(Tile tile) {
        return switch (tile) {
            case Empty -> Color.YELLOW;
            case Wall -> Color.BLACK;
            case EmptyWithKey -> Color.ORANGE;
            case Staircase -> Color.GRAY;
            case Shop -> Color.RED;
        };
    }

    /**
     * Returns how many tiles across the current board is.
     *
     * @return The width, in tiles.
     */
    private int getBoardWidth() {
        return GameState.getInstance().board.tiles[0].length;
    }

    /**
     * Returns how many tiles tall the current board is.
     *
     * @return The height, in tiles.
     */
    private int getBoardHeight() {
        return GameState.getInstance().board.tiles.length;
    }

    /**
     * Returns the size of a tile, in pixels. A tile is square, and so this value
     * is both the width and height of a tile.
     *
     * @return The width/height of a tile, in pixels.
     */
    private int getTileSize() {
        int maxTilesWide = WINDOW_WIDTH / (getBoardWidth() + 2);
        int maxTilesHigh = WINDOW_HEIGHT / (getBoardHeight() + 2);
        return Math.max(4, Math.min(maxTilesHigh, maxTilesWide));
    }

    /**
     * Given a tile's x location, returns its x coordinate it should be drawn in pixels.
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
     * @param y The x coordinate in tiles
     * @param tileSize The width/height of a tile, in pixels
     * @return The y coordinate in pixels of the tile
     */
    private int getPixelYFromTile(int y, int tileSize) {
        return y * tileSize + (WINDOW_HEIGHT - getBoardHeight() * tileSize) / 2;
    }

    /**
     * Draws a tile.
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

        g.setColor(getTileColour(tile));
        g.fillRect(px, py, tileSize, tileSize);
    }

    /**
     * Draws the player.
     *
     * @param g The Graphics object we use to draw to the screen.
     * @param x The x position of the player, as a tile offset.
     * @param y The y position of the player, as a tile offset.
     */
    private void renderPlayer(Graphics g, int x, int y) {
        int tileSize = getTileSize();
        int px = getPixelXFromTile(x, tileSize);
        int py = getPixelYFromTile(y, tileSize);

        g.setColor(Color.BLUE);
        g.fillOval(px, py, tileSize, tileSize);
    }

    /**
     * Draws an enemy.
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
        g.fillOval(px, py, tileSize, tileSize);
    }

    /**
     * Redraws the window. Will be automatically called by the system/window manager when
     * required.
     *
     * @param g the specified Graphics window
     */
    @Override
    public void paint(Graphics g) {
        super.paintComponents(g);

        GameState state = GameState.getInstance();

        if (state.board == null || state.board.tiles == null) {
            return;
        }

        for (int y = 0; y < state.board.tiles.length; ++y) {
            for (int x = 0; x < state.board.tiles[y].length; ++x) {
                renderTile(g, x, y, state.board.tiles[y][x]);
            }
        }

        renderPlayer(g, state.player.x, state.player.y);

        for (Enemy e: state.enemies) {
            renderEnemy(g, e.x, e.y);
        }
    }

    /**
     * Causes the game to be re-rendered.
     */
    public void render() {
        /*
         * Tells the window manager that the entire window is dirty, and so it needs to be repainted.
         */
        invalidate();
    }

    /**
     * Sets the GameState for demonstrations. Should be removed once the rest of the game is written.
     */
    private void setDemoState() {
        Board b = new Board();
        b.tiles = new Tile[][] {
                new Tile[]{Tile.Wall , Tile.Wall , Tile.Wall , Tile.Wall , Tile.Wall , Tile.Wall , Tile.Wall , Tile.Wall },
                new Tile[]{Tile.Wall , Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Shop, Tile.Wall },
                new Tile[]{Tile.Wall , Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Wall },
                new Tile[]{Tile.Wall , Tile.Wall , Tile.Wall , Tile.Wall , Tile.Wall , Tile.Empty, Tile.Empty, Tile.Wall },
                new Tile[]{Tile.Wall , Tile.Empty, Tile.Empty, Tile.Staircase, Tile.Wall , Tile.Empty, Tile.Empty, Tile.Wall },
                new Tile[]{Tile.Wall , Tile.Empty, Tile.Wall , Tile.Wall , Tile.Wall , Tile.Empty, Tile.Empty, Tile.Wall },
                new Tile[]{Tile.Wall , Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Wall },
                new Tile[]{Tile.Wall , Tile.Wall , Tile.Wall , Tile.Wall , Tile.Wall , Tile.Wall , Tile.Wall , Tile.Wall },
        };
        GameState.getInstance().board = b;
        GameState.getInstance().player = new Player(1, 1, 10);
        GameState.getInstance().enemies = new ArrayList<>();
        GameState.getInstance().enemies.add(new Enemy(4, 1, 5));
        GameState.getInstance().enemies.add(new Enemy(5, 4, 5));
        GameState.getInstance().enemies.add(new Enemy(6, 6, 5));
    }

    public static void main(String[] args) {
        /*
         * Initialise the singleton.
         */
        instance = new Renderer();

        /*
         * Show the window. We do this in a SwingUtilities.invokeLater so that main doesn't return
         * and we enter the window's event loop.
         */
        SwingUtilities.invokeLater(() -> {
            getInstance().setDemoState();
            getInstance().setVisible(true);
        });
    }
}
