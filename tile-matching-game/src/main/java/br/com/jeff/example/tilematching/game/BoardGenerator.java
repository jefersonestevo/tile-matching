package br.com.jeff.example.tilematching.game;

public interface BoardGenerator {

    Board generateBoard();

    Tile getNewTileForPosition(Point position);

}
