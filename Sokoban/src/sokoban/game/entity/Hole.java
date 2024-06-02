package sokoban.game.entity;

import sokoban.game.Game;
import sokoban.game.Vector2;

/**
 * Hole is a place a box can fall into.<br>
 * Once a box is placed on a hole, it will never be moved again, and the hole is
 * completely walkable in that state.<br>
 * This is done by removing the box.
 */
public class Hole extends Goal{
    private boolean has_box = false;
    /**
     * Refer to the Entity constructor for more information.
     * @param g reference to the game
     */
    public Hole(Game g, Vector2 p) {
        super(g, p);
    }
    /**
     * Refer to the Entity constructor for more information.
     * @param g reference to the game
     */
    public Hole(Game g) {
        super(g);
    }
    
    /**
     * During an iteration, if a box exists, then we remove it and declare this goal as
     * filled by using the "has_box" variable.
     */
    @Override
    public void iterate() {
        super.iterate();
        if(has_box)
            return;
        for(int i = 0; i < _game.getEntityList().size(); i++){
            Entity e = _game.getEntityList().get(i);
            if(e.getPosition().x == _position.x
                    && e.getPosition().y == _position.y
                    && e.getClass() == Box.class){
                has_box = true;
                _game.removeEntity(e);
                break;
            }
        }
    }
    
    /**
     * Returns if the goal has been filled with a box.
     * @return whether a box has been set
     */
    public boolean containsBox() {
        return has_box;
    }
}
