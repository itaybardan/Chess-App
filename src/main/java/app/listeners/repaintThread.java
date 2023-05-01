package app.listeners;

import app.chess.ChessBoard;
import app.chess.Move;

class PlayerMoveAndRepaint extends Thread {
    public final ChessBoard cb;
    public final Move move;

    public PlayerMoveAndRepaint(ChessBoard cb, Move move) {
        this.cb = cb;
        this.move = move;
    }

    public void run() {
        this.cb.game.DoMove(this.move);
        this.cb.repaint();
    }
}



