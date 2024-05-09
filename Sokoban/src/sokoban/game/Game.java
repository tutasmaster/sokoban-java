package sokoban.game;

import sokoban.game.entity.*;
import sokoban.game.pathfinding.Graph;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static sokoban.game.Map.generateBoxMap;

public class Game {
	public static final String SAVE_TYPE = ".sok";
	public final String[] levels = {"level01.sokl", "level02.sokl", "wacky.sokl", "suspect.sokl", "lost.sokl"};
	
	private Stack<byte[]> _undo_states = new Stack<>();
	private Stack<byte[]> _redo_states = new Stack<>();
	public int currentLevel = 0;
	private boolean _running;
	
	public List<Entity> getEntityList() {
		return _entity_list;
	}
	
	private List<Entity> _entity_list;
	private String _picked_entity = null;
	String _picked_tile = null;
	Vector2 _cursor = null;
	
	public Player getPlayer() throws NullPointerException {
		for (Entity entity : _entity_list) {
			if (entity.getClass() == Player.class) {
				return (Player) entity;
			}
		}
		throw new NullPointerException("Player not found.");
	}
	
	public Map getMap() {
		return _map;
	}
	
	private Map _map;
	
	public Game() {
	
	}
	
	
	public boolean isRunning() {
		return _running;
	}
	
	public void setRunning(boolean _running) {
		this._running = _running;
	}
	
	public enum INPUT_RESULT {
		NONE,
		NEXT_LEVEL,
		PUSHED,
		MOVED,
		END
	}
	
	public INPUT_RESULT iterate() throws Exception {
		for (Entity entity : _entity_list) {
			entity.iterate();
		}
		
		if (goalsLeft() == 0) {
			if (loadNextLevel()) {
				return INPUT_RESULT.NEXT_LEVEL;
			} else {
				return INPUT_RESULT.END;
			}
		} else if (hasPushed) {
			return INPUT_RESULT.PUSHED;
		}
		return INPUT_RESULT.NONE;
	}
	
	public boolean isTileWalkable(int x, int y) {
		if (x > -1 && x < _map.getWidth() && y > -1 && y < _map.getHeight()) {
			if (_map.getTile(x, y) == Map.Tile.FLOOR || _map.getTile(x,y) == Map.Tile.ICE) {
				for (Entity entity : _entity_list) {
					if (entity.getPosition().x == x && entity.getPosition().y == y && !entity.isWalkable()) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}
	
	public boolean isTileSlippery(int x, int y) {
		if (x > -1 && x < _map.getWidth() && y > -1 && y < _map.getHeight()) {
			if (_map.getTile(x, y) == Map.Tile.ICE) {
				return true;
			}
		}
		return false;
	}
	
	public INPUT_RESULT input(String input) throws Exception {
		hasPushed = false;
		String i = input.toLowerCase();
		switch (i) {
			case "l":
			case "left":
				_redo_states = new Stack<>();
				saveState();
				getPlayer().move(-1, 0);
				if (hasPushed) {
					return INPUT_RESULT.PUSHED;
				}
				break;
			case "r":
			case "right":
				_redo_states = new Stack<>();
				saveState();
				getPlayer().move(1, 0);
				if (hasPushed) {
					return INPUT_RESULT.PUSHED;
				}
				break;
			case "u":
			case "up":
				_redo_states = new Stack<>();
				saveState();
				getPlayer().move(0, -1);
				if (hasPushed) {
					return INPUT_RESULT.PUSHED;
				}
				break;
			case "d":
			case "down":
				_redo_states = new Stack<>();
				saveState();
				getPlayer().move(0, 1);
				if (hasPushed) {
					return INPUT_RESULT.PUSHED;
				}
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
			case "undo":
				loadState();
				break;
			case "redo":
				loadRedoState();
				break;
			case "":
				return iterate();
		}
		
		return INPUT_RESULT.NONE;
	}
	
	private void place() {
		if (_picked_entity != null) {
			removeEntitiesAt(_cursor.x, _cursor.y);
			switch (_picked_entity) {
				case "Box":
					_map.setTile(_cursor.x, _cursor.y, Map.Tile.FLOOR);
					_entity_list.add(new Box(this, _cursor));
					break;
				case "Goal":
					_map.setTile(_cursor.x, _cursor.y, Map.Tile.FLOOR);
					_entity_list.add(new Goal(this, _cursor));
					break;
				case "Erase":
					break;
			}
		} else if (_picked_tile != null) {
			switch (_picked_tile) {
				case "Empty":
					_map.setTile(_cursor.x, _cursor.y, Map.Tile.EMPTY);
					break;
				case "Wall":
					_map.setTile(_cursor.x, _cursor.y, Map.Tile.WALL);
					break;
				case "Floor":
					_map.setTile(_cursor.x, _cursor.y, Map.Tile.FLOOR);
					break;
				case "Ice":
					_map.setTile(_cursor.x, _cursor.y, Map.Tile.ICE);
					break;
			}
		}
	}
	
	private void removeEntitiesAt(int x, int y) {
		_entity_list.removeIf(entity -> (entity.getPosition().x == x && entity.getPosition().y == y && entity.getClass() != Player.class));
	}
	
	public String save_file = "save" + SAVE_TYPE;
	
	private void save() throws Exception {
		FileOutputStream file = new FileOutputStream(save_file);
		save(file);
		file.close();
	}
	
	private void saveState() throws Exception {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		save(outputStream);
		_undo_states.push(outputStream.toByteArray());
		outputStream.close();
	}
	
	private void loadState() throws Exception {
		if (_undo_states.size() == 0) {
			return;
		}
		byte[] state = _undo_states.pop();
		ByteArrayInputStream inputStream = new ByteArrayInputStream(state);
		load(inputStream);
		inputStream.close();
		_redo_states.push(state);
	}
	
	private void loadRedoState() throws Exception {
		if (_redo_states.size() == 0) {
			return;
		}
		byte[] state = _redo_states.pop();
		ByteArrayInputStream inputStream = new ByteArrayInputStream(state);
		load(inputStream);
		inputStream.close();
		_undo_states.push(state);
	}
	
	private void save(OutputStream output) throws Exception {
		ObjectOutputStream stream = new ObjectOutputStream(output);
		
		stream.writeObject(_map);
		stream.writeObject(_entity_list);
		stream.close();
	}
	
	public void saveLevel(String file_name) throws Exception {
		BufferedWriter stream = new BufferedWriter(new FileWriter(file_name));
		StringBuilder result = _map.parse();
		for (Entity entity : _entity_list) {
			char replacement = '.';
			System.out.println(entity.getClass().getSimpleName());
			switch (entity.getClass().getSimpleName()) {
				case "Player":
					replacement = '@';
					break;
				case "Box":
					replacement = 'O';
					break;
				case "Goal":
					replacement = 'G';
					break;
			}
			Vector2 position = entity.getPosition();
			result.setCharAt(_map.parsedCoordToIdx(position.x, position.y), replacement);
		}
		stream.write(result.toString());
		stream.close();
	}
	
	private InputStreamReader getLocalLevel(String level){
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
		ArrayList<Entity> entity_list = new ArrayList<>();
		while ((line = stream.readLine()) != null) {
			result.append(line);
			x = 0;
			for (char c : line.toCharArray()) {
				if (c == Player.PLAYER_CHAR) {
					entity_list.add(new Player(this, new Vector2(x, y)));
				} else if (c == Player.GOAL_CHAR) {
					entity_list.add(new Goal(this, new Vector2(x, y)));
				} else if (c == Player.BOX_CHAR) {
					entity_list.add(new Box(this, new Vector2(x, y)));
				}
				x++;
			}
			y++;
		}
		_map = Map.fromString(x, y, result.toString());
		_entity_list = entity_list;
		
		stream.close();
	}
	
	private void load(InputStream input) throws Exception {
		ObjectInputStream stream = new ObjectInputStream(input);
		_map = (Map) stream.readObject();
		_entity_list = (List<Entity>) stream.readObject();
		for (Entity entity : _entity_list) {
			entity.setGame(this);
		}
		stream.close();
	}
	
	private void load() throws Exception {
		FileInputStream file = new FileInputStream(save_file);
		load(file);
		file.close();
	}
	
	public void startDebugLevel() {
		_map = Map.generateTestMap();
		_entity_list = new ArrayList<>();
		_entity_list.add(new Player(this));
		_entity_list.add(new Goal(this));
	}
	
	public void start() {
		try {
			loadLocalLevel(levels[currentLevel]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		_running = true;
	}
	
	private void loadLocalLevel(String level) throws Exception {
		loadLevel(getLocalLevel("levels/" + level));
	}
	
	boolean hasPushed = false;
	
	public void pushEntity(int x, int y, int x1, int y1) {
		for (Entity entity : _entity_list) {
			if (entity.getPosition().x == x && entity.getPosition().y == y && entity.isPushable()) {
				entity.move(x1, y1);
				hasPushed = true;
			}
		}
	}
	
	public boolean loadNextLevel() throws Exception {
		if (currentLevel >= levels.length - 1)
			return false;
		
		currentLevel++;
		loadLocalLevel(levels[currentLevel]);
		return true;
	}
	
	public int goalsLeft(){
		List<Goal> goals = new ArrayList<>();
		int goalsLeft = 0;
		for (Entity entity : _entity_list) {
			if (entity.getClass() != Goal.class) {
				continue;
			}
            goalsLeft++;
			goals.add((Goal) entity);
		}
		
		for (Goal g : goals) {
			for (Entity entity : _entity_list) {
				if (entity.getClass() != Box.class) {
					continue;
				}
				if (entity.getPosition().equals(g.getPosition())) {
                    goalsLeft--;
				}
			}
		}
		if (goalsLeft < 0) {
			throw new IllegalStateException("There are somehow more boxes placed on goals than goals themselves.");
		}
		return goalsLeft;
	}
	
	public void createMap(int width, int height) {
		_map = generateBoxMap(width, height);
	}
	
	
	public void setPickedEntity(String selectedValue) {
		_picked_entity = selectedValue;
		_picked_tile = null;
	}
	
	public void setPickedTile(String selectedValue) {
		_picked_tile = selectedValue;
		_picked_entity = null;
	}
	
	public void setCursorPos(Vector2 cursorPos) {
		_cursor = cursorPos;
	}
	
	public Vector2 getCursorPos() {
		return _cursor;
	}
	
	public INPUT_RESULT walkTo(Vector2 cursorPosition) throws Exception {
		hasPushed = false;
        Vector2 playerPosition = getPlayer().getPosition();
		saveState();
		if (cursorPosition.getDistanceTo(playerPosition) < 1.05) {
			getPlayer().move(cursorPosition.x - playerPosition.x, cursorPosition.y - playerPosition.y);
			if (hasPushed)
				return INPUT_RESULT.PUSHED;
			else
				return INPUT_RESULT.NONE;
		}
		Graph g = new Graph(this);
		ArrayList<Vector2> path = g.getPath(getPlayer().getPosition(), cursorPosition);
		getPlayer().setPath(path);
		return INPUT_RESULT.NONE;
	}
	
	
}
