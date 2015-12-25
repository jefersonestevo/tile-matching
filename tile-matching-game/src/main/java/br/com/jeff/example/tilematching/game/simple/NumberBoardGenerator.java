package br.com.jeff.example.tilematching.game.simple;

import br.com.jeff.example.tilematching.game.Board;
import br.com.jeff.example.tilematching.game.BoardGenerator;
import br.com.jeff.example.tilematching.game.Position;
import br.com.jeff.example.tilematching.game.Tile;

public class NumberBoardGenerator implements BoardGenerator {

    private int width;
    private int height;

    public NumberBoardGenerator(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public Board generateBoard() {
        Tile[][] tiles = new Tile[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tiles[i][j] = getNewTileForPosition(null);
            }
        }
        return new Board(tiles);
    }

    @Override
    public Tile getNewTileForPosition(Position position) {
        return new NumberTyle(new Double(Math.random() * 5).intValue());
    }
}
