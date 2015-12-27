package br.com.jeff.example.tilematching.game;

import java.util.*;

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
    public boolean move(Position x, Position y) {
        if (!board.isValidMovement(x, y)) {
            return false;
        }

        gameRule.movementStarted();
        Tile tileX = board.getTile(x);
        Tile tileY = board.getTile(y);

        MovementDirection movementDirection = new MovementDirection(x, y);
        createEventForDirection(tileX, x, y, movementDirection.getxDirection());
        createEventForDirection(tileY, y, x, movementDirection.getyDirection());

        board.swap(x, y);

        List<Set<Position>> tylesDestroyed = destroyTilesIfMatched(x, y);

        gameRule.movementEnded();

        if (!tylesDestroyed.isEmpty()) {
            // TODO - Destroy other tyles if possible
        }
        return true;
    }

    protected void createEventForDirection(Tile tile, Position currentPosition, Position destination, MovementDirection.Direction direction) {
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

    protected List<Set<Position>> destroyTilesIfMatched(Position... positions) {
        int matchesToScore = 3;

        List<Set<Position>> tylesDestroyed = new ArrayList<Set<Position>>();
        for (Position position : positions) {
            Set<Position> candidatesX = getCandidatesFromXAxis(position);
            Set<Position> candidatesY = getCandidatesFromYAxis(position);

            if (candidatesX.size() >= matchesToScore && candidatesX.size() >= candidatesY.size()) {
                destroyTilesForPositions(candidatesX);
                tylesDestroyed.add(candidatesX);
            }
            else if (candidatesY.size() >= matchesToScore){
                destroyTilesForPositions(candidatesY);
                tylesDestroyed.add(candidatesY);
            }
        }
        return tylesDestroyed;
    }

    protected void destroyTilesForPositions(Set<Position> positions) {
        for (Position position : positions) {
            gameRule.onEvent(board.getTile(position), position, null, EventType.DESTROYED);

            Tile newTile = boardGenerator.getNewTileForPosition(position);
            board.replaceTile(position, newTile);
            gameRule.onEvent(newTile, position, null, EventType.CREATED);
        }
    }

    protected Set<Position> getCandidatesFromXAxis(Position position) {
        int xPosition = position.getX();
        Tile tile = board.getTile(position);

        List<Set<Position>> allCandidates = new ArrayList<Set<Position>>();
        Set<Position> candidates = null;
        for (int i = 0; i < board.getHeight(); i++) {
            Position currentPosition = new Position(xPosition, i);
            Tile currentTile = board.getTile(currentPosition);
            if (currentTile != null && currentTile.isEqualTo(tile)) {
                if (candidates == null) {
                    candidates = new LinkedHashSet<Position>();
                    allCandidates.add(candidates);
                }
                candidates.add(currentPosition);
            }
            else {
                candidates = null;
            }
        }

        for (Set<Position> currentCandidateList : allCandidates) {
            if (currentCandidateList.contains(position)) {
                return currentCandidateList;
            }
        }
        return Collections.emptySet();
    }

    protected Set<Position> getCandidatesFromYAxis(Position position) {
        int yPosition = position.getY();
        Tile tile = board.getTile(position);

        List<Set<Position>> allCandidates = new ArrayList<Set<Position>>();
        Set<Position> candidates = null;
        for (int i = 0; i < board.getWidth(); i++) {
            Position currentPosition = new Position(i, yPosition);
            Tile currentTile = board.getTile(currentPosition);
            if (currentTile != null && currentTile.isEqualTo(tile)) {
                if (candidates == null) {
                    candidates = new LinkedHashSet<Position>();
                    allCandidates.add(candidates);
                }
                candidates.add(currentPosition);
            }
            else {
                candidates = null;
            }
        }

        for (Set<Position> currentCandidateList : allCandidates) {
            if (currentCandidateList.contains(position)) {
                return currentCandidateList;
            }
        }
        return Collections.emptySet();
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
