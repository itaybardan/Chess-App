package Users;

import Chess.ChessBoard;
import Chess.NegaMax;
import static Pieces.Pawn.PawnPossibleMoves;
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
public class Computer  {
    
    public long[] PiecesPosition;
    public char Color='b';
    public NegaMax search;
    
    
    public Computer(char color)
    {
        this.Color=color;
    }
    public void CopyComputer(Computer comp)
    {
        long a[]={comp.PiecesPosition[0], comp.PiecesPosition[1], comp.PiecesPosition[2], comp.PiecesPosition[3], comp.PiecesPosition[4], comp.PiecesPosition[5]};
        this.PiecesPosition=a;
        this.Color=comp.Color;
    }
    public void setStartPosition()
    {
        if(this.Color=='w')
        {
           long[] a={0xff000000000000L, 0x8100000000000000L, 0x4200000000000000L,   // order in array: { pawn, rook, knight, bishop, queen, king};
           0x2400000000000000L, 0x1000000000000000L, 0x0800000000000000L};
           this.PiecesPosition=a;
        }
        else
        {
          long[] a={ 0xff000000000000L, 0x8100000000000000L, 0x4200000000000000L,
          0x2400000000000000L, 0x0800000000000000L ,0x1000000000000000L};
          this.PiecesPosition=a;
        }
    }

    
    public void setStartPositionDebug()
    {
        if(this.Color=='w')
        {
           long[] a={0xff000000000000L, 0x8100000000000000L, 0x0000000000000000L,   // order in array: { pawn, rook, knight, bishop, queen, king};
                     0x0000000000000000L, 0x010000000000000L, 0x800000000000000L};
           this.PiecesPosition=a;
        }
        else
        {
           long[] a={ 0x47a01004000000L, 0x2200000000000000L, 0x0000000000000000L,
                      0x2000000000L, 0x40000000L ,0x4000000000000000L};
          this.PiecesPosition=a;            
        }
    }
    
    
    public void doMove(ChessBoard cb){
        
        this.search=new NegaMax(cb);
        this.search.start();
        try{
            this.search.join();
        }catch (InterruptedException e) {
            System.out.println("Main thread Interrupted");
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
}
