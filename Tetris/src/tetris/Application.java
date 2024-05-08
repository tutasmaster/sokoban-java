package tetris;

import tetris.game.Game;
import tetris.game.renderer.Renderer;
import tetris.game.renderer.swing.SwingRenderer;

public class Application {
    private final Game _game = new Game();
    private Renderer _renderer;

    public void run(){
        runSwingRenderer();
    }

    private void runSwingRenderer() {
        //_game.start();
        _renderer = new SwingRenderer(_game);
        ((SwingRenderer)_renderer).setGame(_game);
    }
}
