package sokoban.game.renderer.swing;

import sokoban.game.Vector2;
import sokoban.game.Game;
import sokoban.game.Map;
import sokoban.game.entity.*;
import sokoban.game.entity.Box;

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


/**
 * The game panel used by SwingRenderer that effectively handles
 * all Input and Output from the game into the user.<br>
 * The rest of the UX such as buttons and menu bars are provided by SwingGameFrame and
 * the other frames.<br>
 * This only draws the game itself and handles direct inputs such as keyboard or direct
 * mouse control.
 */
public class SwingGamePanel extends JPanel implements KeyListener, MouseListener, MouseMotionListener, ActionListener {
    
    /**
     * Constructs a Game Panel with a reference to the game.<br>
     * It sets the key, mouse and mouse motion listeners.<br>
     * It also splices the tiles and loads every asset it needs.
     * @param game reference to a game
     */
    public SwingGamePanel(Game game) {
        setGame(game);
        setFocusable(true);
        requestFocus();
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        
        //Animation timer
        _timer = new Timer(1000 / 10, this);
        _timer.start();
        
        try {
            /*Tiles get spliced from a tilemap,
            every tile exists next to each other in tilemap.png*/
            URL resource = Map.class.getResource("assets/tilemap.png");
            BufferedImage _tilemap = ImageIO.read(Objects.requireNonNull(resource));
            _block_image = _tilemap.getSubimage(0, 0, 16, 16);
            _goal_image = _tilemap.getSubimage(16, 0, 16, 16);
            _floor_image = _tilemap.getSubimage(32, 0, 16, 16);
            _wall_image = _tilemap.getSubimage(48, 0, 16, 16);
            _player_image = _tilemap.getSubimage(64, 0, 16, 16);
            _ice_image = _tilemap.getSubimage(80,0,16,16);
            _hole_image = _tilemap.getSubimage(80+16,0,16,16);
            _hole_close_image = _tilemap.getSubimage(80+32,0,16,16);
            _down_image = ImageIO.read(Objects.requireNonNull(Map.class.getResource("assets/arrow1.png")));
            _left_image = ImageIO.read(Objects.requireNonNull(Map.class.getResource("assets/arrow2.png")));
            _up_image = ImageIO.read(Objects.requireNonNull(Map.class.getResource("assets/arrow3.png")));
            _right_image = ImageIO.read(Objects.requireNonNull(Map.class.getResource("assets/arrow4.png")));
            _push_audio = loadSound("assets/push2.wav");
            _goal_audio = loadSound("assets/goal.wav");

        } catch (Exception e) {
            SwingRenderer.renderException(e);
        }
    }
    private Timer _timer;
    private Game _game;
    private Boolean _is_editing = false;
    
    /**
     * Sets whether the game is being edited or not,
     * if it is, then we let show the crosshair cursor for more easy
     * modification of the tiles.
     * @param b is editing
     */
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
    private BufferedImage _hole_image;
    private BufferedImage _hole_close_image;
    private BufferedImage _up_image;
    private BufferedImage _down_image;
    private BufferedImage _left_image;
    private BufferedImage _right_image;
    private Clip _push_audio;
    private Clip _goal_audio;
    private float scale_x = 25;
    private float scale_y = 25;
    private int offset_x = 0;
    private int offset_y = 0;
    
    /**
     * Sets the game reference the panel uses
     * @param game reference to a game
     */
    public void setGame(Game game) {
        _game = game;
    }
    
    /**
     * This is the core renderer of the game.<br>
     * It handles things such as scaling, centering, and drawing all the tiles.
     * It renders certain objects in different loops for the sake of proper ordering like:
     * <ol>
     *     <li>Floors</li>
     *     <li>Goals</li>
     *     <li>Other entities such as the player</li>
     *     <li>Boxes, because they overlap the player as an aesthetic choice</li>
     * </ol>
     * It clears the screen everytime it executes.<br>
     * It contains several variables that are used for calculating positions such as center of the screen
     * or the offset of every tile including scaling.
     * @param g  the <code>Graphics</code> context in which to paint
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

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
            }else if (e.getClass() == Hole.class) {
                Vector2 p = e.getPosition();
                int gridPosX = (int) (p.x * scale_x) + offset_x;
                int gridPosY = (int) (p.y * scale_y) + offset_y;
                g.setColor(SwingColors.GOAL);
                //g.drawRect(gridPosX + 4, gridPosY + 4, (int)scale_x - 8, (int)scale_y - 8);
                Hole h = (Hole)e;
                if(((Hole) e).containsBox()){
                    g.drawImage(_hole_close_image, gridPosX, gridPosY, (int) scale_x, (int) scale_y, null);
                }else{
                    g.drawImage(_hole_image, gridPosX, gridPosY, (int) scale_x, (int) scale_y, null);
                }
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

        //GENERATE MOVE LIST
        int x = 0;
        int y = 0;
        for(int i = _game.lastUndoStateLevel; i < _game.getUndoStateSize(); i++){
            try{
                String s = _game.getMoveFromUndoState(i);
                int newX = x*30 + 10;
                if(newX > d.width - 40){
                    x = 0;
                    newX = 10;
                    y++;
                }
                int newY = y*30+20;
                
                switch(s){
                    case "left":
                        g.drawImage(_left_image, newX,newY,20,20, null);
                        break;
                    case "up":
                        g.drawImage(_up_image, newX,newY,20,20 ,null);
                        break;
                    case "right":
                        g.drawImage(_right_image, newX,newY,20,20, null);
                        break;
                    case "down":
                        g.drawImage(_down_image, newX,newY,20,20, null);
                        break;
                    default:
                        g.drawImage(_player_image, newX,newY,20,20, null);
                }
                x+=1;
            }catch(Exception e){
            }
        }
        //g.setColor(Color.WHITE);
        //g.drawString("STEPS: ",0,d.height);
    }
    
    /**
     * Loads a sound as a stream.<br>
     * This is used for audio assets such as the sound of a block
     * being dragged.<br>
     * Assets are being loading from the sokoban.game.assets package.
     * @param resourceName audio file
     * @return audio clip
     * @throws IOException if the file doesn't exist
     * @throws UnsupportedAudioFileException if the audio isn't supported
     * @throws LineUnavailableException if the file is being used by another application
     */
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
    
    /**
     * Sends key presses to the game's input system.
     * They are converted from a button press, to a string that represents the correct action.
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if(_game.playback)
            return;
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
    
    /**
     * Handles whatever result we got from the input such as keyPressed.<br>
     * An example is a prompt when the user finishes a level, or a sound that plays
     * when a box gets dragged.
     * @param r
     */
    public void handleInputResult(Game.INPUT_RESULT r) {
        
        _timer.restart();
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
    
    /**
     * When a mouse has clicked, the following is dependent on what state the game is in:
     * <ul>
     *     <li>
     *         If the game panel is in edit mode, we place a tile/entity at the cursor position.
     *     </li>
     *     <li>
     *         If the game panel is in game mode, we execute the smart pathfinding towards the cursor.
     *     </li>
     * </ul>
     * This function contains some scaling code since we have to know where exactly
     * the game exists in the panel.
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if(_game.playback)
            return;
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
    
    /**
     * When the mouse moves ,is holding a button inside the panel, and we are in edit mode,
     * we show a cursor position to the user to make it clearer where their selection is.
     * @param e the event to be processed
     */
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
    /**
     * When the mouse moves inside the panel, and we are in edit mode,
     * we show a cursor position to the user to make it clearer where their selection is.
     * @param e the event to be processed
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        float sX = (e.getX() - offset_x) / scale_x;
        float sY = (e.getY() - offset_y) / scale_y;

        Vector2 cursor_pos = new Vector2((int) sX, (int) sY);
        _game.setCursorPos(cursor_pos);
        repaint();
    }
    
    /**
     * This is the result of the animation timer.<br>
     * We execute an empty input, which forces the game to go through every
     * entities' iterate function.<br>
     * This is used for animations such as with the pathfinding or ice.
     * @param e the event to be processed
     */
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
