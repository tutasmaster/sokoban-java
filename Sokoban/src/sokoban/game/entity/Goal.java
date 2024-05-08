package sokoban.game.entity;

import sokoban.game.Coord2DInt;
import sokoban.game.Game;

import javax.swing.text.Position;

public class Goal extends Entity {
    public Goal(Game g, Coord2DInt p) {
        super(g, p);
        _pushable = false;
        _walkable = true;
    }

    public Goal(Game g) {
        this(g, new Coord2DInt(0, 0));
    }
}
