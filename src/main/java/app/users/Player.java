package app.users;

import app.chess.ChessBoard;
import app.chess.Move;


public class Player {

    public long[] PiecesPosition;
    public char Color;


    public Player(char color) {
        this.Color = color;
    }

    public void CopyPlayer(Player player) {
        this.PiecesPosition = new long[]{player.PiecesPosition[0], player.PiecesPosition[1], player.PiecesPosition[2], player.PiecesPosition[3], player.PiecesPosition[4], player.PiecesPosition[5]};
        this.Color = player.Color;
    }

    public void setStartPosition() {
        if (this.Color == 'w') {
            this.PiecesPosition = new long[]{0x000000000000ff00L, 0x0000000000000081L, 0x0000000000000042L,
                    0x0000000000000024L, 0x000000000000008L, 0x0000000000000010L};
        } else {
            this.PiecesPosition = new long[]{0x000000000000ff00L, 0x0000000000000081L, 0x0000000000000042L,
                    0x0000000000000024L, 0x0000000000000010L, 0x000000000000008L};
        }
    }

    public void setStartPositionDebug() {
        this.PiecesPosition = new long[]{0x18000000000L, 0x0000000000000000L, 0x400000L,
                0x0000000000000000L, 0x0000000000000000L, 0x4000L};
    }

    public long getOccupiedPlaces() {
        return (PiecesPosition[0] |
                PiecesPosition[1] |
                PiecesPosition[2] |
                PiecesPosition[3] |
                PiecesPosition[4] |
                PiecesPosition[5]);
    }

    public void doMove(ChessBoard cb, Move move) {
        cb.game.DoMove(move);
        cb.repaint();

    }
}
