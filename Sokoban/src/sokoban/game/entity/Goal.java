package sokoban.game.entity;

import sokoban.game.Vector2;
import sokoban.game.Game;

/**
 * Base Goal class, it exists for easy detection by the Game.goalsLeft() function.<br>
 * It's also extended by the Hole class.
 */
public class Goal extends Entity {
    /**
     * Refer to the Entity constructor for more information.
     * @param g reference to the game
     */
    public Goal(Game g, Vector2 p) {
        super(g, p);
        _pushable = false;
        _walkable = true;
    }
    
    /**
     * Refer to the Entity constructor for more information.
     * @param g reference to the game
     */
    public Goal(Game g) {
        this(g, new Vector2(0, 0));
    }
}
