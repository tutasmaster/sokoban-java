package sokoban.game.entity;

import sokoban.game.Vector2;
import sokoban.game.Game;

import java.util.ArrayList;

public class Player extends Entity {


    public Player(Game g) {
        super(g);

    }

    public Player(Game g, Vector2 pos) {
        super(g, pos);
    }

    @Override
    public boolean move(int x, int y) {
        if (!moveInternal(x, y)) {
            _game.pushEntity(_position.x + x, _position.y + y, x, y);
            return false;
        }
        return true;
    }

    @Override
    public void iterate() {
        super.iterate();
    }
}
