package rsIPT.graphics;

import java.io.Serializable;

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
}
