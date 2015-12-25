package br.com.jeff.example.tilematching.game;

public interface TileMatchingGame {

    boolean move(Position x, Position y);

    boolean isWon();

    boolean isLost();

    boolean isFinished();

    Board getBoard();

}
