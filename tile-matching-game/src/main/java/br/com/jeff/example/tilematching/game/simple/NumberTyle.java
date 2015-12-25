package br.com.jeff.example.tilematching.game.simple;

import br.com.jeff.example.tilematching.game.Tile;

public class NumberTyle implements Tile<NumberTyle> {
    private int number;

    public NumberTyle(int number) {
        this.number = number;
    }

    @Override
    public boolean isEqualTo(NumberTyle otherTile) {
        return this.number == otherTile.number;
    }

    public int getNumber() {
        return number;
    }
}
