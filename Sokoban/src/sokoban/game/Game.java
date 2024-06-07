package sokoban.game;

import sokoban.game.entity.*;
import sokoban.game.pathfinding.Graph;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static sokoban.game.Map.generateBoxMap;

/**
 * The class that contains the entire game state.
 */
public class Game {
	public String last_move = "";
	public boolean playback = false;
	public boolean isForcedPlayback = false;

	public int lastUndoStateLevel = 0;
	public int currentUndoState = 0;

	public void startPlayback(){
		playback = true;
		isForcedPlayback = false;
		currentUndoState = lastUndoStateLevel;

	}

	public void startForcedPlayback(){
		playback = true;
		isForcedPlayback = true;
		currentUndoState = lastUndoStateLevel;

	}

	public String getMoveFromUndoState(byte[] state) throws Exception{

		ByteArrayInputStream input = new ByteArrayInputStream(state);
		ObjectInputStream stream = new ObjectInputStream(input);
		Map m = (Map) stream.readObject();
		List<Entity> e = (List<Entity>) stream.readObject();
		String r = (String) stream.readObject();
		stream.close();
		return r;
	}
	public String getMoveFromUndoState(int pos) throws Exception{
		return getMoveFromUndoState(_undo_states.elementAt(pos));
	}

	public void loadUndo(int pos)throws Exception {
		byte[] state = _undo_states.elementAt(pos);
		ByteArrayInputStream inputStream = new ByteArrayInputStream(state);
		load(inputStream);
		inputStream.close();
		_redo_states.push(state);
	}

	public int getUndoStateSize(){
		return _undo_states.size();
	}
	/**
	 * Defines the maximum amount of undo states.
	 */
	public static final int MAXIMUM_UNDO = 50;

	/**
	 * Defines the filetype for all savefiles.
	 */
	public static final String SAVE_TYPE = ".sok";
	/**
	 * Where the game should save.
	 */
	public String save_file = "save" + SAVE_TYPE;
	/**
	 * Defines what levels are loaded and in what order.<br>
	 * The final level will always trigger a congratulations prompt.
	 */
	public final String[] levels = {"level01.sokl", "level02.sokl", "wacky.sokl", "suspect.sokl", "lost.sokl"};
	/**
	 * Defines what level is currently being accessed.
	 */
	public int currentLevel = 0;


	private Stack<byte[]> _undo_states = new Stack<>();
	private Stack<byte[]> _redo_states = new Stack<>();

	private boolean _running;
	private boolean _edit_mode = false;
	
	/**
	 * Gets the list of all the entities currently in-game.
	 * @return list of entities
	 */
	public List<Entity> getEntityList() {
		return _entity_list;
	}
	/**
	 * Adds a entity to be removed in the next iterate()<br>
	 * It does so by adding it to a list to be erased later
	 * @param e entity
	 */
	public void removeEntity(Entity e) {
		_entity_removal_list.add(e);
	}
	private List<Entity> _entity_removal_list = new ArrayList<>();
	private List<Entity> _entity_list;

	private String _picked_entity = null;
	private String _picked_tile = null;
	private Vector2 _cursor = null;
	private boolean _has_pushed = false;
	
	/**
	 * Finds the player in the entity list.
	 * @return the player entity
	 * @throws NullPointerException if the player has not been found
	 */
	public Player getPlayer() throws NullPointerException {
		for (Entity entity : _entity_list) {
			if (entity.getClass() == Player.class) {
				return (Player) entity;
			}
		}
		throw new NullPointerException("Player not found.");
	}
	
	/**
	 * Gets the map class that the game is currently using.
	 * @return
	 */
	public Map getMap() {
		return _map;
	}
	
	private Map _map;
	
	/**
	 * Default constructor
	 */
	public Game() {
	
	}
	
	/**
	 * Gets if the game is still being run (or if the player hasn't completed it yet).
	 * @return the game state if it's being run
	 */
	public boolean isRunning() {
		return _running;
	}
	
	/**
	 * Sets the running state of the game, this stops iterate() function from executing.
	 * @param _running running state
	 */
	public void setRunning(boolean _running) {
		this._running = _running;
	}

	/**
	 * Sets if the game level is being edited at the moment.<br>
	 * This disables progression.
	 * @param editMode is being edited
	 */
	public void setEditMode(boolean editMode) {
		this._edit_mode = editMode;
	}

	/**
	 * Gets if the game is being edited at the moment.<br>
	 * @return is being edited
	 */
	public boolean getEditMode() {
		return _edit_mode;
	}

	/**
	 * Defines what kinds of responses, an input can return.<br>
	 * This is used by the renderers to provide feedback to the player
	 * like a prompt, or a sound whenever an action has taken place.
	 */
	public enum INPUT_RESULT {
		NONE,
		NEXT_LEVEL,
		PUSHED,
		MOVED,
		END
	}
	
	/**
	 * Iterates through every entity's main function (like pathing).<br>
	 * Removes entities that were requested to be deleted.<br>
	 * Reports back what happened through an INPUT_RESULT.<br>
	 * The result is used by the renderer to play back sounds for example.<br>
	 * Will not result in anything if an iteration happened in edit mode.
	 * @return INPUT_RESULT with the appropriate result
	 * @throws Exception for any case that could've happened here, like the player being missing
	 */
	public INPUT_RESULT iterate() throws Exception {
		if(!isRunning()){
			return INPUT_RESULT.NONE;
		}

		if(playback){

			if(currentUndoState < _undo_states.size()){
				loadUndo(currentUndoState);
				currentUndoState++;
			}else{
				playback = false;
				if(!isForcedPlayback) {
					if (loadNextLevel()) {
						return INPUT_RESULT.NEXT_LEVEL;
					} else {
						setRunning(false);
						return INPUT_RESULT.END;
					}
				}
			}
			return INPUT_RESULT.NONE;
		}

		for (Entity entity : _entity_list) {
			entity.iterate();
		}
		for(Entity e : _entity_removal_list){
			_entity_list.remove(e);
		}
		_entity_removal_list = new ArrayList<>();

		if(_edit_mode){
			return INPUT_RESULT.NONE;
		}

		if (goalsLeft() == 0) {
			saveState();
			startPlayback();
		} else if (_has_pushed) {
			return INPUT_RESULT.PUSHED;
		}
		return INPUT_RESULT.NONE;
	}
	
	/**
	 * Gets if a tile is walkable by first comparing it to floor or ice,
	 * then it makes sure there is not an entity in that tile.<br>
	 * This is used by entities to know if they can move to a tile or not.
	 * @param x position
	 * @param y position
	 * @return whether an entity can walk to that tile or not
	 */
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
	
	/**
	 * Returns if a tile is Ice or not.
	 * @param x position
	 * @param y position
	 * @return whether a tile is Ice or not
	 */
	public boolean isTileSlippery(int x, int y) {
		if (x > -1 && x < _map.getWidth() && y > -1 && y < _map.getHeight()) {
			if (_map.getTile(x, y) == Map.Tile.ICE) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Executes any number of inputs that currently exist.<br>
	 * These inputs are sent through text, and include:<br>
	 * <ul>
	 *     <li>left</li>
	 *     <li>right</li>
	 *     <li>up</li>
	 *     <li>down</li>
	 *     <li>save</li>
	 *     <li>load</li>
	 *     <li>place</li>
	 *     <li>undo</li>
	 *     <li>redo</li>
	 *     <li>[blank]</li>
	 * </ul>
	 * <br>
	 * They execute their given functions, including creating undo/redo actions
	 * on movement, and return an INPUT_RESULT for a given action.<br>
	 * A blank input represents a global iteration that calls iterate().
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public INPUT_RESULT input(String input) throws Exception {
		last_move = input;
		_has_pushed = false;
		String i = input.toLowerCase();
		switch (i) {
			case "l":
			case "left":
				_redo_states = new Stack<>();
				saveState();
				getPlayer().move(-1, 0);
				if (_has_pushed) {
					return INPUT_RESULT.PUSHED;
				}
				break;
			case "r":
			case "right":
				_redo_states = new Stack<>();
				saveState();
				getPlayer().move(1, 0);
				if (_has_pushed) {
					return INPUT_RESULT.PUSHED;
				}
				break;
			case "u":
			case "up":
				_redo_states = new Stack<>();
				saveState();
				getPlayer().move(0, -1);
				if (_has_pushed) {
					return INPUT_RESULT.PUSHED;
				}
				break;
			case "d":
			case "down":
				_redo_states = new Stack<>();
				saveState();
				getPlayer().move(0, 1);
				if (_has_pushed) {
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
				case "Hole":
					_map.setTile(_cursor.x, _cursor.y, Map.Tile.FLOOR);
					_entity_list.add(new Hole(this, _cursor));
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

	
	private void save() throws Exception {
		FileOutputStream file = new FileOutputStream(save_file);
		save(file);
		file.close();
	}
	
	private void invalidateStates(){
		if(_undo_states.size() > MAXIMUM_UNDO){
			_undo_states.remove(0);
		}
		if(_redo_states.size() > MAXIMUM_UNDO){
			_redo_states.remove(0);
		}
	}
	
	private void saveState() throws Exception {
		invalidateStates();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		save(outputStream);
		_undo_states.push(outputStream.toByteArray());
		outputStream.close();
	}
	
	private void loadState() throws Exception {
		invalidateStates();
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
		stream.writeObject(last_move);
		stream.close();
	}
	
	/**
	 * Saves a level into a .sokl file.
	 * @param file_name the path to the file
	 * @throws Exception
	 */
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
	
	/**
	 * Loads a level from a given path.
	 * @param level path to the level
	 * @throws Exception
	 */
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
		last_move = (String) stream.readObject();
		stream.close();
	}
	
	private void load() throws Exception {
		FileInputStream file = new FileInputStream(save_file);
		load(file);
		file.close();
	}
	
	/**
	 * This function only exists in case level loading is broken and we need a basic level.
	 */
	public void startDebugLevel() {
		_map = Map.generateTestMap();
		_entity_list = new ArrayList<>();
		_entity_list.add(new Player(this));
		_entity_list.add(new Goal(this));
	}
	
	/**
	 * Loads the first level and sets the game's running state to true.
	 */
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
	
	/**
	 * Pushes an entity at position x,y.<br>
	 * It pushes it by an offset x1,y1<br>
	 * @param x position
	 * @param y position
	 * @param x1 offset
	 * @param y1 offset
	 */
	public void pushEntity(int x, int y, int x1, int y1) {
		for (Entity entity : _entity_list) {
			if (entity.getPosition().x == x && entity.getPosition().y == y && entity.isPushable()) {
				entity.move(x1, y1);
				_has_pushed = true;
			}
		}
	}
	
	/**
	 * Loads the next level by incrementing currentLevel.<br>
	 * Will not load a level while in edit mode or when at the end of the game.
	 * @return whether the level has loaded successfully.
	 * @throws Exception
	 */
	public boolean loadNextLevel() throws Exception {
		if (_edit_mode)
			return false;
		if (currentLevel >= levels.length - 1)
			return false;
		lastUndoStateLevel = _undo_states.size();
		currentLevel++;
		loadLocalLevel(levels[currentLevel]);
		return true;
	}
	
	/**
	 * Calculates how many goals are still empty. If none are empty then we assume the level has been beaten.
	 * @return how many goals are left empty
	 */
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
	
	/**
	 * Creates a basic map that is box-shaped. <br>
	 * This is mostly used for testing purposes.
	 * @param width
	 * @param height
	 */
	public void createMap(int width, int height) {
		_map = generateBoxMap(width, height);
	}
	
	/**
	 * Sets what entity will be used when 'place' gets called.
	 * @param selectedValue entity
	 */
	public void setPickedEntity(String selectedValue) {
		_picked_entity = selectedValue;
		_picked_tile = null;
	}
	
	/**
	 * Sets what tile will be used when 'place' gets called.
	 * @param selectedValue tile
	 */
	public void setPickedTile(String selectedValue) {
		_picked_tile = selectedValue;
		_picked_entity = null;
	}
	
	/**
	 * Sets a cursor position for placing an entity/tile.
	 * @param cursorPos position of the cursor
	 */
	public void setCursorPos(Vector2 cursorPos) {
		_cursor = cursorPos;
	}
	
	/**
	 * Find where the cursor is
	 * @return position of the cursor
	 */
	public Vector2 getCursorPos() {
		return _cursor;
	}
	
	/**
	 * Makes the player path to a tile.<br>
	 * If the selected tile is right next to the player, it just uses a normal move function.
	 * @param cursorPosition where the user has clicked
	 * @return the result of the movement, in case it's considered a normal input
	 * @throws Exception
	 */
	public INPUT_RESULT walkTo(Vector2 cursorPosition) throws Exception {
		if(getPlayer().isPathing()){
			return INPUT_RESULT.NONE;
		}
		_has_pushed = false;
        Vector2 playerPosition = getPlayer().getPosition();
		saveState();
		if (cursorPosition.getDistanceTo(playerPosition) < 1.05) {
			getPlayer().move(cursorPosition.x - playerPosition.x, cursorPosition.y - playerPosition.y);
			if (_has_pushed)
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
