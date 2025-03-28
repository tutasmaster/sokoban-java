package sokoban.game.entity;

import sokoban.game.Map;
import sokoban.game.Vector2;
import sokoban.game.Game;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Any object that has logic, the object exists on top of tiles.<br>
 * It's extensible and it provides a path operation on iterate() by default.
 */
public class Entity implements Serializable {

    /*
    * These definitions are used for serializing entities in the level files (.sokl)
    * They are otherwise irrelevant during gameplay
    * */
    public final static char GOAL_CHAR = 'G';
    public final static char BOX_CHAR = 'O';
    public final static char PLAYER_CHAR = '@';

    /*
    * Entities have a position, an option to be walked on or being pushed by external forces
    * A Box could get pushed by a Player
    * A Goal can be walked on top of by a Player or Box
    * */
    
    protected Vector2 _position;
    protected Boolean _pushable = false;
    protected Boolean _walkable = false;
    
    
    /**
     * We have decided that if the game saves during an automated walk,
     * such as what happens when playing with a mouse, or slipping on ice.<br>
     * The path should not be serialized and stored in the save file.
     */
    transient public ArrayList<Vector2> path;
    public void setPath(ArrayList<Vector2> p) {
        path = p;
    }
    
    /*
    Game is transient because we cannot have references in the Serialized version of the Object
    * */
    protected transient Game _game;
    
    /**
     * Entities such as the goal can be walked on by both the Player and the Boxes.
     * @return if other Entities be on top of this Entity
     */
    public Boolean isWalkable() {
        return _walkable;
    }
    
    /**
     * Entities such as the Player might push block out of the way when moving.
     * @return if other Entities can push this Entity?
     */
    public Boolean isPushable() {
        return _pushable;
    }
    
    /**
     * We give Entities a reference to the game, so that they can be aware of their surroundings.<br>
     * As it is, it's mostly used for pushing blocks from the Game class.
     * @param game used for manipulating the game state from the entity
     */
    public void setGame(Game game) {
        _game = game;
    }
    
    /**
     * We give Entities a reference to the game, so that they can be aware of their surroundings.<br>
     * As it is, it's mostly used for pushing blocks from the Game class.
     * @param game used for manipulating the game state from the entity
     * @param position sets the position of the entity in the world
     */
    public Entity(Game game, Vector2 position) {
        this._game = game;
        this._position = position;
    }
    
    /**
     * We give Entities a reference to the game, so that they can be aware of their surroundings.<br>
     * As it is, it's mostly used for pushing blocks from the Game class.
     * @param game used for manipulating the game state from the entity
     */
    public Entity(Game game) {
        this(game, new Vector2(0, 0));
    }
    
    /**
     * Moves relative to the Entity.<br>
     * Will return whether the entity has sucessfully moved or not.
     * @param x relative position
     * @param y relative position
     * @return whether the Entity has moved or not
     */
    public boolean move(int x, int y) {
        return moveInternal(x, y);
    }
    
    /**
     * Sets position of the entity as a Coord2DInt.
     * @param x position
     * @param y position
     */
    public void setPosition(int x, int y) {
        _position = new Vector2(x, y);
    }
    
    /**
     * Returns where the entity currently is as a Coord2DInt.
     * @return the position of the Entity
     */
    public Vector2 getPosition() {
        return _position;
    }
    
    /**
     * Is used whenever there is an interaction that happens between turns.<br>
     * As it is, it's only used when the player uses pathfinding, when playing with the mouse.<br>
     * --ADDED--
     * It now affects every entity, because ice can cause a similar interaction.<br>
     */
    public void iterate() {
        if (path != null && !path.isEmpty()) {
            Vector2 next_pos = path.get(path.size() - 1);
            path.remove(path.size() - 1);
            _position = next_pos;
        }
    }
    
    /**
     * Checks if the current path for the entity is not empty. <br>
     * Currently used for checking if the player is already on a path, before allowing them to
     * have direct control over the character.
     * @return whether the player is on a path or not
     */
    public boolean isPathing(){

        return !(path == null || path.isEmpty());
    }
    
    /**
     * Moves the entity if it can walk, it will also generate a path if the entity has
     * somehow slipped on a tile of ice.
     * @param x position
     * @param y position
     * @return if the entity was able to walk
     */
    protected boolean moveInternal(int x, int y) {
        Map.Tile tile = _game.getMap().getTile(_position.x + x, _position.y + y);
        if (_game.isTileWalkable(_position.x + x, _position.y + y)) {
            _position = new Vector2(_position.x + x, _position.y + y);
            //Calculate slip on ice
            if(_game.isTileSlippery(_position.x, _position.y)){
                path = new ArrayList<>();
                int ice_count = 1;
                while(_game.isTileWalkable(_position.x + (x*ice_count), _position.y + (y*ice_count))){
                    path.add(0,new Vector2(_position.x + (x*ice_count), _position.y + (y*ice_count)));
                    if(!_game.isTileSlippery(_position.x + (x*ice_count), _position.y + (y*ice_count))){
                        break;
                    }
                    ice_count++;
                }
            }
            return true;
        }
        return false;
    }
}
