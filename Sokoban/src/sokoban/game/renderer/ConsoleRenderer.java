package sokoban.game.renderer;

import sokoban.game.Coord2DInt;
import sokoban.game.Game;
import sokoban.game.Map;
import sokoban.game.entity.Entity;
import sokoban.game.entity.Goal;

import java.io.PrintStream;
import java.util.Scanner;

public class ConsoleRenderer implements Renderer {

    static PrintStream stream = System.out;
    private final Game _game;
    
    public ConsoleRenderer(Game game) {
        _game = game;
        _scanner = new Scanner(System.in);
    }

    
    
    public char tileToChar(Map.Tile t) {

        switch (t) {
            case Empty -> {
                return ' ';
            }
            case Floor -> {
                return '.';
            }
            case Wall -> {
                return '#';
            }
        }
        return '?';
    }

    public StringBuilder renderMap(Map m) {
        //Using a stringbuilder because strings are immutable
        //This will have a big performance advantage, although in practice
        //in a project of this size, it's a bit redundant
        StringBuilder map_string = new StringBuilder();
        for (int y = 0; y < m.get_size_y(); y++) {
            for (int x = 0; x < m.get_size_x(); x++) {
                var t = m.getTile(x, y);
                map_string.append(tileToChar(t));
            }
            map_string.append("\n");
        }
        return map_string;
    }

    public static void drawCharAt(Game g, StringBuilder s, char c, int x, int y) {
        s.setCharAt(x + (y * (g.get_map().get_size_x() + 1)), c);
    }

    public void render() {
        var m = renderMap(_game.get_map());
        for (Entity e : _game.get_entity_list()) {
            Coord2DInt p = e.getPosition();
            if (e.getClass() == Goal.class) {
                drawCharAt(_game, m, '*', p.x, p.y);
                continue;
            }
            drawCharAt(_game, m, '■', p.x, p.y);
        }
        Coord2DInt pos = _game.get_player().getPosition();
        drawCharAt(_game, m, '@', pos.x, pos.y);
        stream.println(m);
    }

    Scanner _scanner;

    public Game.INPUT_RESULT input() throws Exception {
        String input = _scanner.nextLine();
        return _game.input(input);
    }
    
    @Override
    public void start() {
        while(_game.is_running()){
            try{
                _game.iterate();
                render();
                Game.INPUT_RESULT inputResult = input();
                switch (inputResult){
                    case NEXT_LEVEL->System.out.println("Welcome to level " + _game.current_level + "!");
                    case END->System.out.println("Congratulations, you've reached the end!");
                }
            }catch(Exception e){
                _game.set_running(false);
                e.printStackTrace();
            }
        }
    }
}
