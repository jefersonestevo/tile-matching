package br.com.jeff.example.tilematching.game;

import java.io.Serializable;

public class Board implements Serializable {

    private Tile[][] tiles;
    private int width;
    private int height;

    public Board(Tile[][] tiles) {
        this.tiles = tiles;
        // TODO - Validate width and height
        this.width = tiles.length;
        this.height = Integer.MIN_VALUE;
        for (Tile[] tile : tiles) {
            if (tile.length > this.height) {
                this.height = tile.length;
            }
        }
    }

    public boolean isValidMovement(Position x, Position y) {
        if (!isInRange(x) || !isInRange(y)) {
            return false;
        }
        if (x.equals(y)) {
            return false;
        }
        int xDifference = x.getX() - y.getX();
        if (xDifference < -1 || xDifference > 1) {
            return false;
        }
        int yDifference = x.getY() - y.getY();
        if (yDifference < -1 || yDifference > 1) {
            return false;
        }
        return true;
    }

    protected boolean isInRange(Position position) {
        return position.getX() < width && position.getY() < height;
    }

    public void replaceTile(Position position, Tile newTile) {
        tiles[position.getX()][position.getY()] = newTile;
    }

    public MovementDirection swap(Position x, Position y) {
        Tile tempTile = tiles[x.getX()][x.getY()];
        tiles[x.getX()][x.getY()] = tiles[y.getX()][y.getY()];
        tiles[y.getX()][y.getY()] = tempTile;
        return new MovementDirection(x, y);
    }

    public Tile getTile(Position position) {
        return tiles[position.getX()][position.getY()];
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
