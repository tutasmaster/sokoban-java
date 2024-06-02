package sokoban.game;

import java.io.Serializable;

/**
 * Map used by the game, it contains an integer array, each integer representing a tile
 */
public class Map implements Serializable {
    //CONSTANTS
    public static final char EMPTY_CHAR = ' ';
    public static final char WALL_CHAR = '#';
    public static final char FLOOR_CHAR = '.';
    public static final char ICE_CHAR = ',';
    public static final char INVALID_CHAR = '?';
    
    /**
     * Tiles used by the map
     */
    public enum Tile {
        EMPTY,
        FLOOR,
        WALL,
        ICE
    }
    
    /**
     * Constructs a map with width and height but does NOT initialized it
     * @param sizeX width
     * @param sizeY height
     */
    public Map(int sizeX, int sizeY) {
        this._width = sizeX;
        this._height = sizeY;
    }
    
    /**
     * Initializes the map as an array of width * height
     */
    public void initialize() {
        _map = new int[_width * _height];
    }
    
    private int[] _map;
    
    private final int _width;
    private final int _height;
    
    /**
     * Gets the width of the map
     * @return int
     */
    public int getWidth() {
        return _width;
    }
    
    /**
     * Gets the height of the map
     * @return int
     */
    public int getHeight() {
        return _height;
    }
    
    /**
     * Returns an initialized map surrounded by walls and with a floor on the inside
     * We use it for the map editor whenever there is a change in map size, or when testing out
     * the map generation functions.
     * @param width of the map
     * @param height of the map
     * @return Map
     */
    public static Map generateBoxMap(int width, int height) {
        Map m = new Map(width, height);
        m.initialize();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x == 0 || x == (width - 1) || y == 0 || y == (height - 1)) {
                    m.setTile(x, y, Tile.WALL);
                } else {
                    m.setTile(x, y, Tile.FLOOR);
                }
            }
        }
        return m;
    }
    
    
    
    /**
     * Generates a map based on level01.sokl
     * This exists for when the game needs to fallback onto a map but the loading of
     * maps is unavailable, such as missing the level data files (.sokl)
     * @return Map
     */
    public static Map generateTestMap() {
        Map m = new Map(8, 6);
        m.initialize();
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 8; x++) {
                if (x == 0 || x == 7 || y == 0 || y == 5) {
                    m.setTile(x, y, Tile.WALL);
                } else {
                    m.setTile(x, y, Tile.FLOOR);
                }
            }
            m.setTile(3, 1, Tile.WALL);
            m.setTile(3, 3, Tile.WALL);
            m.setTile(3, 4, Tile.WALL);
            m.setTile(3, 5, Tile.WALL);
            m.setTile(6, 4, Tile.WALL);
            m.setTile(6, 3, Tile.WALL);
            m.setTile(7, 4, Tile.EMPTY);
            m.setTile(7, 5, Tile.EMPTY);
        }
        return m;
    }
    
    /**
     * Returns the tile for a given x and y position
     * It exists because the map is a 1d array and not 2d array.
     * pos = x + (y * width)
     * @param x position
     * @param y position
     * @return Tile
     */
    public Tile getTile(int x, int y) {

        if (x > -1 && y > -1 && x < _width && y < _height) {
            return Tile.values()[_map[x + (y * _width)]];
        } else {
            return null;
        }
    }
    
    /**
     * Sets the tile for a given x and y position
     * It exists because the map is a 1d array and not 2d array.
     * pos = x + (y * width)
     * @param x position
     * @param y position
     * @param tile tile to place
     */
    public void setTile(int x, int y, Tile tile) {
        if (x > -1 && y > -1 && x < _width && y < _height) {
            _map[x + (y * _width)] = tile.ordinal();
        }
    }
    
    /*
    .sokl MAP PARSING
     */
    
    
    /**
     * Returns the map converted into characters for usage in the level files (.sokl)
     * @return StringBuilder
     */
    public StringBuilder parse() {
        StringBuilder result = new StringBuilder();
        for (int y = 0; y < _height; y++) {
            for (int x = 0; x < _width; x++) {
                switch (getTile(x, y)) {
                    case EMPTY:
                        result.append(EMPTY_CHAR);
                        break;
                    case FLOOR:
                        result.append(FLOOR_CHAR);
                        break;
                    case WALL:
                        result.append(WALL_CHAR);
                        break;
                    case ICE:
                        result.append(ICE_CHAR);
                        break;
                    default :
                        result.append(INVALID_CHAR);
                        break;
                }
            }
            result.append("\n");
        }
        return result;
    }
    
    /**
     *
     * @param width
     * @param height
     * @param string
     * @return Map
     */
    public static Map fromString(int width, int height, String string) {
        Map map = new Map(width, height);
        map.initialize();
        int i = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                switch (string.charAt(i)) {
                    case EMPTY_CHAR :
                        map.setTile(x, y, Tile.EMPTY);
                        break;
                    case WALL_CHAR:
                        map.setTile(x, y, Tile.WALL);
                        break;
                    case ICE_CHAR:
                        map.setTile(x, y, Tile.ICE);
                        break;
                    default :
                        map.setTile(x, y, Tile.FLOOR);
                        break;
                }
                i++;
            }
        }
        return map;
    }
    
    /**
     * This function exists because parsed map strings include a \n at the end of every line to determine width
     * @param x
     * @param y
     * @return int
     */
    public int parsedCoordToIdx(int x, int y) {
        return x + (y * (_width + 1));
    }
}
