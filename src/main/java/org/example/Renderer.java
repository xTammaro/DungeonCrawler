package org.example;

import javax.swing.*;
import java.awt.*;

/**
 * Responsible for visually showing the game to the player. Also responsible for catching keyboard
 * inputs, and converting them into Actions the player can make.
 */
public interface Renderer {
    /**
     * Returns the singleton Renderer.
     *
     * @return The single Renderer instance.
     */
    static Renderer getInstance() {
        if (GraphicsEnvironment.isHeadless()) {
            return RendererConsole.getInstance();
        } else {
            return RendererGUI.getInstance();
        }
    }

    /**
     * Adds a line to show that a melee attack has taken place. May be called by an enemy or a player.
     *
     * @param actor The actor that is making the attack
     * @param direction The direction the actor is attacking (enemies attack toward the player even though
     *                  they might not be facing that direction.
     * @param enemy True if the enemy is making an attack, otherwise false for the player.
     */
    void addMeleeAttackAnimation(Actor actor, Direction direction, boolean enemy);

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
    void addRangedAttackAnimation(Player player, int minX, int minY, int size);

    /**
     * Used to store the positions and sizes of attack animations to be drawn onscreen.
     * An attack animation is simply at thin rectangle that looks like either a horizontal
     * or vertical line.
     *
     * @author Alex Boxall
     */
    class AttackAnimation {
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
     * Called at the start of a player's turn to remove any attack animations from the screen. This will
     * not cause the screen to update - instead that will occur next time it is redrawn.
     */
    void removeAttackAnimations();

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
    void displayMessageScreen(Color background, Color foreground, String message, GameState.GameMode mode);

    /**
     * Causes the game to be re-rendered.
     *
     * @author Alex Boxall
     */
    void render();

    /**
     * Forcefully redraws the entire screen.
     */
    void renderEverything();
}
