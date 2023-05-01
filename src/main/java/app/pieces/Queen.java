package app.pieces;

import app.chess.Game;

import static app.pieces.Bishop.BishopPossibleMoves;
import static app.pieces.Rook.RookPossibleMoves;


public class Queen {


    public static long QueenPossibleMoves(int place, Game game) {
        return BishopPossibleMoves(place, game) | RookPossibleMoves(place, game);
    }

}
