package sokoban.game.renderer;

import sokoban.game.Vector2;
import sokoban.game.Game;
import sokoban.game.Map;
import sokoban.game.entity.Entity;
import sokoban.game.entity.Goal;

import java.io.PrintStream;
import java.util.Scanner;

public class ConsoleRenderer implements Renderer {

    private static final PrintStream _stream = System.out;
    private final Game _game;
    
    public ConsoleRenderer(Game game) {
        _game = game;
        _scanner = new Scanner(System.in);
    }

    
    
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

    public static void drawCharAt(Game g, StringBuilder s, char c, int x, int y) {
        s.setCharAt(x + (y * (g.getMap().getWidth() + 1)), c);
    }

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

    public Game.INPUT_RESULT input() throws Exception {
        String input = _scanner.nextLine();
        return _game.input(input);
    }
    
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
