package br.com.jeff.example.tilematching.game;

import br.com.jeff.example.tilematching.game.simple.NumberBoardGenerator;
import br.com.jeff.example.tilematching.game.simple.NumberTyle;
import br.com.jeff.example.tilematching.game.simple.SimplePointRule;

import java.util.Scanner;

public class TileMatchingGameImplTest {

    private static TileMatchingGameImpl tileMatchingGame;

    private static SimplePointRule gameRule;

    public static void init() {
        gameRule = new SimplePointRule(10, 50);
        BoardGenerator boardGenerator = new NumberBoardGenerator(5, 5);
        final String a = "02 02 03 03 02"
                + " 00 04 03 03 02"
                + " 01 01 02 02 03"
                + " 02 02 02 04 02"
                + " 02 04 04 03 02";
        boardGenerator = new NumberBoardGenerator(5, 5) {
            @Override
            public Board generateBoard() {
                Tile[][] tiles = new Tile[5][5];
                String[] values = a.split(" ");
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        int value = Integer.valueOf(values[((i % 5) * 5) + j]);
                        tiles[i % 5][j] = new NumberTyle(value);
                    }
                }
                return new Board(tiles);
            }
        };
        tileMatchingGame = new TileMatchingGameImpl(gameRule, boardGenerator);
    }

    public static void main(String... args) {
        init();
        System.out.println("Starting game");
        String userInput = "";
        while (!"end".equals(userInput) && !tileMatchingGame.isFinished()) {
            printBoard(tileMatchingGame.getBoard());
            System.out.println("First position: ");
            userInput = new Scanner(System.in).nextLine();
            if ("end".equals(userInput)) {
                break;
            }
            Position x = getPosition(userInput);
            if (x == null) {
                System.out.println("Invalid position. Please try again");
                continue;
            }
            System.out.println("Second position: ");
            userInput = new Scanner(System.in).nextLine();
            if ("end".equals(userInput)) {
                break;
            }
            Position y = getPosition(userInput);
            if (y == null) {
                System.out.println("Invalid position. Please try again");
                continue;
            }

            if (tileMatchingGame.move(x, y)) {
                System.out.format("Moved tile from position [%d, %d] to position [%d, %d]\n", x.getX(), x.getY(), y.getX(), y.getY());
            }
            else {
                System.out.println("Can't do this. Please try another movement");
            }
            System.out.println("Movement number: " + gameRule.getCurrentMovements());
            System.out.println("Your current score is: " + gameRule.getCurrentPoints());
            System.out.println("============================================");
        }

        if (tileMatchingGame.isWon()) {
            System.out.println("YOU WON");
        }
        else if (tileMatchingGame.isLost()) {
            System.out.println("YOU LOST");
        }
        else {
            System.out.println("GAME ENDED BY THE USER");
        }
    }

    protected static Position getPosition(String userInput) {
        try {
            String x = userInput.split(",")[0].trim();
            String y = userInput.split(",")[1].trim();
            return new Position(Integer.valueOf(x), Integer.valueOf(y));
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    protected static void printBoard(Board board) {
        Tile[][] tiles = board.getTiles();
        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getHeight(); j++) {
                NumberTyle numberTyle = (NumberTyle) tiles[i][j];
                System.out.format("%02d[%01d,%01d]", numberTyle.getNumber(), i, j);
                System.out.print("  ");
            }
            System.out.println();
        }
    }
}