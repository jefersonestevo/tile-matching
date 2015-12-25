package br.com.jeff.example.tilematching.game;

import java.util.*;
import java.util.function.Function;

public class TileMatchingGameImpl implements TileMatchingGame {

    private GameRule gameRule;
    private Board board;
    private BoardGenerator boardGenerator;

    public TileMatchingGameImpl(GameRule gameRule, BoardGenerator boardGenerator) {
        this.gameRule = gameRule;
        this.boardGenerator = boardGenerator;
        this.board = boardGenerator.generateBoard();
    }

    @Override
    public boolean move(Point x, Point y) {
        // TODO - Validate if movement is possible and return false
        gameRule.movementStarted();
        Tile tileX = board.getTile(x);
        Tile tileY = board.getTile(y);

        MovementDirection movementDirection = new MovementDirection(x, y);
        createEventForDirection(tileX, x, y, movementDirection.getxDirection());
        createEventForDirection(tileY, y, x, movementDirection.getyDirection());

        board.swap(x, y);

        destroyTilesIfMatched(x, y);

        gameRule.movementEnded();
        return true;
    }

    protected void createEventForDirection(Tile tile, Point currentPosition, Point destination, MovementDirection.Direction direction) {
        EventType eventType = null;
        switch (direction) {
            case UP:
                eventType = EventType.MOVED_UP;
                break;
            case DOWN:
                eventType = EventType.MOVED_DOWN;
                break;
            case LEFT:
                eventType = EventType.MOVED_LEFT;
                break;
            case RIGHT:
                eventType = EventType.MOVED_RIGHT;
        }
        gameRule.onEvent(tile, currentPosition, destination, eventType);
    }

    protected void destroyTilesIfMatched(Point... positions) {
        int matchesToScore = 3;

        for (Point position : positions) {
            // FIXME - Ajustar toda esta regra
            Set<Point> candidatesX = new LinkedHashSet<>();
            fillCandidateTiles(position, candidatesX, board::getUpTile, (p) -> new Point(p.getX(), p.getY() - 1));
            fillCandidateTiles(position, candidatesX, board::getDownTile, (p) -> new Point(p.getX(), p.getY() + 1));

            Set<Point> candidatesY = new LinkedHashSet<>();
            fillCandidateTiles(position, candidatesY, board::getLeftTile, (p) -> new Point(p.getX() - 1, p.getY()));
            fillCandidateTiles(position, candidatesY, board::getRightTile, (p) -> new Point(p.getX() + 1, p.getY()));

            if (candidatesX.size() >= matchesToScore && candidatesX.size() >= candidatesY.size()) {
                destroyTilesForPositions(candidatesX);
            }
            else if (candidatesY.size() >= matchesToScore){
                destroyTilesForPositions(candidatesY);
            }
        }
    }

    protected void destroyTilesForPositions(Set<Point> positions) {
        for (Point position : positions) {
            gameRule.onEvent(board.getTile(position), position, null, EventType.DESTROYED);

            Tile newTile = boardGenerator.getNewTileForPosition(position);
            board.replaceTile(position, newTile);
            gameRule.onEvent(newTile, position, null, EventType.CREATED);
        }
    }

    private void fillCandidateTiles(Point position, Set<Point> candidatePoints, Function<Point, Tile> getNextTile, Function<Point, Point> getNextPosition) {
        Point currentPosition = position;
        while (currentPosition != null) {
            candidatePoints.add(currentPosition);
            Tile currentTile = board.getTile(currentPosition);
            Tile nextTile = getNextTile.apply(currentPosition);
            if (nextTile != null && nextTile.isEqualTo(currentTile)) {
                currentPosition = getNextPosition.apply(currentPosition);
            }
            else {
                currentPosition = null;
            }
        }
    }

    @Override
    public boolean isWon() {
        return GameResult.WON.equals(gameRule.getResult(board));
    }

    @Override
    public boolean isLost() {
        return GameResult.LOST.equals(gameRule.getResult(board));
    }

    @Override
    public boolean isFinished() {
        return isWon() || isLost();
    }

    @Override
    public Board getBoard() {
        return board;
    }
}
