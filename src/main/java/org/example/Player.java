package org.example;

/**
 * The playable character. Stores only their position and health, the rest (e.g. inventory) is stored
 * as part of the GameState.
 */
public class Player extends Actor {

    private int gold;


    /**
     * The player constructor.
     *
     * @author Alex Boxall
     *
     * Initial gold value is set to zero.
     * @param x The initial X position.
     * @param y The initial Y position.
     * @param hp The initial health.
     */
    public Player(int x, int y, int hp) {
        super(x, y, hp);
        this.gold = 0;
    }

    /**
     * This gets called when the Player dies, i.e. on a game over.
     *
     * @author Alex Boxall
     */
    @Override
    void onZeroHealth() {
        GameState.getInstance().setGameMode(GameState.GameMode.GameOverScreen);
    }

    /**
     * Called when the actor uses a sword attack.
     * This is also called when the enemies attack the player
     * @author Alex Boxall
     * @author Tal Shy-Tielen
     */
    void useSword() {
        int x = this.x;
        int y = this.y;
        switch (getDirection()) {
            case UP     -> y--;
            case DOWN   -> y++;
            case LEFT   -> x--;
            case RIGHT  -> x++;
        }

        if (GameState.getInstance().isOccupied(x,y)) {
            GameState.getInstance().getActorAt(x,y).takeDamage(attackDamage());
        }

        Renderer.getInstance().addMeleeAttackAnimation(this, this.getDirection(), false);
    }

    /**
     * Called when the actor uses a gun attack. Attacks all enemies in its path until a wall is reached.
     *
     * @author Alex Boxall
     */
    void useGun() {
        int x = this.x;
        int y = this.y;

        int size = 0;

        while (GameState.getInstance().board.getTile(x, y) != Tile.Wall) {
            switch (getDirection()) {
                case UP     -> y--;
                case DOWN   -> y++;
                case LEFT   -> x--;
                case RIGHT  -> x++;
            }

            ++size;

            if (GameState.getInstance().isOccupied(x,y)) {
                GameState.getInstance().getActorAt(x,y).takeDamage(attackDamage());
            }
        }

        int minX = getDirection() == Direction.LEFT ? x : this.x;
        int minY = getDirection() == Direction.UP ? y : this.y;

        Renderer.getInstance().addRangedAttackAnimation(this, minX, minY, size);
    }

    /**
     * Calculates the attack damage.
     * This should use hte player's weapon
     * @return Attack damage
     * @author Tal Shy-Tielen
     */
    public int attackDamage() {
        return 50;
    }

    /**
     * @author Tal Shy-Tielen
     * Function should be called whenever the player receives gold.
     * @param x the amount of gold recieved.
     */
    public void receiveGold(int x) {
        this.gold += x;
    }

    /**
     * @author Tal Shy-Tielen
     * Checks whether the player can buy an item.
     * @param x price of item.
     * @return true if the player can afford it, false otherwise.
     */
    public boolean canBuy(int x) {
        return this.gold >= x;
    }

    /**
     * @author Tal Shy-Tielen
     * Buys an item at a given price.
     * @param x price of the item.
     */
    public void buy(int x) {
        this.gold -= x;
    }
}
