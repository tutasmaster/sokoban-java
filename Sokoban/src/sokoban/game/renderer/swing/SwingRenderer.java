package sokoban.game.renderer.swing;

import sokoban.game.Game;
import sokoban.game.renderer.Renderer;

import javax.swing.*;
import java.awt.*;

public class SwingRenderer implements Renderer {
    public SwingRenderer(Game game) {
        setGame(game);
    }


    private Game _game;

    public void setGame(Game game) {
        _game = game;
    }

    public static void renderException(Component c, Exception e) {
        JOptionPane.showMessageDialog(c, e.getMessage(), e.getClass().toString(), JOptionPane.ERROR_MESSAGE);
    }

    public static void renderMessage(Component c, String message, String title) {
        JOptionPane.showMessageDialog(c, message, title, JOptionPane.PLAIN_MESSAGE);
    }
    
    @Override
    public void start() {
        SwingGameFrame frame = new SwingGameFrame(_game);
        frame.setGame(_game);
        frame.pack();
        frame.setVisible(true);
    }
}
