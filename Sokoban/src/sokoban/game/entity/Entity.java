package sokoban.game.entity;

import sokoban.game.Coord2DInt;
import sokoban.game.Game;

import java.io.Serializable;

public class Entity implements Serializable {

    public static char GOAL_CHAR = 'G';
    public static char BOX_CHAR = 'O';
    public static char PLAYER_CHAR = '@';

    protected Coord2DInt _position;

    public Entity(Game g, Coord2DInt _position) {
        this._game = g;
        this._position = _position;
    }

    public Entity(Game g) {
        this(g, new Coord2DInt(0, 0));
    }

    //Relative movement function
    protected boolean move(int x, int y) {
        if (_game.isTileWalkable(_position.x + x, _position.y + y)) {
            _position = new Coord2DInt(_position.x + x, _position.y + y);
            return true;
        }
        return false;
    }

    public void Move(int x, int y) {
        move(x, y);
    }

    public Boolean is_walkable() {
        return _walkable;
    }

    public Boolean is_pushable() {
        return _pushable;
    }

    protected Boolean _pushable = false;
    protected Boolean _walkable = false;

    protected transient Game _game;

    public void setGame(Game game) {
        _game = game;
    }

    public void setPosition(int x, int y) {
        _position = new Coord2DInt(x, y);
    }

    public Coord2DInt getPosition() {
        return _position;
    }

    public void iterate() {
    }
}
