package app.pieces;

import app.chess.Game;


public class King {

    public static final long[] KingAllPossibleMoves = {
            0x0000000000000302L, 0x0000000000000705L, 0x0000000000000E0AL, 0x0000000000001C14L,
            0x0000000000003828L, 0x0000000000007050L, 0x000000000000E0A0L, 0x000000000000C040L,
            0x0000000000030203L, 0x0000000000070507L, 0x00000000000E0A0EL, 0x00000000001C141CL,
            0x0000000000382838L, 0x0000000000705070L, 0x0000000000E0A0E0L, 0x0000000000C040C0L,
            0x0000000003020300L, 0x0000000007050700L, 0x000000000E0A0E00L, 0x000000001C141C00L,
            0x0000000038283800L, 0x0000000070507000L, 0x00000000E0A0E000L, 0x00000000C040C000L,
            0x0000000302030000L, 0x0000000705070000L, 0x0000000E0A0E0000L, 0x0000001C141C0000L,
            0x0000003828380000L, 0x0000007050700000L, 0x000000E0A0E00000L, 0x000000C040C00000L,
            0x0000030203000000L, 0x0000070507000000L, 0x00000E0A0E000000L, 0x00001C141C000000L,
            0x0000382838000000L, 0x0000705070000000L, 0x0000E0A0E0000000L, 0x0000C040C0000000L,
            0x0003020300000000L, 0x0007050700000000L, 0x000E0A0E00000000L, 0x001C141C00000000L,
            0x0038283800000000L, 0x0070507000000000L, 0x00E0A0E000000000L, 0x00C040C000000000L,
            0x0302030000000000L, 0x0705070000000000L, 0x0E0A0E0000000000L, 0x1C141C0000000000L,
            0x3828380000000000L, 0x7050700000000000L, 0xE0A0E00000000000L, 0xC040C00000000000L,
            0x0203000000000000L, 0x0507000000000000L, 0x0A0E000000000000L, 0x141C000000000000L,
            0x2838000000000000L, 0x5070000000000000L, 0xA0E0000000000000L, 0x40C0000000000000L
    };

    public static long KingPossibleMoves(int place, Game game) {
        long bit = 0x1L << place;
        boolean isPlayerPiece = game.isPlayerPiece(bit);
        long emptyOrOpPossiblePlaces;
        long PossibleMoves = 0x0L;

        long rightShift = bit >>> 3 | bit >>> 2 | bit >>> 1;
        if (isPlayerPiece) {
            emptyOrOpPossiblePlaces = KingAllPossibleMoves[place] & (game.ComputerOccupiedPlaces | ~game.PlayerOccupiedPlaces);
            PossibleMoves |= emptyOrOpPossiblePlaces;

            if (!game.isKingUnderThreat(true)) {
                if (((game.AllOccupiedPlaces | game.getComputerPossibleMovesLong()) & (bit << 2 | bit << 1)) == 0 && game.PlayerRightCastling)
                    PossibleMoves |= bit << 2;
                if (((game.AllOccupiedPlaces | game.getComputerPossibleMovesLong()) & rightShift) == 0 && game.PlayerLeftCastling)
                    PossibleMoves |= bit >>> 2;
            }

        } else {
            emptyOrOpPossiblePlaces = KingAllPossibleMoves[place] & (game.PlayerOccupiedPlaces | ~game.ComputerOccupiedPlaces);
            PossibleMoves |= emptyOrOpPossiblePlaces;

            //castling part.
            if (!game.isKingUnderThreat(false)) {
                if (((game.AllOccupiedPlaces | game.getPlayerPossibleMovesLong()) & (bit << 2 | bit << 1)) == 0 && game.ComputerRightCastling)
                    PossibleMoves |= bit << 2;
                if (((game.AllOccupiedPlaces | game.getPlayerPossibleMovesLong()) & rightShift) == 0 && game.ComputerLeftCastling)
                    PossibleMoves |= bit >>> 2;
            }

        }

        return PossibleMoves;
    }

    public static long KingSpecialPossibleMoves(int place, Game game) {
        long bit = 0x1L << place;
        boolean isPlayerPiece = game.isPlayerPiece(bit);
        long emptyOrOpPossiblePlaces;
        long PossibleMoves = 0x0L;

        if (isPlayerPiece) {
            emptyOrOpPossiblePlaces = KingAllPossibleMoves[place] & (game.ComputerOccupiedPlaces | ~game.PlayerOccupiedPlaces);


        } else {
            emptyOrOpPossiblePlaces = KingAllPossibleMoves[place] & (game.PlayerOccupiedPlaces | ~game.ComputerOccupiedPlaces);


        }
        PossibleMoves |= emptyOrOpPossiblePlaces;
        return PossibleMoves;
    }
}
