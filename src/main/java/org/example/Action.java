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
    UsePotion;

    public boolean isMove() {
        return this == MoveLeft || this == MoveRight || this == MoveDown || this == MoveUp;
    }

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
