/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Listeners;

import Chess.Move;
import static Pieces.Bishop.BishopPossibleMoves;
import Chess.ChessBoard;
import Chess.Game;
import static Pieces.King.KingPossibleMoves;
import static Pieces.Knight.KnightPossibleMoves;
import Chess.NegaMax;
import static Pieces.Pawn.PawnPossibleMoves;
import static Pieces.Queen.QueenPossibleMoves;
import static Pieces.Rook.RookPossibleMoves;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import static java.time.Clock.system;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.util.concurrent.TimeUnit;

/**
 * @author itayb
 */
public class ChessBoardMouseListener implements MouseListener
{
    public ChessBoard cb;
    public Game game;
    public boolean secondTouch=false;
    public long Origin;
    public long destination;
    public int OriginBitPlace;
    public NegaMax search;
    public boolean playerTurn=true; 
    public int counter=0;
    public boolean moveMade = false;


    
    public ChessBoardMouseListener(ChessBoard cb)
    {
       this.cb=cb;
       this.game=cb.game;
       this.search=new NegaMax(this.cb);
    }
    
    public void resetClick()
    {
        cb.clearColoredPieces();
        cb.clearColoredSquares();
        this.secondTouch=false;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {

        if(this.search.isAlive()) {
            return;
        }
     /* if (counter==2){ this.search=new NegaMax(this.cb); 
           this.game.DoMove(search.bestMove); counter++; }  */

    if( this.game.GameOver())
    {
          JFrame frame=new JFrame("GAME-OVER");
          frame.setSize(200,200);
          frame.add(new JLabel("GAME-OVER"));
          frame.setVisible(true);
          frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
          frame.setLocationRelativeTo(null);
    }
    else if(!search.isAlive())
    {   
             
        int i=7-((int)e.getPoint().getY())/this.cb.CellSize; // row place  (1-8) layout 2 (bit number 1 is at the down left corner)
        int j=((int)e.getPoint().getX())/this.cb.CellSize; // col place
        int bitClickedPlace=i*8+j;
        this.destination=0x1L<<i*8+j;
        int killId=-1; // -1 means no kill.
        int type;
        
        
      if (!secondTouch && (this.game.getTypeBit(destination)!=-1) && (destination & game.PlayerOccupiedPlaces)!=0)
      {
          this.Origin= 0x1L<<i*8+j;
          this.OriginBitPlace=(i*8)+j;
          this.cb.addColoredPiece(Origin);

          
        if ((Origin & this.game.ComputerOccupiedPlaces) !=0 )//&& !this.PlayerTurn) // the clikced piece is computer's side
        {
           type=this.game.getTypeBit(Origin);
           
              switch (type) {
                  case 0:
                      this.cb.addColoredSquare(this.game.isCheck((PawnPossibleMoves(bitClickedPlace,this.game)),this.OriginBitPlace, 0));
                      break;
                  case 1:
                      this.cb.addColoredSquare(this.game.isCheck((RookPossibleMoves(bitClickedPlace, this.game)),this.OriginBitPlace, 1));
                      break;
                  case 2:
                      this.cb.addColoredSquare(this.game.isCheck((KnightPossibleMoves(bitClickedPlace, this.game)),this.OriginBitPlace,2));
                      break;
                  case 3:
                      this.cb.addColoredSquare(this.game.isCheck((BishopPossibleMoves(bitClickedPlace, this.game)),this.OriginBitPlace, 3));
                      break;
                  case 4:
                      this.cb.addColoredSquare(this.game.isCheck((QueenPossibleMoves(bitClickedPlace, this.game)),this.OriginBitPlace, 4));
                      break;
                  case 5:
                      this.cb.addColoredSquare(this.game.isCheck((KingPossibleMoves(bitClickedPlace, this.game)),this.OriginBitPlace, 5));
                      break;
              }
            
            
            this.secondTouch=true;
        }
        ///////////////////// player 
         
        if ((Origin & this.game.PlayerOccupiedPlaces) !=0)// && this.PlayerTurn) // the clikced piece is player's side
        {
           type=this.game.getTypeBit(Origin);
           
              switch (type) {
                   case 0:
                      this.cb.addColoredSquare(this.game.isCheck((PawnPossibleMoves(bitClickedPlace,this.game)),this.OriginBitPlace, 0));
                      break;
                  case 1:
                      this.cb.addColoredSquare(this.game.isCheck((RookPossibleMoves(bitClickedPlace, this.game)),this.OriginBitPlace, 1));
                      break;
                  case 2:
                      this.cb.addColoredSquare(this.game.isCheck((KnightPossibleMoves(bitClickedPlace, this.game)),this.OriginBitPlace,2));
                      break;
                  case 3:
                      this.cb.addColoredSquare(this.game.isCheck((BishopPossibleMoves(bitClickedPlace, this.game)),this.OriginBitPlace, 3));
                      break;
                  case 4:
                      this.cb.addColoredSquare(this.game.isCheck((QueenPossibleMoves(bitClickedPlace, this.game)),this.OriginBitPlace, 4));
                      break;
                  case 5:
                      this.cb.addColoredSquare(this.game.isCheck((KingPossibleMoves(bitClickedPlace, this.game)),this.OriginBitPlace, 5));
                      break;
              }
            
            
            this.secondTouch=true;
           
        }   
      }
      
      /////// SECOND TIME CLICK
      
      else // if the secondtouch == true;
      {
        if ((this.destination & this.cb.ColoredSquares)!=0)
        {
          for (i=0; i<6; i++)
          {
              if ((this.destination & this.game.Computer.PiecesPosition[i]) !=0){ killId=i;}
              if ((this.destination & this.game.Player.PiecesPosition[i]) !=0){ killId=i;}
          }
           
          int Special=this.game.getMoveSpecialId(this.OriginBitPlace,this.destination,this.game.getTypeBit(Origin));
          Move move=new Move(this.Origin, this.destination ,this.game.getTypeBit(Origin) ,killId,Special);
          this.game.Player.doMove(cb, move);


            // nega max..
            this.search=new NegaMax(cb);
            this.search.start();
           //this.game.Computer.doMove(cb);
            //System.out.println("done");

            resetClick();
          this.playerTurn^=true;
        }
        else if (this.destination==this.Origin)
        {
           resetClick();
        }
      }
       
       e.getComponent().repaint(); 

    }
   }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //ComputerMove(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
       
    }
   
    public void ComputerMove(MouseEvent e){
        // nega max..
        if (this.moveMade){
            System.out.println("doing computer move");
            this.game.Computer.doMove(cb);
            this.moveMade = false;
            e.getComponent().repaint(); 
            
        }
    }
}
