package org.example;


/**
 * Represents an entity that can move around the map, has HP (e.g. the player, an enemy) and can
 * attack. Should be subclassed for the Player and Enemy.
 *
 * TODO: more abstract classes may be needed, e.g. to change the way enemies attack, etc.
 *
 * TODO: many more things can be added, e.g. poison
 */
abstract public class Actor {
    /**
     * The location of the actor from the top-left tile.
     */
    int x;
    int y;

    /**
     * How much health the actor has. They die when it reaches, or goes below zero.
     */
    int hp;

    /**
     * The direction the actor is facing. Gets set to the most recent direction
     * the player moves in.
     */
    private Direction direction = Directon.RIGHT;

    /**
     * Constructor for an Actor. As this is an abstract class, it cannot be
     * directly invoked.
     *
     * @author Alex Boxall
     *
     * @param x The initial X position
     * @param y The initial Y position
     * @param hp The initial health
     */
    Actor(int x, int y, int hp) {
        this.x = x;
        this.y = y;
        this.hp = hp;
    }

    /**
     * Determines whether this actor can move in a certain direction. If there is a wall
     * blocking the way, it should return false.
     * There is also a check, if the player tries to move off the map it will return false.
     * @author Alex Boxall
     * @author Tal Shy-Tielen
     *
     * @param direction The direction to move in.
     * @return Whether the actor can move in this direction.
     */
    boolean canMoveInDirection(Direction direction) {
        int x = this.x;
        int y = this.y;
        switch (direction) {
            case UP  -> y--;
            case RIGHT  -> x++;
            case DOWN  -> y++;
            case LEFT  -> x--;
            default -> {
                return false;
            }
        }
        return (GameState.getInstance().board.getTile(x,y).isMovable());
    }

    /**
     * Moves the actor in a given direction. Assumes that the call to canMoveInDirection has
     * already been made, and that a valid direction is passed in.
     *
     * @author Alex Boxall
     *
     * @param direction The direction to move the player in.
     */
    void moveInDirection(Direction direction) {
        this.direction = direction;

        switch (direction) {
            case UP     -> y--;
            case DOWN   -> y++;
            case LEFT   -> x--;
            case RIGHT  -> x++;
        }
    }

    /**
     * @author Alex Boxall
     * @return The player's current direction.
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Called when the actor uses a sword attack.
     *
     * @author Alex Boxall
     */
    void useSword() {

    }

    /**
     * Called when the actor uses a gun attack.
     *
     * @author Alex Boxall
     */
    void useGun() {

    }

    /**
     * Causes this actor to take a certain amount of damage. If the health reaches zero,
     * the Actor dies.
     *
     * @author Alex Boxall
     *
     * @param attack The health to subtract
     */
    void takeDamage(int attack) {
        hp -= attack;
        if (hp <= 0) {
            onZeroHealth();
        }
    }

    /**
     * Called when this actor dies. It should handle any cleanup (e.g. for enemies, it removes
     * then from the GameState, for the Player it displays a game over screen).
     *
     *
     * @author Alex Boxall
     */
    abstract void onZeroHealth();

    /**
     * Returns the actor's current health.
     * @return The current HP
     */
    public int getHP() {
        return hp;
    }
}
