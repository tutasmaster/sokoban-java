package sokoban.game.renderer;

import sokoban.game.Game;
import sokoban.game.Map;

public interface Renderer {
    public void render(Game game);

    void input(Game game) throws Exception;
}
