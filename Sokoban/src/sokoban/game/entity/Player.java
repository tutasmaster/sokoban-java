package sokoban.game.entity;

import sokoban.game.Vector2;
import sokoban.game.Game;

import java.util.ArrayList;

/**
 * The player represents the position of the player in the world.<br>
 * It goes in the same entity_list as every other entity, and it
 * has logic for pushing blocsk in the move(int x, int y) function.
 */
public class Player extends Entity {
    
    /**
     * Refer to the Entity constructor for more information.
     * @param g reference to the game
     */
    public Player(Game g) {
        super(g);

    }
    
    /**
     * Refer to the Entity constructor for more information.
     * @param g reference to the game
     */
    public Player(Game g, Vector2 pos) {
        super(g, pos);
    }
    
    /**
     * Unless the player is following a path, it might try to move into a box.<br>
     * In that case, we want to push the box in the same direction of the player
     * @param x relative position
     * @param y relative position
     * @return whether the player has moved or not
     */
    @Override
    public boolean move(int x, int y) {
        if(isPathing()){
            return false;
        }
        if (!moveInternal(x, y)) {
            _game.pushEntity(_position.x + x, _position.y + y, x, y);
            return false;
        }
        return true;
    }
}
