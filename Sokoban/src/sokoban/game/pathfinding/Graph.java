package sokoban.game.pathfinding;

import sokoban.game.Vector2;
import sokoban.game.Game;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Graph {
    private final Game _game;
    private Vector2 _current_target;

    public Graph(Game game) {
        _game = game;
    }

    Node generateNode(Vector2 position) {
        return new Node(position, position.getDistanceTo(_current_target));
    }

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
    
    private void validateNode(Node current, Node next, ArrayList<Node> neighbours) {
        if (_game.isTileWalkable(next.pos.x, next.pos.y)) {
            next.parent = current;
            neighbours.add(next);
        }
    }
    
    private ArrayList<Vector2> followNodePath(Node node) {
        ArrayList<Vector2> path = new ArrayList<>();
        Node current = node;
        while (current.parent != null) {
            path.add(current.pos);
            current = current.parent;
        }
        return path;
    }

    public ArrayList<Vector2> getPath(Vector2 start, Vector2 end) {
        _current_target = end;
        PriorityQueue<Node> visited = new PriorityQueue<>(new NodeComparator());
        PriorityQueue<Node> to_visit = new PriorityQueue<>(new NodeComparator());
        boolean foundPath = false;
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
