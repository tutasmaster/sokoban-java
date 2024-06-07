package sokoban.game.pathfinding;

import sokoban.game.Vector2;
import sokoban.game.Game;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * The graph handles pathfinding in the game
 */
public class Graph {
    private final Game _game;
    private Vector2 _current_target;
    
    /**
     * The graph handles pathfinding in the game
     * @param game A sokoban game instance
     */
    public Graph(Game game) {
        _game = game;
    }
    
    /**
     * Generates a node for a given position, this node's cost is set to the distance to the target using
     * _current_target
     * @param position of the node
     * @return the Node for pathfinding
     */
    Node generateNode(Vector2 position) {
        return new Node(position, position.getDistanceTo(_current_target));
    }
    
    /**
     * Generates a list of all neighbour nodes, this is used for pathfinding
     * @param node that has the position we want to find the neighbours of
     * @return a list of every neighbour
     */
    ArrayList<Node> findNeighbours(Node node) {
        ArrayList<Node> neighbours = new ArrayList<>();
        Node left = generateNode(new Vector2(node.pos.x - 1, node.pos.y));
        Node right = generateNode(new Vector2(node.pos.x + 1, node.pos.y));
        Node down = generateNode(new Vector2(node.pos.x, node.pos.y + 1));
        Node up = generateNode(new Vector2(node.pos.x, node.pos.y - 1));
        validateNode(node, left, neighbours);
        validateNode(node, right, neighbours);
        validateNode(node, up, neighbours);
        validateNode(node, down, neighbours);
        return neighbours;
    }
    
    /**
     * Checks the conditions for a node existing and adds it to the neighbours array
     *
     * Used by the findNeighbours(Node node) function
     *
     * In this case, it avoids non-walkable tiles and slippery tiles.
     * @param current node
     * @param next neighbour
     * @param neighbours neighbour list
     */
    private void validateNode(Node current, Node next, ArrayList<Node> neighbours) {
        //We do not want the player to just walk through the ice.
        if (   _game.isTileWalkable(next.pos.x, next.pos.y)
                && !_game.isTileSlippery(next.pos.x, next.pos.y)) {
            next.parent = current;
            neighbours.add(next);
        }
    }
    
    /**
     * Gets the path from a node
     *
     * It creates an array with every position in the path as a Vector2
     *
     * This is done by backtracking from the final node, until we reach the starting node
     *
     * The resulting path will be backwards, as in the first position in the array will actually be
     * the final position in the path
     * @param node final node of the path
     * @return the path as an ArrayList<Vector2>
     */
    private ArrayList<Vector2> followNodePath(Node node) {
        ArrayList<Vector2> path = new ArrayList<>();
        Node current = node;
        while (current.parent != null) {
            path.add(current.pos);
            current = current.parent;
        }
        return path;
    }
    
    
    /**
     * Get a path for a given start and end point <br>
     * This functions uses the game from the constructor and uses it to generate a path <br>
     * It does this by creating a graph and keeping a list of visited nodes <br>
     * When iterating through the nodes, it will keep track of the already visited nodes
     * and give them the appropriate parents until it hits the final position. <br>
     * It then backtracks through the end nodes until it reaches the beginning, thus creating
     * an ArrayList with every position
     * @param start position
     * @param end position
     * @return ArrayList of the path
     */
    public ArrayList<Vector2> getPath(Vector2 start, Vector2 end) {
        _current_target = end;
        PriorityQueue<Node> visited = new PriorityQueue<>(new NodeComparator());
        PriorityQueue<Node> to_visit = new PriorityQueue<>(new NodeComparator());
        Node starting_node = generateNode(start);
        to_visit.add(starting_node);
        while (true) {
            Node current_node = to_visit.poll();
            if (current_node == null) {
                break;
            }
            visited.add(current_node);
            to_visit.remove(current_node);
            ArrayList<Node> neighbours = findNeighbours(current_node);
            for (Node neighbour : neighbours) {
                if (neighbour.pos.x == end.x && neighbour.pos.y == end.y) {
                    return followNodePath(neighbour);
                }

                if (!to_visit.contains(neighbour) && !visited.contains(neighbour)) {
                    neighbour.parent = current_node;
                    to_visit.add(neighbour);
                }
            }
        }
        return null;
    }
}
