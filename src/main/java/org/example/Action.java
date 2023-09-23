package org.example;

/**
 * Represents an action that the user could take.
 */
public enum Action {
    MoveLeft,
    MoveRight,
    MoveUp,
    MoveDown,
    UseSword,
    UseGun,
    UsePotion,
    OpenInventory,
    EnterShop,
    EnterChest,
    StartGame,
    KeyPress1,
    KeyPress2,
    KeyPress3,
    KeyPress4,
    KeyPress5;

    /**
     * @author Tal Shy-Tielen
     * @return true if action is a movement and false otherwise.
     */
    public boolean isMove() {
        return this == MoveLeft || this == MoveRight || this == MoveDown || this == MoveUp;
    }

    /**
     * Given an Action, coverts it into the integer corresponding to the number key that was pressed. If the
     * key pressed was not a number, -1 is returned.
     *
     * @author Alex Boxall
     * @return The integer corresponding to the key pressed to generate this action, or -1 if it was non-numeric.
     */
    public int translateToNumeric() {
        return switch (this) {
            case KeyPress1 -> 1;
            case KeyPress2 -> 2;
            case KeyPress3 -> 3;
            case KeyPress4 -> 4;
            case KeyPress5 -> 5;
            default -> -1;
        };
    }

    /**
     * @author Tal Shy-Tielen
     * @return the direction of the move in the Direction form.
     * If the action has no direction, it will return null.
     */
    public Direction getDirection() {
        switch (this) {
            case MoveDown -> {
                return Direction.DOWN;
            }
            case MoveUp -> {
                return Direction.UP;
            }
            case MoveLeft -> {
                return Direction.LEFT;
            }
            case MoveRight -> {
                return Direction.RIGHT;
            }
            default -> {
                return null;
            }
        }
    }
}
