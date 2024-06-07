package sokoban.game.pathfinding;

import java.util.Comparator;

/**
 * Used for ordering nodes in the pathfinding algorithm <br>
 * The criteria for comparison is cost
 */
public class NodeComparator implements Comparator<Node> {
    @Override
    public int compare(Node o1, Node o2) {
        return Double.compare(o1.cost, o2.cost);
    }
}
