package org.example;

/**
 * Responsible for visually showing the game to the player. Also responsible for catching keyboard
 * inputs, and converting them into Actions the player can make.
 */
public class Renderer {
    private Renderer() {

    }

    private static Renderer instance;

    public static Renderer getInstance() {
        if (instance == null) {
            instance = new Renderer();
        }
        return instance;
    }

    /**
     * Redraws the game board with JavaFx.
     */
    void render() {
        Board map = GameState.getInstance().board;
    }
}
