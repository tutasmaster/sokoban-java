package sokoban.game.pathfinding;

import sokoban.game.Vector2;

import java.util.Objects;

/**
 * Node used in the pathfinding algorithm
 */
public class Node {
    public double cost;
    public Vector2 pos;
    public Node parent = null;
    
    /**
     * Node used in the pathfinding algorithm
     * @param pos position of the node
     * @param v cost of the node
     */
    public Node(Vector2 pos, double v) {
        this.pos = pos;
        this.cost = v;
    }
    
    /**
     * Used to compare two nodes<br>
     * If two nodes have the same position they are assumed to be the same
     * @param o
     * @return
     */
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
