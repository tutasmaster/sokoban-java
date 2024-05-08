package tetris.game.renderer;

import tetris.game.Game;

public interface Renderer {
    public void render(Game game);

    void input(Game game) throws Exception;
}
