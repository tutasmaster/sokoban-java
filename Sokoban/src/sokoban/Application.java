package sokoban;

import sokoban.game.Game;
import sokoban.game.renderer.ConsoleRenderer;
import sokoban.game.renderer.Renderer;
import sokoban.game.renderer.swing.SwingRenderer;

/**
 * Game application, this creates a game object and
 * creates the renderer selected in the public static void main.<br>
 * This can be re-used by something like a game launcher at a later date.
 */
public class Application {

    private final Game _game = new Game();
    private Renderer _renderer;
    
    /**
     * Starts the Console Renderer.
     */
    public void runConsole(){
        run_console_renderer();
    }
    /**
     * Starts the Java Swing Renderer.
     */
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
