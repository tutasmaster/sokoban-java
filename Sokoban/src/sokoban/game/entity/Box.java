package sokoban.game.entity;

import sokoban.game.Coord2DInt;
import sokoban.game.Game;

public class Box extends Entity{
    public Box(Game g, Coord2DInt pos) {
        super(g,pos);
        _pushable = true;
        _walkable = false;
    }

    public Box(Game g) {
        this(g, new Coord2DInt(0,0));
    }


}
