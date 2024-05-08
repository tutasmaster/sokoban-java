package sokoban.game;

import java.io.Serializable;

public class Map implements Serializable {
    public Map(int sizeX, int sizeY) {
        this._size_x = sizeX;
        this._size_y = sizeY;
    }

    public static Map GenerateBoxMap(int width, int height) {
        Map m = new Map(width,height);
        m.initialize();
        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                if(x == 0 || x == (width-1) || y == 0 || y == (height-1)){
                    m.setTile(x,y,Tile.Wall);
                }else{
                    m.setTile(x,y,Tile.Floor);
                }
            }
        }
        return m;
    }

    public static Map fromString(int width, int height, String s) {
        Map m = new Map(width, height);
        m.initialize();
        int i = 0;
        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                switch(s.charAt(i)){
                    case ' ' -> m.setTile(x,y,Tile.Empty);
                    case '#' -> m.setTile(x,y,Tile.Wall);
                    default -> m.setTile(x,y,Tile.Floor);
                }
                i++;
            }
        }
        return m;
    }

    public void initialize(){
        _map = new int[_size_x*_size_y];
    }

    public static Map GenerateTestMap(){
        Map m = new Map(8,6);
        m.initialize();
        for (int y = 0; y < 6; y++){
            for (int x = 0; x < 8; x++){
                if(x == 0 || x == 7 || y == 0 || y == 5){
                    m.setTile(x,y,Tile.Wall);
                }else{
                    m.setTile(x,y,Tile.Floor);
                }
            }
            m.setTile(3,1, Tile.Wall);
            m.setTile(3,3, Tile.Wall);
            m.setTile(3,4, Tile.Wall);
            m.setTile(3,5, Tile.Wall);
            m.setTile(6,4, Tile.Wall);
            m.setTile(6,3, Tile.Wall);
            m.setTile(7,4, Tile.Empty);
            m.setTile(7,5, Tile.Empty);
        }
        return m;
    }

    public StringBuilder parse() {
        StringBuilder result = new StringBuilder();
        for (int y = 0; y < _size_y; y++){
            for (int x = 0; x < _size_x; x++){
                switch(getTile(x,y)){
                    case Empty -> result.append(" ");
                    case Floor -> result.append(".");
                    case Wall -> result.append("#");
                    default -> result.append("?");
                }
            }
            result.append("\n");
        }
        return result;
    }

    //This function exists becaused parsed map strings include a \n at the end of every line to determine width
    public int parsedCoordToIdx(int x, int y){
        return x + (y * (_size_x+1));
    }

    public enum Tile{
        Empty,
        Floor,
        Wall
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

        if(x > -1 && y > -1 && x < _size_x && y < _size_y) {
            return Tile.values()[_map[x + (y * _size_x)]];
        }
        else{
            return null;
        }
    }

    public void setTile(int x, int y, Tile t){
        if(x > -1 && y > -1 && x < _size_x && y < _size_y){
            _map[x + (y * _size_x)] = t.ordinal();
        }
    }

}
