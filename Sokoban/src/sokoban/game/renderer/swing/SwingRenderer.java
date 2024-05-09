package sokoban.game.renderer.swing;

import sokoban.game.Game;
import sokoban.game.renderer.Renderer;

import javax.swing.*;

public class SwingRenderer implements Renderer {
    public SwingRenderer(Game game) {
        setGame(game);
    }
    
    private Game _game;

    public void setGame(Game game) {
        _game = game;
    }

    
    public static void renderException(Exception e) {
        //We use a JFrame to prevent the exception from not being displayed on top of
        //other UI Elements
        JFrame jframe=new JFrame();
        jframe.setAlwaysOnTop(true);
        JOptionPane.showMessageDialog(jframe, e.getMessage(), e.getClass().toString(), JOptionPane.ERROR_MESSAGE);
    }

    public static void renderMessage(String message, String title) {
        //We use a JFrame to prevent the message from not being displayed on top of
        //other UI Elements
        JFrame jframe=new JFrame();
        jframe.setAlwaysOnTop(true);
        JOptionPane.showMessageDialog(jframe, message, title, JOptionPane.PLAIN_MESSAGE);
    }
    
    @Override
    public void start() {
        SwingGameFrame frame = new SwingGameFrame(_game);
        frame.setGame(_game);
        frame.pack();
        frame.setVisible(true);
    }
}
