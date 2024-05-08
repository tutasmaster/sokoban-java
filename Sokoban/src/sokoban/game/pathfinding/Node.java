package sokoban.game.pathfinding;

import sokoban.game.Vector2;

import java.util.Objects;

public class Node {
    public double cost;
    public Vector2 pos;
    public Node parent = null;

    public Node(Vector2 pos, double v) {
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
