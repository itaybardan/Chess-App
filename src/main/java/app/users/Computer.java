package app.users;

import app.chess.ChessBoard;
import app.chess.NegaMaxAlg;


public class Computer {
    public long[] PiecesPosition;
    public char Color;
    public NegaMaxAlg search;

    public Computer(char color) {
        this.Color = color;
    }

    public void CopyComputer(Computer comp) {
        this.PiecesPosition = new long[]{comp.PiecesPosition[0], comp.PiecesPosition[1], comp.PiecesPosition[2], comp.PiecesPosition[3], comp.PiecesPosition[4], comp.PiecesPosition[5]};
        this.Color = comp.Color;
    }

    public void setStartPosition() {
        // order in array: { pawn, rook, knight, bishop, queen, king};
        long[] a;
        if (this.Color == 'w') {
            a = new long[]{0xff000000000000L, 0x8100000000000000L, 0x4200000000000000L,
                    0x2400000000000000L, 0x1000000000000000L, 0x0800000000000000L};
        } else {
            a = new long[]{0xff000000000000L, 0x8100000000000000L, 0x4200000000000000L,
                    0x2400000000000000L, 0x0800000000000000L, 0x1000000000000000L};
        }
        this.PiecesPosition = a;
    }


    public void setStartPositionDebug() {
        // order in array: { pawn, rook, knight, bishop, queen, king};
        if (this.Color == 'w') {
            this.PiecesPosition = new long[]{0xff000000000000L, 0x8100000000000000L, 0x0000000000000000L,
                    0x0000000000000000L, 0x010000000000000L, 0x800000000000000L};
        } else {
            this.PiecesPosition = new long[]{0x47a01004000000L, 0x2200000000000000L, 0x0000000000000000L,
                    0x2000000000L, 0x40000000L, 0x4000000000000000L};
        }
    }


    public void doMove(ChessBoard cb) {

        this.search = new NegaMaxAlg(cb);
        this.search.start();
        try {
            this.search.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread Interrupted");
        }
    }

    public long getOccupiedPlaces() {
        return (PiecesPosition[0] |
                PiecesPosition[1] |
                PiecesPosition[2] |
                PiecesPosition[3] |
                PiecesPosition[4] |
                PiecesPosition[5]);
    }
}
