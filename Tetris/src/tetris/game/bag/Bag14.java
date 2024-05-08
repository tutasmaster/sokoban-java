package tetris.game.bag;

import tetris.game.Piece;

import java.util.Random;

public class Bag14 implements Bag{
    @Override
    public Piece getNextPiece() {
        if(!initialized){
            start();
        }
        if(curPos >= 14){
            shuffle(10);
            curPos = 0;
        }
        Piece p = bag[curPos];
        curPos += 1;
        return p;
    }

    private void start() {
        for(int i = 0; i < 14; i+=7){
            bag[i] = Piece.generatePiece(Piece.Type.O);
            bag[i+1] = Piece.generatePiece(Piece.Type.I);
            bag[i+2] = Piece.generatePiece(Piece.Type.J);
            bag[i+3] = Piece.generatePiece(Piece.Type.Z);
            bag[i+4] = Piece.generatePiece(Piece.Type.S);
            bag[i+5] = Piece.generatePiece(Piece.Type.L);
            bag[i+6] = Piece.generatePiece(Piece.Type.T);
        }
        shuffle(20);
    }

    public void shuffle(int iterations) {
        Random r = new Random();
        for(int i = 0; i < iterations; i++){
            int r1 = r.nextInt(0,14);
            int r2 = r.nextInt(0,14);
            swap(r1,r2);
        }
    }

    public void swap(int i1, int i2){
        Piece p = bag[i1];
        bag[i1] = bag[i2];
        bag[i2] = p;
    }

    int curPos = 0;
    Boolean initialized = false;

    Piece[] bag = new Piece[14];
}
