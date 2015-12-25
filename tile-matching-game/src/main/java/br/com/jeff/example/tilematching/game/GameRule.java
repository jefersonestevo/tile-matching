package br.com.jeff.example.tilematching.game;

public interface GameRule {

    void movementStarted();

    void movementEnded();

    void onEvent(Tile tile, Point currentPosition, Point destination, EventType eventType);

    GameResult getResult(Board board);
}
