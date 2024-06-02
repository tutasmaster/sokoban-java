package sokoban.game.renderer;

/**
 * Interface for any kind of renderer that exists in the application.<br>
 * For this project only two renderers exist ConsoleRenderer and SwiftRenderer.<br>
 * They provide user input and rendering of the map, effectively making the game playable.<br>
 * They typically use a Game instance (however it is not required by the interface)
 * and exist only to be used by the Sokoban class at the moment.
 */
public interface Renderer {
    /**
     * Start the UI and creates a new game.
     */
    void start();
}
