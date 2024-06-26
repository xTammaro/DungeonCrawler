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
    private Direction direction = Direction.RIGHT;

    /**
     * Constructor for an Actor. As this is an abstract class, it cannot be
     * directly invoked.
     *
     * @author Ja
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
     * @param direction Sets the Actor's direction.
     */
    void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Determines whether this actor can move in a certain direction. If there is a wall
     * blocking the way, it should return false.
     * There is also a check, if the player tries to move off the map it will return false.
     * @author Jake Tammaro
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
        return (GameState.getInstance().board.getTile(x,y).isMovable()) && !GameState.getInstance().isOccupied(x, y);
    }

    /**
     * Moves the actor in a given direction. Assumes that the call to canMoveInDirection has
     * already been made, and that a valid direction is passed in.
     *
     * @author Jake Tammaro
     *
     * @param direction The direction to move the player in.
     */
    void moveInDirection(Direction direction) {
        setDirection(direction);
        switch (direction) {
            case UP     -> y--;
            case DOWN   -> y++;
            case LEFT   -> x--;
            case RIGHT  -> x++;
        }
    }

    /**
     * @author Jake Tammaro
     * @return The player's current direction.
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Called when the actor uses a sword attack.
     * This is also called when the enemies attack the player
     * @author Jake Tammaro
     */
    void useSword() {
        int x = this.x;
        int y = this.y;
        switch (direction) {
            case UP     -> y--;
            case DOWN   -> y++;
            case LEFT   -> x--;
            case RIGHT  -> x++;
        }

        if (GameState.getInstance().isOccupied(x,y)) {
            GameState.getInstance().getActorAt(x,y).takeDamage(meleeAttackDamage());
        }
    }


    /**
     * Causes this actor to take a certain amount of damage. If the health reaches zero,
     * the Actor dies.
     *
     * @author Alex Boxall
     *
     * @param attack The health to subtract
     */
    public void takeDamage(int attack) {
        hp -= attack;
        if (hp <= 0) {
            onZeroHealth();
        }
    }

    /**
     * Called when this actor dies. It should handle any cleanup (e.g. for enemies, it removes
     * then from the GameState, for the Player it displays a game over screen).
     *
     * @author Alex Boxall
     */
    abstract void onZeroHealth();

    /**
     * Called when the actor attacks something
     * @return The melee damage the actor does
     * @author Tal Shy-Tielen
     */
    abstract int meleeAttackDamage() ;
    /**
     * Returns the actor's current health.
     * @return The current HP
     */
    public int getHp() {
        return hp;
    }

    /**
     * @param hp sets the actor's hp
     * to a new value
     */
    public void setHp(int hp) {
        this.hp = hp;
    }

    /*
     * @return The actor's x position
     */
    public int getX() {
        return x;
    }

    /*
     * @return The actor's y position
     */
    public int getY() {
        return y;
    }
}
