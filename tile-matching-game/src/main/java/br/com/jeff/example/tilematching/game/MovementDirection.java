package br.com.jeff.example.tilematching.game;

public class MovementDirection {
    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT;
    }

    private Direction xDirection;
    private Direction yDirection;

    public MovementDirection(Position x, Position y) {
        if (x.getX() == y.getX()) {
            if (x.getY() > y.getY()) {
                xDirection = Direction.UP;
            }
            else {
                xDirection = Direction.DOWN;
            }
        }
        else {
            if (x.getX() > y.getX()) {
                xDirection = Direction.RIGHT;
            }
            else {
                xDirection = Direction.LEFT;
            }
        }
        yDirection = getOpposite(xDirection);
    }

    protected Direction getOpposite(Direction direction) {
        switch (direction) {
            case UP:
                return Direction.DOWN;
            case DOWN:
                return Direction.UP;
            case LEFT:
                return Direction.RIGHT;
            case RIGHT:
                return Direction.LEFT;
        }
        return null;
    }

    public Direction getxDirection() {
        return xDirection;
    }

    public Direction getyDirection() {
        return yDirection;
    }
}
