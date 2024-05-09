package sokoban.game.entity;

import sokoban.game.Game;
import sokoban.game.Vector2;

public class Hole extends Goal{
    private boolean hasBox = false;
    public Hole(Game g, Vector2 p) {
        super(g, p);
    }

    public Hole(Game g) {
        super(g);
    }
    @Override
    public void iterate() {
        super.iterate();
        if(hasBox)
            return;
        for(int i = 0; i < _game.getEntityList().size(); i++){
            Entity e = _game.getEntityList().get(i);
            if(e.getPosition().x == _position.x
                    && e.getPosition().y == _position.y
                    && e.getClass() == Box.class){
                _game.getEntityList().remove(e);
                hasBox = true;
                return;
            }
        }
    }

    public boolean containsBox() {
        return hasBox;
    }
}
