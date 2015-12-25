package br.com.jeff.example.tilematching.game;

import java.io.Serializable;

public interface GameRule extends Serializable {

    void movementStarted();

    void movementEnded();

    void onEvent(Tile tile, Position currentPosition, Position destination, EventType eventType);

    GameResult getResult(Board board);
}
