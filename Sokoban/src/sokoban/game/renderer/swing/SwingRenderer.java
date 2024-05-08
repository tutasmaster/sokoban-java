package sokoban.game.renderer.swing;

import sokoban.game.Game;
import sokoban.game.renderer.Renderer;

import javax.swing.*;
import java.awt.*;
import java.util.Dictionary;
import java.util.Map;

public class SwingRenderer implements Renderer {
    public SwingRenderer(Game game) {
        setGame(game);
        SwingGameFrame frame = new SwingGameFrame(_game);
        frame.setGame(_game);
        frame.pack();
        frame.setVisible(true);
    }


    private Game _game;

    @Override
    public void render(Game game) {

    }

    public void setGame(Game game){
        _game = game;
    }

    @Override
    public void input(Game game) throws Exception {

    }

    public static void renderException(Component c, Exception e){
        JOptionPane.showMessageDialog(c, e.getMessage(), e.getClass().toString(), JOptionPane.ERROR_MESSAGE);
    }

    public static void renderMessage(Component c, String message, String title){
        JOptionPane.showMessageDialog(c, message, title, JOptionPane.PLAIN_MESSAGE);
    }
}
