package tetris.game.renderer.swing;

import tetris.game.Game;
import tetris.game.renderer.Renderer;

import javax.swing.*;

public class SwingRenderer implements Renderer {
    public SwingRenderer(Game game) {
        setGame(game);
        SwingGameFrame frame = new SwingGameFrame(_game);
        frame.setGame(_game);
        frame.pack();
        frame.setVisible(true);
    }


    private Game _game;


    public void render(Game game) {

    }

    public void setGame(Game game){
        _game = game;
    }

    public void input(Game game) throws Exception {

    }
}
