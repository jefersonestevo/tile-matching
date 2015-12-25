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

    public void replaceTile(Point point, Tile newTile) {
        tiles[point.getX()][point.getY()] = newTile;
    }

    public MovementDirection swap(Point x, Point y) {
        Tile tempTile = tiles[x.getX()][x.getY()];
        tiles[x.getX()][x.getY()] = tiles[y.getX()][y.getY()];
        tiles[y.getX()][y.getY()] = tempTile;
        return new MovementDirection(x, y);
    }

    public Tile getTile(Point position) {
        return tiles[position.getX()][position.getY()];
    }

    public Tile getDownTile(Point point) {
        if (point.getY() + 1 < tiles[point.getX()].length) {
            return getTile(new Point(point.getX(), point.getY() + 1));
        }
        return null;
    }

    public Tile getUpTile(Point point) {
        if (point.getY() - 1 >= tiles[point.getX()].length) {
            return getTile(new Point(point.getX(), point.getY() - 1));
        }
        return null;
    }

    public Tile getLeftTile(Point point) {
        if (point.getX() - 1 >= tiles.length) {
            return getTile(new Point(point.getX() - 1, point.getY()));
        }
        return null;
    }

    public Tile getRightTile(Point point) {
        if (point.getX() + 1 < tiles.length) {
            return getTile(new Point(point.getX() + 1, point.getY()));
        }
        return null;
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
