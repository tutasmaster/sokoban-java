package sokoban.game.entity;

import sokoban.game.Vector2;
import sokoban.game.Game;

import java.util.ArrayList;

public class Player extends Entity {

    /*
    * We have decided that if the game saves during an automated player walk,
    * such as what happens when playing with a mouse.
    * The path should not be serialized and stored in the save file.
     */
    transient public ArrayList<Vector2> path;

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
        if (path != null && !path.isEmpty()) {
            Vector2 next_pos = path.get(path.size() - 1);
            path.remove(path.size() - 1);
            _position = next_pos;
        }
    }

    public void setPath(ArrayList<Vector2> p) {
        path = p;
    }
}
