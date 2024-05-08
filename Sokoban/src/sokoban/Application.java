package sokoban;

import sokoban.game.Game;
import sokoban.game.renderer.ConsoleRenderer;
import sokoban.game.renderer.Renderer;
import sokoban.game.renderer.swing.SwingGamePanel;
import sokoban.game.renderer.swing.SwingRenderer;

public class Application {

    private final Game _game = new Game();
    private Renderer _renderer;

    public void run(){
        runSwingRenderer();
    }

    private void runSwingRenderer() {
        _game.start();
        _renderer = new SwingRenderer(_game);
        ((SwingRenderer)_renderer).setGame(_game);

    }

    public void runConsoleRenderer(){
        _renderer = new ConsoleRenderer();
        _game.start();
        while(_game.is_running()){
            try{
                _game.iterate();
                _renderer.render(_game);
                _renderer.input(_game);
            }catch(Exception e){
                _game.set_running(false);
                e.printStackTrace();
            }
        }
    }
}
