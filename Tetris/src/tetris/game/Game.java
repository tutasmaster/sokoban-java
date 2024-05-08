package tetris.game;

import rsIPT.graphics.Coord2DInt;
import rsIPT.input.InputManager;
import tetris.game.bag.Bag;
import tetris.game.bag.Bag14;

public class Game {

    public Game() {
        _map = new Map(10,22);
        piece = _bag.getNextPiece();
        _input.addKey("up");
        _input.addKey("down");
        _input.addKey("left");
        _input.addKey("right");
        _input.addKey("rotate");
    }

    private InputManager _input = new InputManager();

    public Map get_map() {
        return _map;
    }

    private Bag _bag = new Bag14();

    private Map _map;
    public Piece piece;
    public Coord2DInt piecePosition = new Coord2DInt(3,0);

    public void input(String s, InputManager.InputState iS) {
        if(s != "frame"){
            _input.updateKey(s, iS);
        }else if(s == "frame"){
            if(_input.getKey("left") == InputManager.InputState.Held){
                if(checkPiecePosition(-1,0,piece)) {
                    piecePosition.x -= 1;
                }
            }else if(_input.getKey("right") == InputManager.InputState.Held){
                if(checkPiecePosition(1,0,piece)) {
                    piecePosition.x += 1;
                }
            }

            if(_input.getKey("down") == InputManager.InputState.Held){
                if(checkPiecePosition(0,1,piece)) {
                    piecePosition.y += 1;
                }
            }
            if(_input.getKey("rotate") == InputManager.InputState.Pressed){
                if(checkPiecePosition(0,0,piece.rotatePiece())){
                    piece = piece.rotatePiece();
                }
            }
            frame_accumulator += 1;
            if(frame_accumulator > getGravityFramerate()){
                frame_accumulator = 0;

                if(checkPiecePosition(0,1,piece)){
                    piecePosition.y += 1;
                }else{
                    _map.setPiece(piecePosition.x, piecePosition.y, piece);
                    piece = _bag.getNextPiece();
                    piecePosition = new Coord2DInt(3,0);
                }
            }
            _input.iterate();
        }



    }

    private int getGravityFramerate() {
        if(gravity == 0){
            return 30;
        }
        return 60;
    }

    public int gravity = 0;

    private int frame_accumulator = 0;

    private Boolean checkPiecePosition(int x, int y, Piece p) {
        for(int j = 0; j < 4; j++){
            for(int i = 0; i < 4; i++){
                int xF = i+x+piecePosition.x;
                int yF = j+y+piecePosition.y;
                if(_map.getTile(xF, yF) != Map.Tile.Empty && p.getTile(i,j) != Map.Tile.Empty){
                    return false;
                }
            }
        }
        return true;
    }
}
