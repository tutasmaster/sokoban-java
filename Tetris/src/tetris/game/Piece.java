package tetris.game;

import rsIPT.graphics.SwingColors;

import java.awt.*;
import java.util.Dictionary;

import static tetris.game.Piece.Type.I;

public class Piece {
    public Map.Tile getTile(int x, int y) {
        return _map[x + (y*4)];
    }

    public enum Type {
        EMPTY,
        I,
        O,
        Z,
        S,
        L,
        T,
        J
    }
    private Map.Tile _type;
    public Color getColor(){
        switch(_type){
            case Cyan -> {
                return SwingColors.CYAN;
            }
            case Blue -> {
                return SwingColors.BLUE;
            }
            case Orange -> {
                return SwingColors.ORANGE;
            }
            case Green -> {
                return SwingColors.GREEN;
            }
            case Purple -> {
                return SwingColors.PURPLE;
            }
            case Red -> {
                return SwingColors.RED;
            }
            case Yellow -> {
                return SwingColors.YELLOW;
            }
        }
        return SwingColors.BACKGROUND;
    }

    public static Piece generatePiece(Type t){
        String data = "0000000000000000";
        Map.Tile tile = Map.Tile.Empty;
        switch (t){
            case I -> {
                data = "0010001000100010";
                tile = Map.Tile.Cyan;
            }
            case O -> {
                data = "0000011001100000";
                tile = Map.Tile.Yellow;
            }
            case Z -> {
                data = "0000011000110000";
                tile = Map.Tile.Red;
            }
            case S -> {
                data = "0000011011000000";
                tile = Map.Tile.Green;
            }
            case L -> {
                data = "0100010001000110";
                tile = Map.Tile.Orange;
            }
            case J -> {
                data = "0010001000100110";
                tile = Map.Tile.Blue;
            }
            case T -> {
                data = "0000011100100000";
                tile = Map.Tile.Purple;
            }
        }
        return new Piece(data, tile);
    }

    public Piece rotatePiece(){
        Piece p = new Piece("0000000000000000", _type);
        for(int j = 0; j < 4; j++){
            for(int i = 0; i < 4; i++){
                p._map[i + (j*4)] = _map[(3-j) + (i*4)];
            }
        }
        return p;
    }

    private Map.Tile[] _map;
    public Piece(String data, Map.Tile t){
        //00000110011000000 SQUARE
        this._type = t;
        _map = new Map.Tile[4*4];
        for(int j = 0; j < 4; j++){
            for(int i = 0; i < 4; i++){
                _map[i + (j*4)] = data.charAt(i + (j*4)) == '1' ? t : Map.Tile.Empty;
            }
        }
    }
}
