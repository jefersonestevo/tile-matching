package br.com.jeff.example.tilematching.game;

public interface Tile<T extends Tile> {

    boolean isEqualTo(T tile);

}
