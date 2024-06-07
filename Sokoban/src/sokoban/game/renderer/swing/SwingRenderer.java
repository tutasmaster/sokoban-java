package sokoban.game.renderer.swing;

import sokoban.game.Game;
import sokoban.game.renderer.Renderer;

import javax.swing.*;

/**
 * Renders the game to a Java Swing frame.
 */
public class SwingRenderer implements Renderer {
    
    /**
     * Constructor for the renderer, it gets a game reference to modify.
     * @param game reference to the game
     */
    public SwingRenderer(Game game) {
        setGame(game);
    }
    
    private Game _game;
    
    /**
     * Sets the game reference. It's the core of the renderers use.
     */
    public void setGame(Game game) {
        _game = game;
    }
    
    /**
     * Show a panel with an error message. This is used for exceptions and saves writing
     * JFrame initialization code on every exception handler.
     * @param e exception
     */
    public static void renderException(Exception e) {
        //We use a JFrame to prevent the exception from not being displayed on top of
        //other UI Elements
        JFrame jframe=new JFrame();
        jframe.setAlwaysOnTop(true);
        JOptionPane.showMessageDialog(jframe, e.getMessage(), e.getClass().toString(), JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Show a panel with a message. This is used for game results such as beating a level and saves
     * JFrame initialization code on every exception handler.
     * @param message
     * @param title
     */
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
