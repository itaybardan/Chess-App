package Listeners;

import Chess.ChessBoard;
import Chess.Move;

class PlayerMoveAndRepaint extends Thread {
    public ChessBoard cb;
    public Move move;

    public PlayerMoveAndRepaint(ChessBoard cb, Move move)//(ChessBoard cb)
    {
        this.cb = cb;
        this.move = move;
    }

    public void run() {
        this.cb.game.DoMove(this.move);
        this.cb.repaint();
    }
}



