package br.com.jeff.example.tilematching.game.simple;

import br.com.jeff.example.tilematching.game.*;

public class SimplePointRule implements GameRule {
    private int maxPoints;
    private int maxMovements;
    private int currentPoints;
    private int currentMovements;

    public SimplePointRule(int maxPoints, int maxMovements) {
        this.maxPoints = maxPoints;
        this.maxMovements = maxMovements;
    }

    @Override
    public void movementStarted() {
        currentMovements++;
    }

    @Override
    public void movementEnded() {
    }

    @Override
    public void onEvent(Tile tile, Point currentPosition, Point destination, EventType eventType) {
        if (EventType.DESTROYED.equals(eventType)) {
            currentPoints++;
        }
    }

    @Override
    public GameResult getResult(Board board) {
        if (currentPoints >= maxPoints) {
            return GameResult.WON;
        }
        else if (currentMovements >= maxMovements) {
            return GameResult.LOST;
        }
        return GameResult.CONTINUE;
    }

    public int getCurrentPoints() {
        return currentPoints;
    }

    public int getCurrentMovements() {
        return currentMovements;
    }
}
