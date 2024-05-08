package sokoban.game.entity;

import sokoban.game.Vector2;
import sokoban.game.Game;

public class Box extends Entity {
    public Box(Game g, Vector2 pos) {
        super(g, pos);
        _pushable = true;
        _walkable = false;
    }

    public Box(Game g) {
        this(g, new Vector2(0, 0));
    }


}
