package sokoban.game.entity;

import sokoban.game.Coord2DInt;
import sokoban.game.Game;
import sokoban.game.pathfinding.Graph;

import java.util.ArrayList;

public class Player extends Entity {

    transient public ArrayList<Coord2DInt> path;
    public Player(Game g) {
        super(g);

    }

    public Player(Game g, Coord2DInt pos) {
        super(g,pos);
    }

    @Override
    public void Move(int x, int y){
        if(!move(x,y)){
            _game.pushEntity(_position.x + x, _position.y + y, x, y);
        }
    }
    @Override
    public void iterate() {
        super.iterate();
        if(path != null && !path.isEmpty()){
            Coord2DInt next_pos = path.get(path.size()-1);
            path.remove(path.size()-1);
            _position = next_pos;
        }
    }

    public void setPath(ArrayList<Coord2DInt> p) {
        path = p;
    }
}
