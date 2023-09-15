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
    StartGame;

    /**
     * @author Tal Shy-Tielen
     * @return true if action is a movement and false otherwise.
     */
    public boolean isMove() {
        return this == MoveLeft || this == MoveRight || this == MoveDown || this == MoveUp;
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
