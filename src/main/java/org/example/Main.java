package org.example;

import org.example.configuration.GameConfiguration;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main {
    public static int testFunction() {
        return 1;
    }

    /**
     * Sets the GameState for demonstrations. Should be removed once the rest of the game is written.
     *
     * @author Alex Boxall
     */
    static void setDemoState() {
        Board b = new Board();
        b.setAllTiles(new Tile[][] {
                new Tile[]{Tile.Wall , Tile.Wall , Tile.Wall , Tile.Wall , Tile.Wall , Tile.Wall , Tile.Wall , Tile.Wall },
                new Tile[]{Tile.Wall , Tile.Empty, Tile.Empty, Tile.Empty, Tile.Chest, Tile.Empty, Tile.Shop, Tile.Wall },
                new Tile[]{Tile.Wall , Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Wall },
                new Tile[]{Tile.Wall , Tile.Wall , Tile.Wall , Tile.Wall , Tile.Wall , Tile.Empty, Tile.Empty, Tile.Wall },
                new Tile[]{Tile.Wall , Tile.Empty, Tile.Empty, Tile.Staircase, Tile.Wall , Tile.Empty, Tile.Empty, Tile.Wall },
                new Tile[]{Tile.Wall , Tile.Empty, Tile.Wall , Tile.Wall , Tile.Wall , Tile.Empty, Tile.Empty, Tile.Wall },
                new Tile[]{Tile.Wall , Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Wall },
                new Tile[]{Tile.Wall , Tile.Wall , Tile.Wall , Tile.Wall , Tile.Wall , Tile.Wall , Tile.Wall , Tile.Wall },
        });
        GameState.getInstance().board = b;
        GameState.getInstance().player = new Player(1, 1, 30);
        GameState.getInstance().enemies = new ArrayList<>();
        GameState.getInstance().enemies.add(new Enemy(4, 1, 5, 10));
        GameState.getInstance().enemies.add(new Enemy(5, 4, 5, 10));
        GameState.getInstance().enemies.add(new Enemy(6, 6, 5, 10));
    }

    /**
     * Sets the GameState for a new game.
     * @author Jake Tammaro
     * @throws Exception
     */
    static void initializeGameState() throws Exception {
        GameConfiguration newConfig = new GameConfiguration("gameConfig.json");
        GameState.getInstance().levelNumber = 0;
        newConfig.initializeGame(newConfig.getBoardLayout(0));
        GameState.getInstance().setGameConfiguration(newConfig);
    }


    public static void main(String[] args) throws Exception {
        //setDemoState();

        try {
            initializeGameState();

        } catch (Exception e) {
            e.printStackTrace();
        }


        if (GraphicsEnvironment.isHeadless()) {
            RendererConsole cons = (RendererConsole) Renderer.getInstance();

            cons.renderEverything();

            while (true) {
                System.out.print("> ");
                cons.run();
            }

        } else {
            /*
             * Show the window. We do this in a SwingUtilities.invokeLater so that main doesn't return
             * and we enter the window's event loop.
             */
            SwingUtilities.invokeLater(() -> {
                RendererGUI gui = (RendererGUI) Renderer.getInstance();
                gui.setVisible(true);
            });
        }
    }
}
