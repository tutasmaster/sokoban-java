package sokoban.game.entity;

import sokoban.game.Vector2;
import sokoban.game.Game;

/**
 * A box is an entity the player pushes, and is considered an objective that needs to
 * sit on top of a goal.
 */
public class Box extends Entity {
    
    /**
     * Refer to the Entity constructor for more information.
     * @param g reference to the game
     * @param pos position
     */
    public Box(Game g, Vector2 pos) {
        super(g, pos);
        _pushable = true;
        _walkable = false;
    }
    
    /**
     * Refer to the Entity constructor for more information.
     * @param g reference to the game
     */
    public Box(Game g) {
        this(g, new Vector2(0, 0));
    }


}
