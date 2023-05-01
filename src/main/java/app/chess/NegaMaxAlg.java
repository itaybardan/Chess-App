package app.chess;

import java.util.ArrayList;

public class NegaMaxAlg extends Thread {
    public static final int[] PiecesValue = {100, 500, 320, 330, 900, 20000};
    public static final int[][] computerBonus = {
            {0, 0, 0, 0, 0, 0, 0, 0,
                    50, 50, 50, 50, 50, 50, 50, 50,
                    10, 10, 20, 30, 30, 20, 10, 10,
                    5, 5, 10, 25, 25, 10, 5, 5,
                    0, 0, 0, 20, 20, 0, 0, 0,
                    5, -5, -10, 0, 0, -10, -5, 5,
                    5, 10, 10, -20, -20, 10, 10, 5,
                    0, 0, 0, 0, 0, 0, 0, 0},

            {0, 0, 0, 0, 0, 0, 0, 0,
                    5, 10, 10, 10, 10, 10, 10, 5,
                    -5, 0, 0, 0, 0, 0, 0, -5,
                    -5, 0, 0, 0, 0, 0, 0, -5,
                    -5, 0, 0, 0, 0, 0, 0, -5,
                    -5, 0, 0, 0, 0, 0, 0, -5,
                    -5, 0, 0, 0, 0, 0, 0, -5,
                    0, 0, 0, 5, 5, 0, 0, 0},

            {-50, -40, -30, -30, -30, -30, -40, -50,
                    -40, -20, 0, 0, 0, 0, -20, -40,
                    -30, 0, 10, 15, 15, 10, 0, -30,
                    -30, 5, 15, 20, 20, 15, 5, -30,
                    -30, 0, 15, 20, 20, 15, 0, -30,
                    -30, 5, 10, 15, 15, 10, 5, -30,
                    -40, -20, 0, 5, 5, 0, -20, -40,
                    -50, -40, -30, -30, -30, -30, -40, -50},

            {-20, -10, -10, -10, -10, -10, -10, -20,
                    -10, 0, 0, 0, 0, 0, 0, -10,
                    -10, 0, 5, 10, 10, 5, 0, -10,
                    -10, 5, 5, 10, 10, 5, 5, -10,
                    -10, 0, 10, 10, 10, 10, 0, -10,
                    -10, 10, 10, 10, 10, 10, 10, -10,
                    -10, 5, 0, 0, 0, 0, 5, -10,
                    -20, -10, -10, -10, -10, -10, -10, -20},

            {-20, -10, -10, -5, -5, -10, -10, -20,
                    -10, 0, 0, 0, 0, 0, 0, -10,
                    -10, 0, 5, 5, 5, 5, 0, -10,
                    -5, 0, 5, 5, 5, 5, 0, -5,
                    0, 0, 5, 5, 5, 5, 0, -5,
                    -10, 5, 5, 5, 5, 5, 0, -10,
                    -10, 0, 5, 0, 0, 0, 0, -10,
                    -20, -10, -10, -5, -5, -10, -10, -20},

            {-30, -40, -40, -50, -50, -40, -40, -30,
                    -30, -40, -40, -50, -50, -40, -40, -30,
                    -30, -40, -40, -50, -50, -40, -40, -30,
                    -30, -40, -40, -50, -50, -40, -40, -30,
                    -20, -30, -30, -40, -40, -30, -30, -20,
                    -10, -20, -20, -20, -20, -20, -20, -10,
                    20, 20, 0, 0, 0, 0, 20, 20,
                    20, 30, 10, 0, 0, 10, 30, 20}};
    public static final int[][] playerBonus = {
            {0, 0, 0, 0, 0, 0, 0, 0,
                    5, 10, 10, -20, -20, 10, 10, 5,
                    5, -5, -10, 0, 0, -10, -5, 5,
                    0, 0, 0, 20, 20, 0, 0, 0,
                    5, 5, 10, 25, 25, 10, 5, 5,
                    10, 10, 20, 30, 30, 20, 10, 10,
                    50, 50, 50, 50, 50, 50, 50, 50,
                    0, 0, 0, 0, 0, 0, 0, 0},

            {0, 0, 0, 0, 0, 0, 0, 0,
                    5, 10, 10, 10, 10, 10, 10, 5,
                    -5, 0, 0, 0, 0, 0, 0, -5,
                    -5, 0, 0, 0, 0, 0, 0, -5,
                    -5, 0, 0, 0, 0, 0, 0, -5,
                    -5, 0, 0, 0, 0, 0, 0, -5,
                    -5, 0, 0, 0, 0, 0, 0, -5,
                    0, 0, 0, 5, 5, 0, 0, 0},

            {-50, -40, -30, -30, -30, -30, -40, -50,
                    -40, -20, 0, 0, 0, 0, -20, -40,
                    -30, 0, 10, 15, 15, 10, 0, -30,
                    -30, 5, 15, 20, 20, 15, 5, -30,
                    -30, 0, 15, 20, 20, 15, 0, -30,
                    -30, 5, 10, 15, 15, 10, 5, -30,
                    -40, -20, 0, 5, 5, 0, -20, -40,
                    -50, -40, -30, -30, -30, -30, -40, -50},

            {-20, -10, -10, -10, -10, -10, -10, -20,
                    -10, 0, 0, 0, 0, 0, 0, -10,
                    -10, 0, 5, 10, 10, 5, 0, -10,
                    -10, 5, 5, 10, 10, 5, 5, -10,
                    -10, 0, 10, 10, 10, 10, 0, -10,
                    -10, 10, 10, 10, 10, 10, 10, -10,
                    -10, 5, 0, 0, 0, 0, 5, -10,
                    -20, -10, -10, -10, -10, -10, -10, -20},

            {-20, -10, -10, -5, -5, -10, -10, -20,
                    -10, 0, 0, 0, 0, 0, 0, -10,
                    -10, 0, 5, 5, 5, 5, 0, -10,
                    -5, 0, 5, 5, 5, 5, 0, -5,
                    -5, 0, 5, 5, 5, 5, 0, 0,
                    -10, 0, 5, 5, 5, 5, 5, -10,
                    -10, 0, 0, 0, 0, 5, 0, -10,
                    -20, -10, -10, -5, -5, -10, -10, -20},

            {20, 30, 10, 0, 0, 10, 30, 20,
                    20, 20, 0, 0, 0, 0, 20, 20,
                    -10, -20, -20, -20, -20, -20, -20, -10,
                    -20, -30, -30, -40, -40, -30, -30, -20,
                    -30, -40, -40, -50, -50, -40, -40, -30,
                    -30, -40, -40, -50, -50, -40, -40, -30,
                    -30, -40, -40, -50, -50, -40, -40, -30,
                    -30, -40, -40, -50, -50, -40, -40, -30}};
    public final int depth;
    public final Game game = new Game();
    public final ChessBoard cb;
    public Move bestMove;
    public final int compSide = 1;

    public NegaMaxAlg(ChessBoard cb)
    {
        this.cb = cb;
        this.depth = cb.game.depth;
    }

    public void run() {
        this.game.CopyGame(cb.game);
        this.NegaMax(this.depth, Integer.MIN_VALUE + 1, Integer.MAX_VALUE - 1, compSide);

        if (bestMove != null) {
            this.cb.game.DoMove(bestMove);
        }
        this.cb.repaint();
    }

    //generates a tree of all moves possible, and with the help of 'evaluate' function, decides which move is the best
    public int NegaMax(int depth, int alpha, int beta, int comp) {
        if (depth == 0 || game.isGameOver(comp == 1) != 0) return evaluate(comp) * comp;
        int bestValue = Integer.MIN_VALUE + 1;
        ArrayList<Move> moves = comp == 1 ? game.getComputerPossibleMoves() : game.getPlayerPossibleMoves();

        for (Move move : moves) {
            game.DoMove(move);

            int score = -NegaMax(depth - 1, -beta, -alpha, -comp);
            if (score > bestValue) {
                bestValue = score;
                if (depth == this.depth)
                    bestMove = move;
            }
            game.undoMove();
            alpha = Math.max(score, alpha);
            if (alpha >= beta)
                break;
        }

        return bestValue;
    }

    public int evaluate(int comp) {
        int score = 0;
        long bitCount;
        long Pieces;

        for (int i = 0; i < 6; i++) {
            Pieces = this.game.Computer.PiecesPosition[i];
            bitCount = Long.bitCount(Pieces);
            for (int j = 0; j < bitCount; j++) {
                score += (computerBonus[i][Long.numberOfTrailingZeros(Pieces)] + PiecesValue[i]);
                Pieces ^= Long.lowestOneBit(Pieces);
            }
            Pieces = this.game.Player.PiecesPosition[i];
            bitCount = Long.bitCount(Pieces);
            for (int j = 0; j < bitCount; j++) {
                score -= (playerBonus[i][Long.numberOfTrailingZeros(Pieces)] + PiecesValue[i]);
                Pieces ^= Long.lowestOneBit(Pieces);

            }
        }
        // checkmate
        score += this.game.isGameOver(comp == 1) * comp;

        return score;
    }


}
