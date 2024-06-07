package sokoban.game.renderer;

import sokoban.game.Vector2;
import sokoban.game.Game;
import sokoban.game.Map;
import sokoban.game.entity.Entity;
import sokoban.game.entity.Goal;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Renders the game to a terminal.
 * The inputs match the strings that game uses internally in the Game.input(String input) function.
 */
public class ConsoleRenderer implements Renderer {

    private static final PrintStream _stream = System.out;
    private final Game _game;
    
    /**
     * Constructor for the renderer, it gets a game reference to modify.
     * @param game reference to the game
     */
    public ConsoleRenderer(Game game) {
        _game = game;
        _scanner = new Scanner(System.in);
    }
    
    
    /**
     * Converts a tile to a visible character in the console.
     * @param t tile
     * @return character that represents a tile
     */
    public char tileToChar(Map.Tile t) {

        switch (t) {
            case EMPTY:
                return ' ';
            case FLOOR:
                return '.';
            case WALL:
                return '#';
        }
        return '?';
    }
    
    /**
     * Renders the map to a string.<br>
     * It exists so that later the string can replace elements with entities,
     * that sit on top of tiles.
     * @param m map
     * @return a string that is a representation of the map. Seperated rows by '\n' characters.
     */
    public StringBuilder renderMap(Map m) {
        //Using a StringBuilder because strings are immutable
        //This will have a big performance advantage, although in practice
        //in a project of this size, it's a bit redundant
        StringBuilder map_string = new StringBuilder();
        for (int y = 0; y < m.getHeight(); y++) {
            for (int x = 0; x < m.getWidth(); x++) {
                Map.Tile t = m.getTile(x, y);
                map_string.append(tileToChar(t));
            }
            map_string.append("\n");
        }
        return map_string;
    }
    
    /**
     * It takes a string and writes a character in a specific x and y position.<br>
     * This is usually used for writing entities to a result of renderMap(Map m).
     * @param g game
     * @param s target string
     * @param c target character
     * @param x position
     * @param y position
     */
    public static void drawCharAt(Game g, StringBuilder s, char c, int x, int y) {
        s.setCharAt(x + (y * (g.getMap().getWidth() + 1)), c);
    }
    
    /**
     * Renders the current map with the tiles and entities.
     */
    public void render() {
        StringBuilder m = renderMap(_game.getMap());
        for (Entity e : _game.getEntityList()) {
            Vector2 p = e.getPosition();
            if (e.getClass() == Goal.class) {
                drawCharAt(_game, m, '*', p.x, p.y);
                continue;
            }
            drawCharAt(_game, m, '■', p.x, p.y);
        }
        Vector2 pos = _game.getPlayer().getPosition();
        drawCharAt(_game, m, '@', pos.x, pos.y);
        _stream.println(m);
    }

    private final Scanner _scanner;
    
    /**
     * It takes an input from the terminal and sends it to the game.
     * It returns an INPUT_RESULT that is used to determine events that should happen,
     * at certain points in the game. Such as the level changing or beating the game.
     * @return an INPUT_RESULT for the given input.
     * @throws Exception
     */
    public Game.INPUT_RESULT input() throws Exception {
        String input = _scanner.nextLine();
        return _game.input(input);
    }
    
    /**
     * Creates the main loop, it keeps redrawing the game and asking the user
     * for the next input.<br>
     * It will do this until the game decides it's not running anymore by changing
     * the running property.
     */
    @Override
    public void start() {
        while(_game.isRunning()){
            try{
                _game.iterate();
                render();
                Game.INPUT_RESULT inputResult = input();
                switch (inputResult){
                    case NEXT_LEVEL:
                        System.out.println("Welcome to level " + _game.currentLevel + "!");
                        break;
                    case END:
                        System.out.println("Congratulations, you've reached the end!");
                        break;
                }
            }catch(Exception e){
                _game.setRunning(false);
                e.printStackTrace();
            }
        }
    }
}
