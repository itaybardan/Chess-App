package app.pieces;

import app.chess.Game;
import app.chess.Move;


public class Pawn {

    public static final long[] ComputerPawnAllPossibleMoves = getComputerPawnPossibleMoves();
    public static final long[] ComputerPawnAllPossibleAttackMoves = getComputerPawnPossibleAttackMoves();
    public static final long[] PlayerPawnAllPossibleMoves = getPlayerPawnPossibleMoves();
    public static final long[] PlayerPawnAllPossibleAttackMoves = getPlayerPawnPossibleAttackMoves();

    private static long[] getComputerPawnPossibleMoves() {
        long[] mov = new long[64];
        long bit = 0x1L;
        for (int i = 0; i < 64; i++) {
            bit = bit << i;
            if ((bit & 0xffL) == 0) {
                mov[i] = bit >>> 8;
            }
            bit = 0x1L;
        }
        return mov;
    }

    public static long PawnPossibleMoves(int place, Game game) {
        long Origin = 0x1L << place;
        long ForwardPossibleMoves;
        long PossibleAtkMoves;
        boolean isPlayerPiece = game.isPlayerPiece(Origin);

        Move LastMove = new Move(Origin, Origin, 0, 0, 0); // random move not important
        if (!game.undoMoves.empty())
            LastMove = game.undoMoves.lastElement();
        long finalPossibleMoves = 0x0L;

        if (isPlayerPiece) {
            ForwardPossibleMoves = PlayerPawnAllPossibleMoves[place] & (~game.AllOccupiedPlaces);
            PossibleAtkMoves = PlayerPawnAllPossibleAttackMoves[place] & game.ComputerOccupiedPlaces;

            if (place / 8 == 1 && (Origin << 8 & game.AllOccupiedPlaces) == 0 && (Origin << 16 & game.AllOccupiedPlaces) == 0) // if the pawn still not moved
                finalPossibleMoves |= ((ForwardPossibleMoves) | (Origin << 16) | PossibleAtkMoves);
            else finalPossibleMoves |= ((ForwardPossibleMoves) | PossibleAtkMoves);

            if ((LastMove.destination << 1 == Origin || LastMove.destination >>> 1 == Origin) && LastMove.Serial == 0 && LastMove.destination == LastMove.Origin >>> 16)
                finalPossibleMoves |= LastMove.destination << 8;  // en passant
        } else // if computer's turn
        {
            ForwardPossibleMoves = ComputerPawnAllPossibleMoves[place] & (~game.AllOccupiedPlaces);
            PossibleAtkMoves = ComputerPawnAllPossibleAttackMoves[place] & game.PlayerOccupiedPlaces;

            if (place / 8 == 6 && (Origin >>> 8 & game.AllOccupiedPlaces) == 0 && (Origin >>> 16 & game.AllOccupiedPlaces) == 0) // if the pawn still has not moved
                finalPossibleMoves |= ((ForwardPossibleMoves) | (Origin >>> 16) | PossibleAtkMoves);
            else finalPossibleMoves |= ((ForwardPossibleMoves) | PossibleAtkMoves);

            if ((LastMove.destination << 1 == Origin || LastMove.destination >>> 1 == Origin) && LastMove.Serial == 0 && LastMove.destination == LastMove.Origin << 16)
                finalPossibleMoves |= LastMove.destination >>> 8; // en passant
        }
        return finalPossibleMoves;
    }

    public static long[] getComputerPawnPossibleAttackMoves() {
        long[] mov = new long[64];
        long bit;
        for (int i = 0; i < 64; i++) {
            bit = 0x1L << i;
            if ((bit & 0x1010101010101ffL) == 0) {
                mov[i] |= bit >>> 9;

            }
            if ((bit & 0x80808080808080ffL) == 0) {
                mov[i] |= bit >>> 7;

            }
        }
        return mov;
    }

    private static long[] getPlayerPawnPossibleMoves() {
        long[] mov = new long[64];
        long bit = 0x1L;
        for (int i = 0; i < 64; i++) {
            bit <<= i;
            if ((bit & 0xff00000000000000L) == 0) {
                mov[i] = bit << 8;
            }
            bit = 0x1L;
        }
        return mov;
    }

    public static long[] getPlayerPawnPossibleAttackMoves() {
        long[] mov = new long[64];
        long bit;
        for (int i = 0; i < 64; i++) {
            bit = 0x1L << i;
            if ((bit & 0xff80808080808080L) == 0) {
                mov[i] |= bit << 9;

            }
            if ((bit & 0xff01010101010101L) == 0) {
                mov[i] |= bit << 7;

            }
        }
        return mov;
    }
}
