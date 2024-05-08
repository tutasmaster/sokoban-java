package sokoban.game.entity;

import sokoban.game.Vector2;
import sokoban.game.Game;

public class Goal extends Entity {
    public Goal(Game g, Vector2 p) {
        super(g, p);
        _pushable = false;
        _walkable = true;
    }

    public Goal(Game g) {
        this(g, new Vector2(0, 0));
    }
}
