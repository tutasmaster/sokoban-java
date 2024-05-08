package tetris.game;

import java.io.Serializable;

public class Map implements Serializable {
    public Map(int sizeX, int sizeY) {
        this._size_x = sizeX;
        this._size_y = sizeY;
        initialize();
    }

    public void initialize(){
        _map = new int[_size_x*_size_y];
    }

    public enum Tile{
        Empty,
        Cyan,
        Blue,
        Orange,
        Green,
        Purple,
        Yellow,
        Red
    }

    private int[] _map;

    public int get_size_x() {
        return _size_x;
    }

    public int get_size_y() {
        return _size_y;
    }

    private int _size_x, _size_y;
    public Tile getTile(int x, int y){
        if(x > -1 && x < _size_x && y > -1 && y < _size_y){
            return Tile.values()[_map[x + (y * _size_x)]];
        }
        return Tile.Red;
    }

    public void setTile(int x, int y, Tile t){
        _map[x + (y * _size_x)] = t.ordinal();
    }

    public void setPiece(int x, int y, Piece p) {
        for (int j = 0; j < 4; j++){
            for (int i = 0; i < 4; i++){
                int xFinal = x + i;
                int yFinal = y+j;
                Tile t = p.getTile(i,j);
                if(t != Tile.Empty && xFinal > -1 && xFinal < _size_x && yFinal > -1 && yFinal < _size_y){
                    setTile(xFinal,yFinal,t);
                }
            }
        }
    }

}
