package sokoban.game.renderer.swing;

import java.awt.*;

/**
 * Exists from a time when all game objects were simple
 * rectangles on the screen. This goes unused in the final version
 * where every entity/tile has its own image.<br>
 * The exception is the BACKGROUND color that is still used to wipe the screen
 * when no tile is available to be drawn.
 */
public class SwingColors {
    public static final Color WALL = new Color(63, 66, 77);
    public static final Color FLOOR = new Color(111, 120, 173);
    public static final Color BACKGROUND = new Color(27, 27, 40);
    public static final Color BOX = new Color(255, 125, 83);
    public static final Color PLAYER = new Color(84, 112, 255);
    public static final Color GOAL = new Color(161, 255, 84);
}
