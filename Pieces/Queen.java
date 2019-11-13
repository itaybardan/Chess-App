/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pieces;

import Chess.Game;
import static Pieces.Bishop.BishopPossibleMoves;
import static Pieces.Rook.RookPossibleMoves;


/**
 *
 * @author itayb
 */
public class Queen {
    

    public static long QueenPossibleMoves(int place,Game game)
    {
        return BishopPossibleMoves(place,game) | RookPossibleMoves(place,game);
    }
  
}
