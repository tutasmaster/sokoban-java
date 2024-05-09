package sokoban.game.renderer.swing;

import sokoban.game.Vector2;
import sokoban.game.Game;
import sokoban.game.Map;
import sokoban.game.entity.Box;
import sokoban.game.entity.Entity;
import sokoban.game.entity.Goal;
import sokoban.game.entity.Player;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

import static sokoban.game.renderer.swing.SwingRenderer.renderException;
import static sokoban.game.renderer.swing.SwingRenderer.renderMessage;

public class SwingGamePanel extends JPanel implements KeyListener, MouseListener, MouseMotionListener, ActionListener {

    public SwingGamePanel(Game game) {
        setGame(game);
        setFocusable(true);
        requestFocus();
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

        Timer timer = new Timer(1000 / 30, this);
        timer.start();
        try {
            URL resource = Map.class.getResource("assets/tilemap.png");
            BufferedImage _tilemap = ImageIO.read(Objects.requireNonNull(resource));
            _block_image = _tilemap.getSubimage(0, 0, 16, 16);
            _goal_image = _tilemap.getSubimage(16, 0, 16, 16);
            _floor_image = _tilemap.getSubimage(32, 0, 16, 16);
            _wall_image = _tilemap.getSubimage(48, 0, 16, 16);
            _player_image = _tilemap.getSubimage(64, 0, 16, 16);
            _ice_image = _tilemap.getSubimage(80,0,16,16);
            _push_audio = loadSound("assets/push2.wav");
            _goal_audio = loadSound("assets/goal.wav");
        } catch (Exception e) {
            SwingRenderer.renderException(e);
        }
    }

    private Game _game;
    private Boolean _is_editing = false;

    public void setEditing(Boolean b) {
        _is_editing = b;
        if(_is_editing)
            setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        else
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    
    private BufferedImage _block_image;
    private BufferedImage _goal_image;
    private BufferedImage _floor_image;
    private BufferedImage _wall_image;
    private BufferedImage _player_image;
    private BufferedImage _ice_image;
    private Clip _push_audio;
    private Clip _goal_audio;
    private float scale_x = 25;
    private float scale_y = 25;
    private int offset_x = 0;
    private int offset_y = 0;

    public void setGame(Game game) {
        _game = game;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (_game == null) {
            return;
        }
        Map map = _game.getMap();
        Dimension d = getSize();
        scale_x = d.width / (float) map.getWidth();
        scale_y = d.height / (float) map.getHeight();

        float min = Math.min(scale_x, scale_y);
        scale_x = min;
        scale_y = min;

        offset_x = (d.width - (int) (scale_x * map.getWidth())) / 2;
        offset_y = (d.height - (int) (scale_y * map.getHeight())) / 2;

        int sX = Math.round(scale_x);
        int sY = Math.round(scale_y);

        g.setColor(SwingColors.BACKGROUND);
        g.fillRect(0,0,d.width, d.height);

        for (int j = 0; j < map.getHeight(); j++) {
            for (int i = 0; i < map.getWidth(); i++) {
                Map.Tile t = map.getTile(i, j);

                int posX = (int) (i * scale_x) + offset_x;
                int posY = (int) (j * scale_y) + offset_y;

                if (t == Map.Tile.WALL) {
                    g.setColor(SwingColors.WALL);
                    g.fillRect(posX, posY, sX + 1, sY + 1);
                    g.drawImage(_wall_image, posX, posY, sX + 1, sY + 1, null);
                } else if (t == Map.Tile.FLOOR) {
                    g.setColor(SwingColors.FLOOR);
                    g.fillRect(posX, posY, sX + 1, sY + 1);
                    g.drawImage(_floor_image, posX, posY, sX + 1, sY + 1, null);
                }else if (t == Map.Tile.ICE) {
                    g.setColor(SwingColors.FLOOR);
                    g.fillRect(posX, posY, sX + 1, sY + 1);
                    g.drawImage(_ice_image, posX, posY, sX + 1, sY + 1, null);
                }
            }
        }


        for (Entity e : _game.getEntityList()) {
            if (e.getClass() == Goal.class) {
                Vector2 p = e.getPosition();
                int gridPosX = (int) (p.x * scale_x) + offset_x;
                int gridPosY = (int) (p.y * scale_y) + offset_y;
                g.setColor(SwingColors.GOAL);
                //g.drawRect(gridPosX + 4, gridPosY + 4, (int)scale_x - 8, (int)scale_y - 8);
                g.drawImage(_goal_image, gridPosX, gridPosY, (int) scale_x, (int) scale_y, null);
            }
        }

        for (Entity e : _game.getEntityList()) {
            if (e.getClass() == Player.class) {
                Vector2 p = _game.getPlayer().getPosition();
                int gridPosX = (int) (p.x * scale_x) + offset_x;
                int gridPosY = (int) (p.y * scale_y) + offset_y;
                g.setColor(SwingColors.PLAYER);
                //g.fillArc(gridPosX, gridPosY, (int) sX, (int) sY, 0, 360);
                g.setColor(Color.BLACK);
                //g.drawArc(gridPosX, gridPosY, sX, sY, 0, 360);

                g.drawImage(_player_image, gridPosX, gridPosY, sX, sY, null);
            }
        }

        //BOXES get drawn last because they overlay sprites
        for (Entity e : _game.getEntityList()) {
            if (e.getClass() == Box.class) {
                Vector2 p = e.getPosition();
                int gridPosX = (int) (p.x * scale_x) + offset_x;
                int gridPosY = (int) (p.y * scale_y) + offset_y;
                g.setColor(SwingColors.BOX);
                //g.fillRect(gridPosX + 5, gridPosY + 5, (int)scale_x - 10, (int)scale_y - 10);
                g.setColor(Color.BLACK);
                //g.drawRect(gridPosX + 5, gridPosY + 5, (int)scale_x - 10, (int)scale_y - 10);
                g.drawImage(_block_image, (int) (gridPosX - (sX * 0.125f)), (int) (gridPosY - (sY * 0.125f)), (int) scale_x, (int) scale_y, null);

            }
        }


        Vector2 cursor = _game.getCursorPos();
        if (_is_editing && cursor != null && cursor.y < map.getHeight() && cursor.x < map.getWidth() && cursor.x > -1 && cursor.y > -1) {
            g.setColor(Color.WHITE);
            g.drawRect((int) (cursor.x * scale_x) + offset_x, (int) (cursor.y * scale_x) + offset_y, sX + 1, sY + 1);
        }
    }
    
    public static Clip loadSound(String resourceName)
            throws IOException, UnsupportedAudioFileException,
            LineUnavailableException {
        InputStream in = Map.class.getResourceAsStream(resourceName);
        AudioInputStream ain = AudioSystem.getAudioInputStream(in);
        Clip clip = AudioSystem.getClip();
        clip.open(ain);
        return clip;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        String s = "";
        if (e.getKeyChar() == 'w' || e.getKeyCode() == KeyEvent.VK_UP) {
            s = "up";
        } else if (e.getKeyChar() == 'a' || e.getKeyCode() == KeyEvent.VK_LEFT) {
            s = "left";
        } else if (e.getKeyChar() == 's' || e.getKeyCode() == KeyEvent.VK_DOWN) {
            s = "down";
        } else if (e.getKeyChar() == 'd' || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            s = "right";
        } else if (e.getKeyChar() == 'q'){
            s = "undo";
        } else if (e.getKeyChar() == 'e'){
            s = "redo";
        }

        try {
            Game.INPUT_RESULT r = _game.input(s);
            handleInputResult(r);
            repaint();
        } catch (Exception ex) {
            renderException(ex);
        }
    }
    
    private void handleInputResult(Game.INPUT_RESULT r) {
        if (r == Game.INPUT_RESULT.NEXT_LEVEL) {
            _goal_audio.setMicrosecondPosition(0);
            _goal_audio.start();
            renderMessage("You beat level " + _game.currentLevel + "!", "Congratulations");
        }else if(r == Game.INPUT_RESULT.END) {
            _goal_audio.start();
            renderMessage("You won the game!", "Congratulations");
        }else if(r == Game.INPUT_RESULT.PUSHED){
            _push_audio.stop();
            _push_audio.setMicrosecondPosition(0);
            _push_audio.start();
        }
        repaint();
    }
    
    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
    
    }

    @Override
    public void mousePressed(MouseEvent e) {
        float sX = (e.getX() - offset_x) / scale_x;
        float sY = (e.getY() - offset_y) / scale_y;

        Vector2 cursor_pos = new Vector2((int) sX, (int) sY);
        if (!_is_editing) {
            try{
                Game.INPUT_RESULT r = _game.walkTo(cursor_pos);
                handleInputResult(r);
            }catch(Exception ex){
                SwingRenderer.renderException(ex);
            }
            return;
        }

        _game.setCursorPos(cursor_pos);
        try {
            _game.input("place");
            repaint();
        } catch (Exception ex) {
            renderException(ex);
        }
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
    public void mouseDragged(MouseEvent e) {
        if (!_is_editing) {
            return;
        }

        float sX = (e.getX() - offset_x) / scale_x;
        float sY = (e.getY() - offset_y) / scale_y;
        Vector2 cursor_pos = new Vector2((int) sX, (int) sY);
        _game.setCursorPos(cursor_pos);
        try {
            _game.input("place");
            repaint();
        } catch (Exception ex) {
            renderException(ex);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        float sX = (e.getX() - offset_x) / scale_x;
        float sY = (e.getY() - offset_y) / scale_y;

        Vector2 cursor_pos = new Vector2((int) sX, (int) sY);
        _game.setCursorPos(cursor_pos);
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Game.INPUT_RESULT r = _game.input("");
            repaint();
            handleInputResult(r);
        } catch (Exception ex) {
            renderException(ex);
        }
    }
}
