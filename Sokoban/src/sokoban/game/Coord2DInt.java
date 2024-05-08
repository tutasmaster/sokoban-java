package sokoban.game;

import java.io.Serializable;
import java.util.Objects;

public class Coord2DInt implements Serializable {
    public int x,y;

    public Coord2DInt(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        Coord2DInt that = (Coord2DInt) o;
        return x == that.x && y == that.y;
    }

    public double getDistanceTo(Coord2DInt coord){
        return Math.sqrt(Math.pow(coord.x - x,2) + Math.pow(coord.y - y,2));
    }
}
