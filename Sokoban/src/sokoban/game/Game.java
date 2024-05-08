package sokoban.game;

import sokoban.game.entity.*;
import sokoban.game.pathfinding.Graph;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static sokoban.game.Map.GenerateBoxMap;

public class Game {
    public final String SAVE_TYPE = ".sok";
    public final String[] levels = {"level01.sokl", "level02.sokl"};
    public int current_level = 0;
    private boolean _running;

    public List<Entity> get_entity_list() {
        return _entity_list;
    }

    private List<Entity> _entity_list;
    String picked_entity = null;
    String picked_tile = null;
    Coord2DInt cursor = null;

    public Player get_player() throws NullPointerException {
        for (Entity e : _entity_list) {
            if (e.getClass() == Player.class) {
                return (Player) e;
            }
        }
        throw new NullPointerException("Player not found.");
    }

    public Map get_map() {
        return _map;
    }

    private Map _map;

    public Game() {

    }


    public boolean is_running() {
        return _running;
    }

    public void set_running(boolean _running) {
        this._running = _running;
    }

    public enum INPUT_RESULT {
        NONE,
        NEXT_LEVEL,
        END
    }

    public INPUT_RESULT iterate() throws Exception {
        for (Entity e : _entity_list) {
            e.iterate();
        }
        if (goalsLeft() == 0) {
            if(loadNextLevel()){
                return INPUT_RESULT.NEXT_LEVEL;
            }else{
                return INPUT_RESULT.END;
            }
        }
        return INPUT_RESULT.NONE;
    }

    public boolean isTileWalkable(int x, int y) {
        if (x > -1 && x < _map.get_size_x() && y > -1 && y < _map.get_size_y()) {
            if (_map.getTile(x, y) == Map.Tile.Floor) {
                for (Entity e : _entity_list) {
                    if (e.getPosition().x == x && e.getPosition().y == y && !e.is_walkable()) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public INPUT_RESULT input(String input) throws Exception {
        String i = input.toLowerCase();
        switch (i) {
            case "l":
            case "left":
                get_player().Move(-1, 0);
                break;
            case "r":
            case "right":
                get_player().Move(1, 0);
                break;
            case "u":
            case "up":
                get_player().Move(0, -1);
                break;
            case "d":
            case "down":
                get_player().Move(0, 1);
                break;
            case "save":
                save();
                break;
            case "load":
                load();
                break;
            case "place":
                place();
                break;
            case "":
                return iterate();
        }

        return INPUT_RESULT.NONE;
    }

    private void place() {
        if (picked_entity != null) {
            remove_entities_at(cursor.x, cursor.y);
            switch (picked_entity) {
                case "Box":
                    _map.setTile(cursor.x, cursor.y, Map.Tile.Floor);
                    _entity_list.add(new Box(this, cursor));
                    break;
                case "Goal":
                    _map.setTile(cursor.x, cursor.y, Map.Tile.Floor);
                    _entity_list.add(new Goal(this, cursor));
                    break;
                case "Erase":
                    break;
            }
        } else if (picked_tile != null) {
            switch (picked_tile) {
                case "Empty":
                    _map.setTile(cursor.x, cursor.y, Map.Tile.Empty);
                    break;
                case "Wall":
                    _map.setTile(cursor.x, cursor.y, Map.Tile.Wall);
                    break;
                case "Floor":
                    _map.setTile(cursor.x, cursor.y, Map.Tile.Floor);
                    break;
            }
        }
    }

    private void remove_entities_at(int x, int y) {
        _entity_list.removeIf(e -> (e.getPosition().x == x && e.getPosition().y == y && e.getClass() != Player.class));
    }

    public String save_file = "save" + SAVE_TYPE;

    private void save() throws Exception {
        FileOutputStream file = new FileOutputStream(save_file);
        ObjectOutputStream stream = new ObjectOutputStream(file);
        stream.writeObject(_map);
        stream.writeObject(_entity_list);
        stream.close();
        file.close();
    }

    public void saveLevel(String file_name) throws Exception {
        BufferedWriter stream = new BufferedWriter(new FileWriter(file_name));
        StringBuilder result = _map.parse();
        for (Entity e : _entity_list) {
            char replacement = '.';
            System.out.println(e.getClass().getSimpleName());
            switch (e.getClass().getSimpleName()) {
                case "Player" -> replacement = '@';
                case "Box" -> replacement = 'O';
                case "Goal" -> replacement = 'G';
            }
            Coord2DInt pos = e.getPosition();
            result.setCharAt(_map.parsedCoordToIdx(pos.x, pos.y), replacement);
        }
        stream.write(result.toString());
        stream.close();
    }

    private InputStreamReader getLocalLevel(String level) throws Exception {
        InputStream stream = Map.class.getResourceAsStream(level);
        return new InputStreamReader(stream);
    }

    private InputStreamReader getExternalLevel(String level) throws Exception {
        return new FileReader(level);
    }

    public void loadExternalLevel(String level) throws Exception {
        loadLevel(getExternalLevel(level));
    }

    private void loadLevel(InputStreamReader streamReader) throws Exception {
        BufferedReader stream = new BufferedReader(streamReader);
        String line;
        StringBuilder result = new StringBuilder();
        int x = 0;
        int y = 0;
        ArrayList<Entity> e_list = new ArrayList<Entity>();
        while ((line = stream.readLine()) != null) {
            result.append(line);
            x = 0;
            for (char c : line.toCharArray()) {
                if (c == Player.PLAYER_CHAR) {
                    e_list.add(new Player(this, new Coord2DInt(x, y)));
                } else if (c == Player.GOAL_CHAR) {
                    e_list.add(new Goal(this, new Coord2DInt(x, y)));
                } else if (c == Player.BOX_CHAR) {
                    e_list.add(new Box(this, new Coord2DInt(x, y)));
                }
                x++;
            }
            y++;
        }
        _map = Map.fromString(x, y, result.toString());
        _entity_list = e_list;

        stream.close();
    }

    private void load() throws Exception {
        FileInputStream file = new FileInputStream(save_file);
        ObjectInputStream stream = new ObjectInputStream(file);
        _map = (Map) stream.readObject();
        _entity_list = (List<Entity>) stream.readObject();
        for (Entity e : _entity_list) {
            e.setGame(this);
        }
        stream.close();
        file.close();
    }

    public void startDebugLevel() {
        _map = Map.GenerateTestMap();
        _entity_list = new ArrayList<Entity>();
        _entity_list.add(new Player(this));
        _entity_list.add(new Goal(this));
    }

    public void start() {
        //startDebugLevel();
        try {
            loadLocalLevel(levels[current_level]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        _running = true;
    }

    private void loadLocalLevel(String level) throws Exception {
        loadLevel(getLocalLevel("levels/" + level));
    }

    public void pushEntity(int x, int y, int x1, int y1) {
        for (Entity e : _entity_list) {
            if (e.getPosition().x == x && e.getPosition().y == y && e.is_pushable()) {
                e.Move(x1, y1);
            }
        }
    }

    public boolean loadNextLevel() throws Exception {
        current_level++;
        if(current_level >= levels.length)
            return false;
        loadLocalLevel(levels[current_level]);
        return true;
    }

    public int goalsLeft() throws Exception {
        List<Goal> goals = new ArrayList<Goal>();
        int goals_left = 0;
        for (Entity e : _entity_list) {
            if (e.getClass() != Goal.class) {
                continue;
            }
            goals_left += 1;
            goals.add((Goal) e);
        }

        for (Goal g : goals) {
            for (Entity e : _entity_list) {
                if (e.getClass() != Box.class) {
                    continue;
                }
                if (e.getPosition().equals(g.getPosition())) {
                    goals_left -= 1;
                }
            }
        }
        if (goals_left < 0) {
            throw new IllegalStateException("There are somehow more boxes placed on goals than goals themselves.");
        }
        return goals_left;
    }

    public void createMap(int width, int height) {
        _map = GenerateBoxMap(width, height);
    }


    public void setPickedEntity(String selectedValue) {
        picked_entity = selectedValue;
        picked_tile = null;
    }

    public void setPickedTile(String selectedValue) {
        picked_tile = selectedValue;
        picked_entity = null;
    }

    public void setCursorPos(Coord2DInt cursorPos) {
        cursor = cursorPos;
    }

    public Coord2DInt getCursorPos() {
        return cursor;
    }

    public void walkTo(Coord2DInt cursorPos) {
        if(cursorPos.getDistanceTo(get_player().getPosition()) < 1.05){
            get_player().Move(cursorPos.x - get_player().getPosition().x, cursorPos.y - get_player().getPosition().y);
        }
        Graph g = new Graph(this);
        ArrayList<Coord2DInt> path = g.getPath(get_player().getPosition(), cursorPos);
        get_player().setPath(path);
    }


}
