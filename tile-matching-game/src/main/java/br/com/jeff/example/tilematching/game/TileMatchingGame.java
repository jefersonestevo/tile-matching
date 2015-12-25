package br.com.jeff.example.tilematching.game;

public interface TileMatchingGame {

    boolean move(Point x, Point y);

    boolean isWon();

    boolean isLost();

    boolean isFinished();

    Board getBoard();

}
