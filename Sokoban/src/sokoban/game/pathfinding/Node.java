package sokoban.game.pathfinding;

import sokoban.game.Coord2DInt;

import java.util.Objects;
import java.util.PriorityQueue;

public class Node {
    public double cost;
    public Coord2DInt pos;
    public Node parent = null;
    public Node(Coord2DInt pos, double v) {
        this.pos = pos;
        this.cost = v;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(pos, node.pos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pos);
    }
}
