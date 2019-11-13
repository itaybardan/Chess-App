package Users;

import Chess.ChessBoard;
import Chess.NegaMax;
import Chess.Move;

import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author itayb
 */
public class Player {
   
    public long[] PiecesPosition;//=new long[6];
    public char Color='w';
    
    
    public Player(char color)
    {
        this.Color=color;
    }
    public void CopyPlayer (Player player)
    {
        long a[]={player.PiecesPosition[0], player.PiecesPosition[1], player.PiecesPosition[2], player.PiecesPosition[3], player.PiecesPosition[4], player.PiecesPosition[5]};
        this.PiecesPosition=a;
        this.Color=player.Color;
    }
    public void setStartPosition()
    {
        if (this.Color=='w')
        {   
            long[] a={0x000000000000ff00L, 0x0000000000000081L, 0x0000000000000042L,
                       0x0000000000000024L, 0x000000000000008L, 0x0000000000000010L};
            this.PiecesPosition=a;
        }
        else
        {
            long a[]={0x000000000000ff00L, 0x0000000000000081L, 0x0000000000000042L,
                      0x0000000000000024L, 0x0000000000000010L ,0x000000000000008L};
            this.PiecesPosition=a;
        }
    }
    public void setStartPositionDebug()
    {
        if (this.Color=='w')
        {   
            long a[]={0x18000000000L, 0x0000000000000000L, 0x400000L,
                      0x0000000000000000L, 0x0000000000000000L ,0x4000L};
            this.PiecesPosition=a;
        }
        else
        {
            long a[]={0x18000000000L, 0x0000000000000000L, 0x400000L,
                      0x0000000000000000L, 0x0000000000000000L ,0x4000L};
            this.PiecesPosition=a;
        }
    }

    public long getOccupiedPlaces()
    {
        return (PiecesPosition[0] | 
                PiecesPosition[1] |
                PiecesPosition[2] |
                PiecesPosition[3] |
                PiecesPosition[4] |
                PiecesPosition[5]);
    }

    public void doMove(ChessBoard cb, Move move){

        cb.game.DoMove(move);
        cb.repaint();

    }
/*    public ArrayList getPossibleMoves()
    {
        
    } */
}
