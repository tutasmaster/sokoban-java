package sokoban;

import sokoban.game.Game;
import sokoban.game.renderer.ConsoleRenderer;
import sokoban.game.renderer.Renderer;
import sokoban.game.renderer.swing.SwingRenderer;

public class Application {

    private final Game _game = new Game();
    private Renderer _renderer;

    public void runConsole(){
        run_console_renderer();
    }
    public void runSwing(){
        run_swing_renderer();
    }


    private void run_console_renderer(){
        _game.start();
        _renderer = new ConsoleRenderer(_game);
        _renderer.start();
    }
    
    private void run_swing_renderer() {
        _game.start();
        _renderer = new SwingRenderer(_game);
        _renderer.start();
    }
}
