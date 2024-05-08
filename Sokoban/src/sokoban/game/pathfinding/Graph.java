package sokoban.game.pathfinding;

import sokoban.game.Coord2DInt;
import sokoban.game.Game;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Graph {
    Game _game;
    Coord2DInt current_target;

    public Graph(Game g){
        _game = g;
    }

    Node generateNode(Coord2DInt pos){
        Node n = new Node(pos, pos.getDistanceTo(current_target));
        return n;
    }

    ArrayList<Node> findNeighbours(Node n){
        ArrayList<Node> neighbours = new ArrayList<Node>();
        Node left = generateNode(new Coord2DInt(n.pos.x-1,n.pos.y));
        Node right = generateNode(new Coord2DInt(n.pos.x+1,n.pos.y));
        Node down = generateNode(new Coord2DInt(n.pos.x,n.pos.y+1));
        Node up = generateNode(new Coord2DInt(n.pos.x,n.pos.y-1));
        if(_game.isTileWalkable(left.pos.x, left.pos.y)){
            left.parent = n;
            neighbours.add(left);
        }
        if(_game.isTileWalkable(right.pos.x, right.pos.y)){
            right.parent = n;
            neighbours.add(right);
        }
        if(_game.isTileWalkable(down.pos.x, down.pos.y)){
            down.parent = n;
            neighbours.add(down);
        }
        if(_game.isTileWalkable(up.pos.x, up.pos.y)){
            up.parent = n;
            neighbours.add(up);
        }
        return neighbours;
    }

    private ArrayList<Coord2DInt> followNodePath(Node n){
        ArrayList<Coord2DInt> path = new ArrayList<>();
        Node current = n;
        while(current.parent != null){
            path.add(current.pos);
            current = current.parent;
        }
        return path;
    }
    public ArrayList<Coord2DInt> getPath(Coord2DInt start, Coord2DInt end){
        current_target = end;
        PriorityQueue<Node> visited = new PriorityQueue<>(new NodeComparator());
        PriorityQueue<Node> to_visit = new PriorityQueue<>(new NodeComparator());
        boolean foundPath = false;
        Node starting_node = generateNode(start);
        to_visit.add(starting_node);
        while(true){
            Node current_node = to_visit.poll();
            if(current_node == null){
                break;
            }
            visited.add(current_node);
            to_visit.remove(current_node);
            ArrayList<Node> neighbours = findNeighbours(current_node);
            for(Node n : neighbours){
                if(n.pos.x == end.x && n.pos.y == end.y){
                    return followNodePath(n);
                }

                if(!to_visit.contains(n) && !visited.contains(n)){
                    n.parent = current_node;
                    to_visit.add(n);
                }
            }
        }
        return null;
    }
}
