package br.com.jeff.example.tilematching.game;

import br.com.jeff.example.tilematching.game.*;
import br.com.jeff.example.tilematching.game.simple.NumberBoardGenerator;
import br.com.jeff.example.tilematching.game.simple.NumberTyle;
import br.com.jeff.example.tilematching.game.simple.SimplePointRule;

import java.util.Scanner;

public class TileMatchingGameImplTest {

    private static TileMatchingGameImpl tileMatchingGame;

    private static GameRule gameRule;

    public static void init() {
        gameRule = new SimplePointRule(5, 50);
        BoardGenerator boardGenerator = new NumberBoardGenerator(5, 5);

//        String a = "02 02 03 03 02"
//                + " 00 04 03 01 03"
//                + " 01 01 02 02 01"
//                + " 02 02 02 04 01"
//                + " 02 04 04 03 02";
        String a = "02 02 03 03 02"
                + " 00 04 03 03 03"
                + " 01 01 02 02 03"
                + " 02 02 02 04 01"
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
            System.out.println("Informe o primeiro ponto: ");
            userInput = new Scanner(System.in).nextLine();
            if ("end".equals(userInput)) {
                break;
            }
            Point x = getPoint(userInput);
            if (x == null) {
                continue;
            }
            System.out.println("Informe o segundo ponto: ");
            userInput = new Scanner(System.in).nextLine();
            if ("end".equals(userInput)) {
                break;
            }
            Point y = getPoint(userInput);
            if (y == null) {
                continue;
            }

            tileMatchingGame.move(x, y);
        }

        if (tileMatchingGame.isWon()) {
            System.out.println("YOU WON");
        }
        else if (tileMatchingGame.isLost()) {
            System.out.println("YOU LOST");
        }
    }

    protected static Point getPoint(String userInput) {
        try {
            String x = userInput.split(",")[0].trim();
            String y = userInput.split(",")[1].trim();
            return new Point(Integer.valueOf(x), Integer.valueOf(y));
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