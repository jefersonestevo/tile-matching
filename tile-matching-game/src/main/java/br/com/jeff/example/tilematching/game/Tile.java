package br.com.jeff.example.tilematching.game;

import java.io.Serializable;

public interface Tile<T extends Tile> extends Serializable {

    boolean isEqualTo(T tile);

}
