package tetris.game.renderer.swing;

import rsIPT.graphics.*;
import rsIPT.input.InputManager;
import tetris.game.Game;
import tetris.game.Map;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SwingGamePanel extends JPanel implements KeyListener, MouseListener, ActionListener {

    public SwingGamePanel(Game game) {
        setGame(game);
        setFocusable(true);
        requestFocus();
        addKeyListener(this);
        addMouseListener(this);
        timer = new Timer(1000/60, this);
        timer.start();
    }

    private Game _game;

    private float scale_x = 25;
    private float scale_y = 25;

    public void setGame(Game game){
        _game = game;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        if(_game == null){
            return;
        }

        Map map = _game.get_map();
        Dimension d = getSize();
        scale_x = d.width / (float)map.get_size_x();
        scale_y = d.height / (float)map.get_size_y();

        float min = Math.min(scale_x, scale_y);
        scale_x = min;
        scale_y = min;

        int sX = Math.round(scale_x);
        int sY = Math.round(scale_y);

        for (int j = 0; j < map.get_size_y(); j++){
            for (int i = 0; i < map.get_size_x(); i++){

                Map.Tile t = map.getTile(i,j);

                int posX = (int) (i * scale_x);
                int posY = (int) (j * scale_y);

                if (t != Map.Tile.Empty){
                    switch (t){
                        case Red -> g.setColor(SwingColors.RED);
                        case Orange -> g.setColor(SwingColors.ORANGE);
                        case Green -> g.setColor(SwingColors.GREEN);
                        case Cyan -> g.setColor(SwingColors.CYAN);
                        case Blue -> g.setColor(SwingColors.BLUE);
                        case Purple -> g.setColor(SwingColors.PURPLE);
                        case Yellow -> g.setColor(SwingColors.YELLOW);
                    }
                    g.fillRect(posX, posY, sX+1, sY+1);
                }
                g.setColor(SwingColors.BACKGROUND);
                g.drawLine(posX,0,posX,(int)scale_y*map.get_size_y());
                g.drawLine(0,posY,(int)scale_x*map.get_size_x(),posY);
            }
        }
        for(int j = 0; j < 4; j++){
            for(int i = 0; i < 4; i++){
                Map.Tile t = _game.piece.getTile(i,j);

                if (t != Map.Tile.Empty){
                    switch (t){
                        case Red -> g.setColor(SwingColors.RED);
                        case Orange -> g.setColor(SwingColors.ORANGE);
                        case Green -> g.setColor(SwingColors.GREEN);
                        case Cyan -> g.setColor(SwingColors.CYAN);
                        case Blue -> g.setColor(SwingColors.BLUE);
                        case Purple -> g.setColor(SwingColors.PURPLE);
                        case Yellow -> g.setColor(SwingColors.YELLOW);
                    }

                    int posX = (int) ((i + _game.piecePosition.x) * scale_x);
                    int posY = (int) ((j + _game.piecePosition.y) * scale_y);
                    g.fillRect(posX, posY, sX+1, sY+1);
                }

            }
        }


    }

    Timer timer;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e){
        input = "";
        if(e.getKeyChar() == 'w'){
            input = "up";
        }else if(e.getKeyChar() == 'a'){
            input = "left";
        }else if(e.getKeyChar() == 's'){
            input = "down";
        }else if(e.getKeyChar() == 'd'){
            input = "right";
        }else if(e.getKeyChar() == 'j'){
            input = "rotate";
        }
        _game.input(input, InputManager.InputState.Pressed);
    }

    String input = "";

    @Override
    public void keyReleased(KeyEvent e) {

        input = "";
        if(e.getKeyChar() == 'w'){
            input = "up";
        }else if(e.getKeyChar() == 'a'){
            input = "left";
        }else if(e.getKeyChar() == 's'){
            input = "down";
        }else if(e.getKeyChar() == 'd'){
            input = "right";
        }else if(e.getKeyChar() == 'j'){
            input = "rotate";
        }
        _game.input(input, InputManager.InputState.Depressed);
    }

    Coord2DInt cursor_pos = null;

    @Override
    public void mouseClicked(MouseEvent e) {
        /*float sX = e.getX() / scale_x;
        float sY = e.getY() / scale_y;

        pos = new Coord2DInt((int)sX, (int)sY);*/
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //FOR THE TIMER
        _game.input("frame", InputManager.InputState.Pressed);
        repaint();
    }
}
