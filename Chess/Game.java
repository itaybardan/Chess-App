package Chess;


import Users.Player;
import Users.Computer;
import static Pieces.Bishop.BishopPossibleMoves;
import static Pieces.King.KingAllPossibleMoves;
import static Pieces.King.KingPossibleMoves;
import static Pieces.King.KingSpecialPossibleMoves;
import static Pieces.Knight.KnightPossibleMoves;
import static Pieces.Pawn.PawnPossibleMoves;
import static Pieces.Queen.QueenPossibleMoves;
import static Pieces.Rook.RookPossibleMoves;
import java.util.ArrayList;
import java.util.Stack;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author itayb
 */
  public class Game {
    
    public Computer Computer;
    public Player Player;
    public long AllOccupiedPlaces;
    public long ComputerOccupiedPlaces;
    public long PlayerOccupiedPlaces;
    public Stack<Move> undoMoves;
    public boolean PlayerRightCastling;
    public boolean PlayerLeftCastling;
    public boolean ComputerRightCastling;
    public boolean ComputerLeftCastling;
    public int pieceToPromotion=4;
    public int depth=4;

    public Game ()
    {
      this.Computer=new Computer('b');
      this.Player=new Player('w');
      this.undoMoves=new Stack();
      reCasttlingOptions();
      
      this.Player.setStartPosition();
      this.Computer.setStartPosition();
      
      updateOccupied();
 
    }
 
    public void CopyGame(Game game)
    {
        this.Computer.CopyComputer(game.Computer);
        this.Player.CopyPlayer(game.Player);
        this.ComputerLeftCastling=game.ComputerLeftCastling;
        this.ComputerRightCastling=game.ComputerRightCastling;
        this.PlayerLeftCastling=game.PlayerLeftCastling;
        this.PlayerRightCastling=game.PlayerRightCastling;
        
        this.updateOccupied();
        

        this.undoMoves.addAll(game.undoMoves);
    }
    //-----------------------------------------------------------
    public void updateOccupied()
    {
        this.ComputerOccupiedPlaces=this.Computer.getOccupiedPlaces();
        this.PlayerOccupiedPlaces= this.Player.getOccupiedPlaces();
        this.AllOccupiedPlaces= this.Computer.getOccupiedPlaces() | this.Player.getOccupiedPlaces();
    }

    public long getOccupiedPlaces()
    {
        return this.Computer.getOccupiedPlaces() | this.Player.getOccupiedPlaces();
    }

    public int getTypeBit(long bit)
    {
        if ((bit & this.Computer.PiecesPosition[0])!=0 || (bit & this.Player.PiecesPosition[0])!=0)
            return 0;
        else if ((bit & this.Computer.PiecesPosition[1])!=0 || (bit & this.Player.PiecesPosition[1])!=0)
            return 1;
        else if ((bit & this.Computer.PiecesPosition[2])!=0 || (bit & this.Player.PiecesPosition[2])!=0)
            return 2;
        else if ((bit & this.Computer.PiecesPosition[3])!=0 || (bit & this.Player.PiecesPosition[3])!=0)
            return 3;
        else if ((bit & this.Computer.PiecesPosition[4])!=0 || (bit & this.Player.PiecesPosition[4])!=0)
            return 4;
        else if ((bit & this.Computer.PiecesPosition[5])!=0 || (bit & this.Player.PiecesPosition[5])!=0)
            return 5;
        return -1;
    }
    public boolean isPlayerPiece(long bit)
    {
        return (bit & this.ComputerOccupiedPlaces) == 0;
    }
//------------------------------------------------------------------------------    
    public ArrayList<Move> getComputerPossibleMoves()
    {   
        long ps;
        ArrayList<Move> possibleMoves=new ArrayList();
        
        int place;
        long destination;
        long pieceBit;
        int bitCount;
        int bitCount2;
        
        long pieces=this.Computer.PiecesPosition[0];

        bitCount=Long.bitCount(pieces);
        for (int i=0; i<bitCount; i++)
        {
            place=Long.numberOfTrailingZeros(pieces);
            pieceBit=Long.lowestOneBit(pieces);
            ps= isCheck(PawnPossibleMoves(place,this),place,0);

            bitCount2=Long.bitCount(ps);
            for(int j=0; j<bitCount2; j++)
            {
                destination=Long.lowestOneBit(ps);                
                possibleMoves.add(new Move(pieceBit,destination,0,this.getTypeBit(destination),this.getMoveSpecialId( place, destination,0)));         
                ps^=destination;
            }
           pieces^=pieceBit;
        }
        
        pieces=this.Computer.PiecesPosition[1];
        bitCount=Long.bitCount(pieces);
        for (int i=0; i<bitCount; i++)
        {
            place=Long.numberOfTrailingZeros(pieces);
            pieceBit=Long.lowestOneBit(pieces);
            ps= isCheck(RookPossibleMoves(place,this),place,1);

            bitCount2=Long.bitCount(ps);
            for(int j=0; j<bitCount2; j++)
            {
                destination=Long.lowestOneBit(ps);                
                possibleMoves.add(new Move(pieceBit,destination,1,this.getTypeBit(destination),this.getMoveSpecialId( place, destination,1)));
                ps^=destination;
            }
           pieces^=pieceBit;
        }
        
        pieces=this.Computer.PiecesPosition[2];
        bitCount=Long.bitCount(pieces);
        for (int i=0; i<bitCount; i++)
        {
            place=Long.numberOfTrailingZeros(pieces);
            pieceBit=Long.lowestOneBit(pieces);
            ps= isCheck(KnightPossibleMoves(place,this),place,2);

            bitCount2=Long.bitCount(ps);
            for(int j=0; j<bitCount2; j++)
            {
                destination=Long.lowestOneBit(ps);                
                possibleMoves.add(new Move(pieceBit,destination,2,this.getTypeBit(destination),this.getMoveSpecialId( place, destination,2)));
                ps^=destination;
            }
           pieces^=pieceBit;
        }
        
        pieces=this.Computer.PiecesPosition[3];
        bitCount=Long.bitCount(pieces);
        for (int i=0; i<bitCount; i++)
        {
            place=Long.numberOfTrailingZeros(pieces);
            pieceBit=Long.lowestOneBit(pieces);
            ps= isCheck(BishopPossibleMoves(place,this),place,3);

            bitCount2=Long.bitCount(ps);
            for(int j=0; j<bitCount2; j++)
            {
                destination=Long.lowestOneBit(ps);                
                possibleMoves.add(new Move(pieceBit,destination,3,this.getTypeBit(destination),this.getMoveSpecialId( place, destination,3)));
                ps^=destination;
            }
           pieces^=pieceBit;
        } 
        
        pieces=this.Computer.PiecesPosition[4];
        bitCount=Long.bitCount(pieces);
        for (int i=0; i<bitCount; i++)
        {
            place=Long.numberOfTrailingZeros(pieces);
            pieceBit=Long.lowestOneBit(pieces);
            ps= isCheck(QueenPossibleMoves(place,this),place,4);

            bitCount2=Long.bitCount(ps);
            for(int j=0; j<bitCount2; j++)
            {
                destination=Long.lowestOneBit(ps);                
                possibleMoves.add(new Move(pieceBit,destination,4,this.getTypeBit(destination),this.getMoveSpecialId( place, destination,4)));
                ps^=destination;
            }
           pieces^=pieceBit;
        }
        
        pieces=this.Computer.PiecesPosition[5];
        bitCount=Long.bitCount(pieces);
        for (int i=0; i<bitCount; i++)
        {
            place=Long.numberOfTrailingZeros(pieces);
            pieceBit=Long.lowestOneBit(pieces);
            ps= isCheck(KingPossibleMoves(place,this),place,5);

            bitCount2=Long.bitCount(ps);
            for(int j=0; j<bitCount2; j++)
            {
                destination=Long.lowestOneBit(ps);                
                possibleMoves.add(new Move(pieceBit,destination,5,this.getTypeBit(destination),this.getMoveSpecialId( place, destination,5)));
                ps^=destination;
            }
           pieces^=pieceBit;
        }
                

        return possibleMoves;
    }
    
    public long getPlayerPossibleMovesLong(){

        long ps;
        ArrayList<Move> possibleMoves=new ArrayList();
        int bitCount;
        int bitCount2;
        int place;
        long destination;
        long pieceBit;


        long pieces=this.Player.PiecesPosition[0];
        bitCount=Long.bitCount(pieces);
        for (int i=0; i<bitCount; i++)
        {
            place=Long.numberOfTrailingZeros(pieces);
            pieceBit=Long.lowestOneBit(pieces);
            ps= isCheck(PawnPossibleMoves(place,this),place,0);

            bitCount2=Long.bitCount(ps);
            for(int j=0; j<bitCount2; j++)
            {
                destination=Long.lowestOneBit(ps);
                possibleMoves.add(new Move(pieceBit,destination,0,this.getTypeBit(destination),this.getMoveSpecialId( place, destination,0)));
                ps^=destination;
            }
            pieces^=pieceBit;
        }

        pieces=this.Player.PiecesPosition[1];
        bitCount=Long.bitCount(pieces);
        for (int i=0; i<bitCount; i++)
        {
            place=Long.numberOfTrailingZeros(pieces);
            pieceBit=Long.lowestOneBit(pieces);
            ps= isCheck(RookPossibleMoves(place,this),place,1);

            bitCount2=Long.bitCount(ps);
            for(int j=0; j<bitCount2; j++)
            {
                destination=Long.lowestOneBit(ps);
                possibleMoves.add(new Move(pieceBit,destination,1,this.getTypeBit(destination),this.getMoveSpecialId( place, destination,1)));
                ps^=destination;
            }
            pieces^=pieceBit;
        }

        pieces=this.Player.PiecesPosition[2];
        bitCount=Long.bitCount(pieces);
        for (int i=0; i<bitCount; i++)
        {
            place=Long.numberOfTrailingZeros(pieces);
            pieceBit=Long.lowestOneBit(pieces);
            ps= isCheck(KnightPossibleMoves(place,this),place,2);

            bitCount2=Long.bitCount(ps);
            for(int j=0; j<bitCount2; j++)
            {
                destination=Long.lowestOneBit(ps);
                possibleMoves.add(new Move(pieceBit,destination,2,this.getTypeBit(destination),this.getMoveSpecialId( place, destination,2)));
                ps^=destination;
            }
            pieces^=pieceBit;
        }

        pieces=this.Player.PiecesPosition[3];
        bitCount=Long.bitCount(pieces);
        for (int i=0; i<bitCount; i++)
        {
            place=Long.numberOfTrailingZeros(pieces);
            pieceBit=Long.lowestOneBit(pieces);
            ps= isCheck(BishopPossibleMoves(place,this),place,3);

            bitCount2=Long.bitCount(ps);
            for(int j=0; j<bitCount2; j++)
            {
                destination=Long.lowestOneBit(ps);
                possibleMoves.add(new Move(pieceBit,destination,3,this.getTypeBit(destination),this.getMoveSpecialId( place, destination,3)));
                ps^=destination;
            }
            pieces^=pieceBit;
        }

        pieces=this.Player.PiecesPosition[4];
        bitCount=Long.bitCount(pieces);
        for (int i=0; i<bitCount; i++)
        {
            place=Long.numberOfTrailingZeros(pieces);
            pieceBit=Long.lowestOneBit(pieces);
            ps= isCheck(QueenPossibleMoves(place,this),place,4);

            bitCount2=Long.bitCount(ps);
            for(int j=0; j<bitCount2; j++)
            {
                destination=Long.lowestOneBit(ps);
                possibleMoves.add(new Move(pieceBit,destination,4,this.getTypeBit(destination),this.getMoveSpecialId( place, destination,4)));
                ps^=destination;
            }
            pieces^=pieceBit;
        }

        pieces=this.Player.PiecesPosition[5];
        bitCount=Long.bitCount(pieces);
        for (int i=0; i<bitCount; i++)
        {
            place=Long.numberOfTrailingZeros(pieces);
            pieceBit=Long.lowestOneBit(pieces);
            ps= isCheck(KingSpecialPossibleMoves(place,this),place,5);

            bitCount2=Long.bitCount(ps);
            for(int j=0; j<bitCount2; j++)
            {
                destination=Long.lowestOneBit(ps);
                possibleMoves.add(new Move(pieceBit,destination,5,this.getTypeBit(destination),this.getMoveSpecialId( place, destination,5)));
                ps^=destination;
            }
            pieces^=pieceBit;
        }


        long destinations = 0x0L;
        for(Move move : possibleMoves){
            destinations |= move.destination;
        }
        
        return destinations;
    }

    public long getComputerPossibleMovesLong(){
        ArrayList<Move> possibleMoves= getComputerPossibleMoves();
        long ps;

        int place;
        long destination;
        long pieceBit;
        int bitCount;
        int bitCount2;

        long pieces=this.Computer.PiecesPosition[0];

        bitCount=Long.bitCount(pieces);
        for (int i=0; i<bitCount; i++)
        {
            place=Long.numberOfTrailingZeros(pieces);
            pieceBit=Long.lowestOneBit(pieces);
            ps= isCheck(PawnPossibleMoves(place,this),place,0);

            bitCount2=Long.bitCount(ps);
            for(int j=0; j<bitCount2; j++)
            {
                destination=Long.lowestOneBit(ps);
                possibleMoves.add(new Move(pieceBit,destination,0,this.getTypeBit(destination),this.getMoveSpecialId( place, destination,0)));
                ps^=destination;
            }
            pieces^=pieceBit;
        }

        pieces=this.Computer.PiecesPosition[1];
        bitCount=Long.bitCount(pieces);
        for (int i=0; i<bitCount; i++)
        {
            place=Long.numberOfTrailingZeros(pieces);
            pieceBit=Long.lowestOneBit(pieces);
            ps= isCheck(RookPossibleMoves(place,this),place,1);

            bitCount2=Long.bitCount(ps);
            for(int j=0; j<bitCount2; j++)
            {
                destination=Long.lowestOneBit(ps);
                possibleMoves.add(new Move(pieceBit,destination,1,this.getTypeBit(destination),this.getMoveSpecialId( place, destination,1)));
                ps^=destination;
            }
            pieces^=pieceBit;
        }

        pieces=this.Computer.PiecesPosition[2];
        bitCount=Long.bitCount(pieces);
        for (int i=0; i<bitCount; i++)
        {
            place=Long.numberOfTrailingZeros(pieces);
            pieceBit=Long.lowestOneBit(pieces);
            ps= isCheck(KnightPossibleMoves(place,this),place,2);

            bitCount2=Long.bitCount(ps);
            for(int j=0; j<bitCount2; j++)
            {
                destination=Long.lowestOneBit(ps);
                possibleMoves.add(new Move(pieceBit,destination,2,this.getTypeBit(destination),this.getMoveSpecialId( place, destination,2)));
                ps^=destination;
            }
            pieces^=pieceBit;
        }

        pieces=this.Computer.PiecesPosition[3];
        bitCount=Long.bitCount(pieces);
        for (int i=0; i<bitCount; i++)
        {
            place=Long.numberOfTrailingZeros(pieces);
            pieceBit=Long.lowestOneBit(pieces);
            ps= isCheck(BishopPossibleMoves(place,this),place,3);

            bitCount2=Long.bitCount(ps);
            for(int j=0; j<bitCount2; j++)
            {
                destination=Long.lowestOneBit(ps);
                possibleMoves.add(new Move(pieceBit,destination,3,this.getTypeBit(destination),this.getMoveSpecialId( place, destination,3)));
                ps^=destination;
            }
            pieces^=pieceBit;
        }

        pieces=this.Computer.PiecesPosition[4];
        bitCount=Long.bitCount(pieces);
        for (int i=0; i<bitCount; i++)
        {
            place=Long.numberOfTrailingZeros(pieces);
            pieceBit=Long.lowestOneBit(pieces);
            ps= isCheck(QueenPossibleMoves(place,this),place,4);

            bitCount2=Long.bitCount(ps);
            for(int j=0; j<bitCount2; j++)
            {
                destination=Long.lowestOneBit(ps);
                possibleMoves.add(new Move(pieceBit,destination,4,this.getTypeBit(destination),this.getMoveSpecialId( place, destination,4)));
                ps^=destination;
            }
            pieces^=pieceBit;
        }

        pieces=this.Computer.PiecesPosition[5];
        bitCount=Long.bitCount(pieces);
        for (int i=0; i<bitCount; i++)
        {
            place=Long.numberOfTrailingZeros(pieces);
            pieceBit=Long.lowestOneBit(pieces);
            ps= isCheck(KingSpecialPossibleMoves(place,this),place,5);

            bitCount2=Long.bitCount(ps);
            for(int j=0; j<bitCount2; j++)
            {
                destination=Long.lowestOneBit(ps);
                possibleMoves.add(new Move(pieceBit,destination,5,this.getTypeBit(destination),this.getMoveSpecialId( place, destination,5)));
                ps^=destination;
            }
            pieces^=pieceBit;
        }

        long destinations = 0x0L;
        for(Move move : possibleMoves){
            destinations |= move.destination;
        }
        
        return destinations;
    }
    
    public ArrayList<Move> getPlayerPossibleMoves()
    {   
        /*long bit=0x1L;
        long bit2=0x1L;*/
        long ps;
        ArrayList<Move> possibleMoves=new ArrayList();
        int bitCount;
        int bitCount2;
        int place;
        long destination;
        long pieceBit;
        
 
        long pieces=this.Player.PiecesPosition[0];
        bitCount=Long.bitCount(pieces);
        for (int i=0; i<bitCount; i++)
        {
            place=Long.numberOfTrailingZeros(pieces);
            pieceBit=Long.lowestOneBit(pieces);
            ps= isCheck(PawnPossibleMoves(place,this),place,0);

            bitCount2=Long.bitCount(ps);
            for(int j=0; j<bitCount2; j++)
            {
                destination=Long.lowestOneBit(ps);                
                possibleMoves.add(new Move(pieceBit,destination,0,this.getTypeBit(destination),this.getMoveSpecialId( place, destination,0)));
                ps^=destination;
            }
           pieces^=pieceBit;
        }
        
        pieces=this.Player.PiecesPosition[1];
        bitCount=Long.bitCount(pieces);
        for (int i=0; i<bitCount; i++)
        {
            place=Long.numberOfTrailingZeros(pieces);
            pieceBit=Long.lowestOneBit(pieces);
            ps= isCheck(RookPossibleMoves(place,this),place,1);

            bitCount2=Long.bitCount(ps);
            for(int j=0; j<bitCount2; j++)
            {
                destination=Long.lowestOneBit(ps);                
                possibleMoves.add(new Move(pieceBit,destination,1,this.getTypeBit(destination),this.getMoveSpecialId( place, destination,1)));
                ps^=destination;
            }
           pieces^=pieceBit;
        }
        
        pieces=this.Player.PiecesPosition[2];
        bitCount=Long.bitCount(pieces);
        for (int i=0; i<bitCount; i++)
        {
            place=Long.numberOfTrailingZeros(pieces);
            pieceBit=Long.lowestOneBit(pieces);
            ps= isCheck(KnightPossibleMoves(place,this),place,2);

            bitCount2=Long.bitCount(ps);
            for(int j=0; j<bitCount2; j++)
            {
                destination=Long.lowestOneBit(ps);                
                possibleMoves.add(new Move(pieceBit,destination,2,this.getTypeBit(destination),this.getMoveSpecialId( place, destination,2)));
                ps^=destination;
            }
           pieces^=pieceBit;
        }
        
        pieces=this.Player.PiecesPosition[3];
        bitCount=Long.bitCount(pieces);
        for (int i=0; i<bitCount; i++)
        {
            place=Long.numberOfTrailingZeros(pieces);
            pieceBit=Long.lowestOneBit(pieces);
            ps= isCheck(BishopPossibleMoves(place,this),place,3);

            bitCount2=Long.bitCount(ps);
            for(int j=0; j<bitCount2; j++)
            {
                destination=Long.lowestOneBit(ps);                
                possibleMoves.add(new Move(pieceBit,destination,3,this.getTypeBit(destination),this.getMoveSpecialId( place, destination,3)));
                ps^=destination;
            }
           pieces^=pieceBit;
        } 
        
        pieces=this.Player.PiecesPosition[4];
        bitCount=Long.bitCount(pieces);
        for (int i=0; i<bitCount; i++)
        {
            place=Long.numberOfTrailingZeros(pieces);
            pieceBit=Long.lowestOneBit(pieces);
            ps= isCheck(QueenPossibleMoves(place,this),place,4);

            bitCount2=Long.bitCount(ps);
            for(int j=0; j<bitCount2; j++)
            {
                destination=Long.lowestOneBit(ps);                
                possibleMoves.add(new Move(pieceBit,destination,4,this.getTypeBit(destination),this.getMoveSpecialId( place, destination,4)));
                ps^=destination;
            }
           pieces^=pieceBit;
        }
        
        pieces=this.Player.PiecesPosition[5];
        bitCount=Long.bitCount(pieces);
        for (int i=0; i<bitCount; i++)
        {
            place=Long.numberOfTrailingZeros(pieces);
            pieceBit=Long.lowestOneBit(pieces);
            ps= isCheck(KingPossibleMoves(place,this),place,5);

            bitCount2=Long.bitCount(ps);
            for(int j=0; j<bitCount2; j++)
            {
                destination=Long.lowestOneBit(ps);                
                possibleMoves.add(new Move(pieceBit,destination,5,this.getTypeBit(destination),this.getMoveSpecialId( place, destination,5)));
                ps^=destination;
            }
           pieces^=pieceBit;
        }
                
 
        return possibleMoves;
    }
//--------------------------------------------------------------------------------------------------------------
    public void newGame()
    {
        /*if (this.Player.Color=='b')
        {
            this.Player.setBStartPosition();
            this.Computer.setWStartPosition();
        }
        else
        {
            this.Player.setWStartPosition();
            this.Computer.setBStartPosition();
        } */
        
        // need t oask for color changing!!!
        /// will be added
        this.Computer.setStartPosition();
        this.Player.setStartPosition();

         this.reCasttlingOptions();
         this.updateOccupied();
         this.undoMoves.clear();
         
         //System.out.println("after new game");
         //this.print();
         
    }
//---------------------------------------------------------------------------------------------------------
    public void reCasttlingOptions()
    {
        this.ComputerLeftCastling=true;
        this.ComputerRightCastling=true;
        this.PlayerLeftCastling=true;
        this.PlayerRightCastling=true; 
    }
//---------------------------------------------------------------------------------------------------------
    public boolean GameOver()
    {
        return isGameOver(true)!=0|| isGameOver(false)!=0;
    }
//-------------------------------------------------------------------------------
    public  long isCheck(long PossibleMoves,int place,int Serial) // get the pogetssible moves and delete the moves that can "check" the king
    {

      long Origin=0x1L<<place;
      boolean isPlayerPiece=this.isPlayerPiece(Origin);
      long legalMoves=0x0L;
      long lowBit=0x0L;
      int numMoves=Long.bitCount(PossibleMoves);
      for (int i=0; i<numMoves; i++)
      {
          lowBit=Long.lowestOneBit(PossibleMoves);
          int Special=this.getMoveSpecialId(place,lowBit,Serial);
          Move move=new Move(Origin, lowBit, Serial,this.getTypeBit(lowBit),Special);
          this.DoMove(move);
          if(!this.isKingUnderThreat(isPlayerPiece))
              legalMoves|=lowBit;       
         
          PossibleMoves^=lowBit;
          this.undoMove();
      }

      return legalMoves;
      
    }
//------------------------------------------------------------------------------------------------------
    public  int isGameOver(boolean PlayerTurn) // will return -1 for tie, 99999 for checkmate, 0 for nothing;
    {
        long bit;
        long pieces;
        
        if (this.undoMoves.size()>=6)
        {
            int index=this.undoMoves.size()-1;
            if(undoMoves.get(index).Origin== undoMoves.get(index-4).Origin &&
               undoMoves.get(index).destination== undoMoves.get(index-4).destination &&
               undoMoves.get(index-1).Origin== undoMoves.get(index-5).Origin &&
               undoMoves.get(index-1).destination== undoMoves.get(index-5).destination)
             return -1;
     
        }
        
        if (PlayerTurn)
        {  
            for(int i=0; i<6; i++)
            {
              pieces=this.Player.PiecesPosition[i];
              int numPieces=Long.bitCount(pieces);
              for (int j=0; j<numPieces; j++)
              {
                bit=Long.lowestOneBit(pieces);
                switch (i){
                    case 0 : 
                        if (isCheck(PawnPossibleMoves(Long.numberOfTrailingZeros(bit),this),Long.numberOfTrailingZeros(bit),0)!=0x0L) return 0;
                        break;
                    case 1 : 
                        if (isCheck(RookPossibleMoves(Long.numberOfTrailingZeros(bit),this),Long.numberOfTrailingZeros(bit),1)!=0x0L) return 0;
                        break;
                    case 2 :  
                        if (isCheck(KnightPossibleMoves(Long.numberOfTrailingZeros(bit),this),Long.numberOfTrailingZeros(bit),2)!=0x0L) return 0;
                        break;
                    case 3 : 
                        if (isCheck(BishopPossibleMoves(Long.numberOfTrailingZeros(bit),this),Long.numberOfTrailingZeros(bit),3)!=0x0L) return 0;
                        break;
                    case 4 :
                        if (isCheck(QueenPossibleMoves(Long.numberOfTrailingZeros(bit),this),Long.numberOfTrailingZeros(bit),4)!=0x0L) return 0;
                        break;
                    case 5 :
                        if (isCheck(KingPossibleMoves(Long.numberOfTrailingZeros(bit),this),Long.numberOfTrailingZeros(bit),5)!=0x0L) return 0;
                        break;
                    
                }
                pieces^=bit;
              }
                          
            }
            
            if (!this.isKingUnderThreat(true))
                return -50000;   
        }
        else {
            for(int i=0; i<6; i++)
            {
              pieces=this.Computer.PiecesPosition[i];
              
              int numPieces=Long.bitCount(pieces);
              for (int j=0; j<numPieces; j++)
              {
                bit=Long.lowestOneBit(pieces);
                switch (i){
                    case 0 : 
                        if (isCheck(PawnPossibleMoves(Long.numberOfTrailingZeros(bit),this),Long.numberOfTrailingZeros(bit),0)!=0x0L) return 0;
                        break;
                    case 1 : 
                        if (isCheck(RookPossibleMoves(Long.numberOfTrailingZeros(bit),this),Long.numberOfTrailingZeros(bit),1)!=0x0L) return 0;
                        break;
                    case 2 :  
                        if (isCheck(KnightPossibleMoves(Long.numberOfTrailingZeros(bit),this),Long.numberOfTrailingZeros(bit),2)!=0x0L) return 0;
                        break;
                    case 3 : 
                        if (isCheck(BishopPossibleMoves(Long.numberOfTrailingZeros(bit),this),Long.numberOfTrailingZeros(bit),3)!=0x0L) return 0;
                        break;
                    case 4 :
                        if (isCheck(QueenPossibleMoves(Long.numberOfTrailingZeros(bit),this),Long.numberOfTrailingZeros(bit),4)!=0x0L) return 0;
                        break;
                    case 5 :
                        if (isCheck(KingPossibleMoves(Long.numberOfTrailingZeros(bit),this),Long.numberOfTrailingZeros(bit),5)!=0x0L) return 0;
                        break;
                }
                pieces^=bit;
              }
              
            }  

            if (!this.isKingUnderThreat(false))
                return -1; 
        }    

       return 99999;
        
    }
///----------------------------------------------------------------------------------------------------------------
    public  boolean isKingUnderThreat(boolean PlayerTurn)
    {
        long temp;
        long myKing= PlayerTurn ? this.Player.PiecesPosition[5] : this.Computer.PiecesPosition[5];
        long pawnPos;
        long knightPos;
        int place=Long.numberOfTrailingZeros(myKing);
        long pieces;
        int endValue;
        
        if (PlayerTurn)
        {
            pawnPos=((this.Computer.PiecesPosition[0] & 0xfefefefefefefefeL)>>>9) | ((this.Computer.PiecesPosition[0] & 0x7f7f7f7f7f7f7f7fL)>>>7);
            if((myKing & pawnPos) !=0)return true; 
            
            knightPos= (this.Computer.PiecesPosition[2] & 0x7f7f7f7f7f7f7f7fL)<<17  |
                       (this.Computer.PiecesPosition[2] & 0xfefefefefefefefeL)<<15  |
                       (this.Computer.PiecesPosition[2] & 0x3f3f3f3f3f3f3f3fL)<<10  |
                       (this.Computer.PiecesPosition[2] & 0xfcfcfcfcfcfcfcfcL)<<6   |
                       (this.Computer.PiecesPosition[2] & 0xfefefefefefefefeL)>>>17 |
                       (this.Computer.PiecesPosition[2] & 0x7f7f7f7f7f7f7f7fL)>>>15 |
                       (this.Computer.PiecesPosition[2] & 0xfcfcfcfcfcfcfcfcL)>>>10 |
                       (this.Computer.PiecesPosition[2] & 0x3f3f3f3f3f3f3f3fL)>>>6  ;
            if((myKing & knightPos) !=0)return true;

            if (this.Computer.PiecesPosition[5]==0x0L)return true; // temporary
            if (( myKing & KingAllPossibleMoves[Long.numberOfTrailingZeros(this.Computer.PiecesPosition[5])]) !=0)
                return true;
                            
            //----------------------------------------------------------------------------------------------------

//--------------------------------------------------------------------------------------------------------------------------------------   
            temp=myKing<<8;
            endValue=(7-(place/8));
            for (int i=0; i<endValue; i++) // up
            {
                if( (temp & this.AllOccupiedPlaces) !=0)
                {
                    if ((temp & (this.Computer.PiecesPosition[1] | this.Computer.PiecesPosition[4]))!=0)
                        return true;
                    break;
                                
                }
                temp<<=8;
            }
//--------------------------------------------------------------------------------------------------------------------------------------               
            temp=myKing<<1;
            for (int i=0; i<(7-(place%8)); i++) // right
            {
                if( (temp & this.AllOccupiedPlaces) !=0)
                {
                    if ((temp & (this.Computer.PiecesPosition[1] | this.Computer.PiecesPosition[4]))!=0)
                        return true;
                    break;
                                
                }
                temp<<=1;
            }
//--------------------------------------------------------------------------------------------------------------------------------------   
            temp=myKing>>>8;
            for (int i=0; i<(place/8); i++) // down
            {
                if( (temp & this.AllOccupiedPlaces) !=0)
                {
                    if ((temp & (this.Computer.PiecesPosition[1] | this.Computer.PiecesPosition[4]))!=0)
                        return true;
                    break;
                                
                }
                temp>>>=8;
            }
//--------------------------------------------------------------------------------------------------------------------------------------                       
            temp=myKing>>>1;
            for (int i=0; i<(place%8); i++) // left
            {
                if( (temp & this.AllOccupiedPlaces) !=0)
                {
                    if ((temp & (this.Computer.PiecesPosition[1] | this.Computer.PiecesPosition[4]))!=0)
                        return true;
                    break;
                                
                }
                temp>>>=1;
            } 
//-------------------------------------------------------------------------------------------------------------------------------------- 
            temp=myKing<<9;
            for (int i=0; i<Math.min(7-place%8, 7-place/8); i++) // up right
            {
                if( (temp & this.AllOccupiedPlaces) !=0)
                {
                    if ((temp & (this.Computer.PiecesPosition[3] | this.Computer.PiecesPosition[4]))!=0)
                        return true;
                    break;
                } 
                temp<<=9;
            }
//--------------------------------------------------------------------------------------------------------------------------------------             
            temp=myKing>>>7;
            for (int i=0; i<Math.min(7-(place%8), place/8); i++) // down-right
            {
                if( (temp & this.AllOccupiedPlaces) !=0)
                {
                    if ((temp & (this.Computer.PiecesPosition[3] | this.Computer.PiecesPosition[4]))!=0)
                        return true;
                    break;
                } 
                temp>>>=7;
            }
//--------------------------------------------------------------------------------------------------------------------------------------             
            temp=myKing>>>9;
            for (int i=0; i<Math.min((place%8), place/8); i++) // down left
            {
                if( (temp & this.AllOccupiedPlaces) !=0)
                {
                    if ((temp & (this.Computer.PiecesPosition[3] | this.Computer.PiecesPosition[4]))!=0)
                        return true;
                    break;
                } 
                temp>>>=9;
            }
//--------------------------------------------------------------------------------------------------------------------------------------             
            temp=myKing<<7;
            for (int i=0; i<Math.min(place%8, 7-(place/8)); i++) // up left
            {
                if( (temp & this.AllOccupiedPlaces) !=0)
                {
                    if ((temp & (this.Computer.PiecesPosition[3] | this.Computer.PiecesPosition[4]))!=0)
                        return true;
                    break;
                } 
                temp<<=7;
            }
        
//-------------------------------------------------------------------------------------------------------------------------------------- 

                       
        }
        else 
        {
            //System.out.println("my kING "+Long.toHexString(myKing));
            pawnPos=((this.Player.PiecesPosition[0] & 0xfefefefefefefefeL)<<7) | ((this.Player.PiecesPosition[0] & 0x7f7f7f7f7f7f7f7fL)<<9);
            if((myKing & pawnPos) !=0)return true;
            
            knightPos= (this.Player.PiecesPosition[2] & 0x7f7f7f7f7f7f7f7fL)<<17  |
                       (this.Player.PiecesPosition[2] & 0xfefefefefefefefeL)<<15  |
                       (this.Player.PiecesPosition[2] & 0x3f3f3f3f3f3f3f3fL)<<10  |
                       (this.Player.PiecesPosition[2] & 0xfcfcfcfcfcfcfcfcL)<<6   |
                       (this.Player.PiecesPosition[2] & 0xfefefefefefefefeL)>>>17 |
                       (this.Player.PiecesPosition[2] & 0x7f7f7f7f7f7f7f7fL)>>>15 |
                       (this.Player.PiecesPosition[2] & 0xfcfcfcfcfcfcfcfcL)>>>10 |
                       (this.Player.PiecesPosition[2] & 0x3f3f3f3f3f3f3f3fL)>>>6  ;
            if((myKing & knightPos) !=0)return true;

            //System.out.println("MYkING "+Long.toHexString(myKing)+"  op "+Long.toHexString(KingAllPossibleMoves[Long.numberOfTrailingZeros(this.Player.PiecesPosition[5])]));
            if (this.Player.PiecesPosition[5]==0x0L)return true;
            if (( myKing & KingAllPossibleMoves[Long.numberOfTrailingZeros(this.Player.PiecesPosition[5])]) !=0)
            return true;
            //----------------------------------------------------------------------------------------------------

 
//--------------------------------------------------------------------------------------------------------------------------------------   
            temp=myKing<<8;
            for (int i=0; i<(7-(place/8)); i++) // up
            {
                if( (temp & this.AllOccupiedPlaces) !=0)
                {
                    if ((temp & (this.Player.PiecesPosition[1] | this.Player.PiecesPosition[4]))!=0)
                        return true;
                    break;
                                
                }
                temp<<=8;
            }
//--------------------------------------------------------------------------------------------------------------------------------------               
            temp=myKing<<1;
            for (int i=0; i<(7-(place%8)); i++) // right
            {
                if( (temp & this.AllOccupiedPlaces) !=0)
                {
                    if ((temp & (this.Player.PiecesPosition[1] | this.Player.PiecesPosition[4]))!=0)
                        return true;
                    break;
                                
                }
                temp<<=1;
            }
//--------------------------------------------------------------------------------------------------------------------------------------   
            temp=myKing>>>8;
            for (int i=0; i<(place/8); i++) // down
            {
                if( (temp & this.AllOccupiedPlaces) !=0)
                {
                    if ((temp & (this.Player.PiecesPosition[1] | this.Player.PiecesPosition[4]))!=0)
                        return true;
                    break;
                                
                }
                temp>>>=8;
            }
//--------------------------------------------------------------------------------------------------------------------------------------                       
            temp=myKing>>>1;
            for (int i=0; i<(place%8); i++) // left
            {
                if( (temp & this.AllOccupiedPlaces) !=0)
                {
                    if ((temp & (this.Player.PiecesPosition[1] | this.Player.PiecesPosition[4]))!=0)
                        return true;
                    break;
                                
                }
                temp>>>=1;
            } 
//-------------------------------------------------------------------------------------------------------------------------------------- 
            temp=myKing<<9;
            for (int i=0; i<Math.min(7-place%8, 7-place/8); i++) // up right
            {
                if( (temp & this.AllOccupiedPlaces) !=0)
                {
                    if ((temp & (this.Player.PiecesPosition[3] | this.Player.PiecesPosition[4]))!=0)
                        return true;
                    break;
                } 
                temp<<=9;
            }
//--------------------------------------------------------------------------------------------------------------------------------------             
            temp=myKing>>>7;
            for (int i=0; i<Math.min(7-(place%8), place/8); i++) // down-right
            {
                if( (temp & this.AllOccupiedPlaces) !=0)
                {
                    if ((temp & (this.Player.PiecesPosition[3] | this.Player.PiecesPosition[4]))!=0)
                        return true;
                    break;
                } 
                temp>>>=7;
            }
//--------------------------------------------------------------------------------------------------------------------------------------             
            temp=myKing>>>9;
            for (int i=0; i<Math.min((place%8), place/8); i++) // down left
            {
                if( (temp & this.AllOccupiedPlaces) !=0)
                {
                    if ((temp & (this.Player.PiecesPosition[3] | this.Player.PiecesPosition[4]))!=0)
                        return true;
                    break;
                } 
                temp>>>=9;
            }
//--------------------------------------------------------------------------------------------------------------------------------------             
            temp=myKing<<7;
            for (int i=0; i<Math.min(place%8, 7-(place/8)); i++) // up left
            {
                if( (temp & this.AllOccupiedPlaces) !=0)
                {
                    if ((temp & (this.Player.PiecesPosition[3] | this.Player.PiecesPosition[4]))!=0)
                        return true;
                    break;
                } 
                temp<<=7;
            }
        }
//-------------------------------------------------------------------------------------------------------------------------------------- 


      return false; 
        
    }
//---------------------------------------------------------------------------------------------------------------------------
    public  void DoMove(Move move)
    {
     
       if ((move.Origin & this.PlayerOccupiedPlaces)!=0) 
       {
          //System.out.println("player piece");
          this.Player.PiecesPosition[move.Serial]^=move.Origin;
          this.Player.PiecesPosition[move.Serial]|=move.destination;
          
          if (move.killId!=-1 ) // if this move is a capture move
          {           
              this.Computer.PiecesPosition[move.killId]^=move.destination;
          }        
          
       }
       else 
       {
          this.Computer.PiecesPosition[move.Serial]^=move.Origin;
          this.Computer.PiecesPosition[move.Serial]|=move.destination;
          
          if (move.killId!=-1 ) // if this move is a capture move
          {
             this.Player.PiecesPosition[move.killId]^=move.destination;           
          }
                
        } 

        // handle Special moves
          switch (move.Special){
              case 0: 
                this.Player.PiecesPosition[1]^=0x1L;
                this.Player.PiecesPosition[1]|=this.Player.PiecesPosition[5]<<1;
                this.PlayerLeftCastling=false;
                break;
              case 1:  
                this.Player.PiecesPosition[1]^=0x80L;
                this.Player.PiecesPosition[1]|=this.Player.PiecesPosition[5]>>>1;
                this.PlayerRightCastling=false;  
                break;
              case 2:
                this.Computer.PiecesPosition[1]^=0x100000000000000L;
                this.Computer.PiecesPosition[1]|=this.Computer.PiecesPosition[5]<<1;
                this.ComputerLeftCastling=false; 
                break;
              case 3:
                this.Computer.PiecesPosition[1]^=0x8000000000000000L;
                this.Computer.PiecesPosition[1]|=this.Computer.PiecesPosition[5]>>>1;
                this.ComputerRightCastling=false; 
                break;
              case 4:
                this.Player.PiecesPosition[0]^=move.destination;
                System.out.println("this.pieceToPromotion "+this.pieceToPromotion);
                this.Player.PiecesPosition[this.pieceToPromotion]|=move.destination;
                break;
              case 5:
                this.Computer.PiecesPosition[0]^=move.destination;
                this.Computer.PiecesPosition[4]|=move.destination;
                break;
              case 6:
                this.Computer.PiecesPosition[0]^=move.destination>>>8;
                break;
              case 7:
                this.Player.PiecesPosition[0]^=move.destination<<8;
                break;
            case 8:
                this.PlayerLeftCastling=false;
                break;                  
            case 9:
                this.PlayerRightCastling=false;
                break;
            case 10:
                this.PlayerLeftCastling=false;
                this.PlayerRightCastling=false;
                break;
            case 11:
                this.ComputerLeftCastling=false;
                break;                
            case 12:
                this.ComputerRightCastling=false;
                break;
            case 13:
                this.ComputerLeftCastling=false;
                this.ComputerRightCastling=false;                
                break;
            
          }
          
      this.updateOccupied();
      this.undoMoves.push(move);
       
    }
//--------------------------------------------------------------------------------------------------------------
    public void undoMove()
    {
        Stack<Move> undoMoves=this.undoMoves;
        Move move=undoMoves.pop();
        
        
        if ((move.destination & this.ComputerOccupiedPlaces)!=0) // if that was computer turn
        {
            if (move.killId!=-1)
                this.Player.PiecesPosition[move.killId]|=move.destination;

            this.Computer.PiecesPosition[move.Serial]^=move.destination;
            this.Computer.PiecesPosition[move.Serial]|=move.Origin;
            
        }
        else // if that was player turn
        { 
            if (move.killId!=-1)
               this.Computer.PiecesPosition[move.killId]|=move.destination;
 
            this.Player.PiecesPosition[move.Serial]^=move.destination;
            this.Player.PiecesPosition[move.Serial]|=move.Origin;
        }
        
        
        switch (move.Special){
             case 0: 
                this.Player.PiecesPosition[1]^=this.Player.PiecesPosition[5]>>>1;
                this.Player.PiecesPosition[1]|=0x1L;
                this.PlayerLeftCastling=true;
                break;
              case 1:  
                this.Player.PiecesPosition[1]^=this.Player.PiecesPosition[5]<<1;
                this.Player.PiecesPosition[1]|=0x80L;
                this.PlayerRightCastling=true;  
                break;
              case 2:
                this.Computer.PiecesPosition[1]^=this.Computer.PiecesPosition[5]>>>1;
                this.Computer.PiecesPosition[1]|=0x100000000000000L;
                this.ComputerLeftCastling=true; 
                break;
              case 3:
                this.Computer.PiecesPosition[1]^=this.Computer.PiecesPosition[5]<<1;
                this.Computer.PiecesPosition[1]|=0x8000000000000000L;
                this.ComputerRightCastling=true; 
                break;
            case 4:
                this.Player.PiecesPosition[0]^=move.destination; 
                this.Player.PiecesPosition[this.pieceToPromotion]^=move.destination;
                break;
            case 5:
                this.Computer.PiecesPosition[0]^=move.destination;
                this.Computer.PiecesPosition[this.pieceToPromotion]^=move.destination;
                break;
            case 6:
                this.Computer.PiecesPosition[0]|=move.destination>>>8;
                break;
            case 7:
                this.Player.PiecesPosition[0]|=move.destination<<8;
                break;
            case 8:
                this.PlayerLeftCastling=true;
                break;                  
            case 9:
                this.PlayerRightCastling=true;
                break;
            case 10:
                this.PlayerLeftCastling=true;
                this.PlayerRightCastling=true;
                break;
            case 11:
                this.ComputerLeftCastling=true;
                break;                
            case 12:
                this.ComputerRightCastling=true;
                break;
            case 13:
                this.ComputerLeftCastling=true;
                this.ComputerRightCastling=true;                
                break;
        }
        
      this.updateOccupied();
      
    }
    //----------------------------------------------------------------------------------
    public  int getMoveSpecialId( int place, long destination ,int Serial)
    {
        long Origin=0x1L<<place;
        boolean isPlayer=this.isPlayerPiece(Origin);
        long myKing= isPlayer ? this.Player.PiecesPosition[5] : this.Computer.PiecesPosition[5];
        
          if (Serial==5 && (KingAllPossibleMoves[place] & destination)==0)
          {
             if(isPlayer)
             {
                 return this.Player.PiecesPosition[5]>>>2==destination ? 0 : 1 ;
             }
             return this.Computer.PiecesPosition[5]>>>2==destination ? 2 : 3 ;
             /*if ((destination==myKing>>>2) && isPlayer)
                 return 0;
             else if ((destination==myKing<<2) && isPlayer)
                 return 1;
             else if ((destination==myKing>>>2) && !isPlayer)
                 return 2;
             else  if ((destination==myKing<<2) && !isPlayer)
                 return 3;*/
             
          }
          else if (isPlayer && Serial==0 && (destination & 0xff00000000000000L)!=0)
            return 4; // pawn crwoning player
          else if (!isPlayer && Serial==0 && (destination & 0xffL)!=0)
            return 5; // pawn crowning comp;
          else if (Serial==0  && (destination & this.AllOccupiedPlaces)==0 && (((destination & Origin<<7)!=0) || (destination & Origin<<9) !=0))
            return 6; // en passant (הכאה דרך הילוכו) plyer
          else if (Serial==0  && (destination & this.AllOccupiedPlaces)==0 && (((destination & Origin>>>7)!=0) || (destination & Origin>>>9) !=0))
            return 7; // en passant (הכאה דרך הילוכו)  computer
          
         else if ((isPlayer && Serial==1 && this.PlayerLeftCastling && place==0 ) || (!isPlayer && destination==0x1L &&  this.PlayerLeftCastling)|| isPlayer && Serial==5 && this.PlayerLeftCastling && !this.PlayerRightCastling)
            return 8;
          else if ((isPlayer && Serial==1 && this.PlayerRightCastling && place==7 ) || (!isPlayer && destination==0x80L && this.PlayerRightCastling) || isPlayer && Serial==5 && !this.PlayerLeftCastling && this.PlayerRightCastling)
            return 9;
          else if ((isPlayer && Serial==5 && (this.PlayerLeftCastling && this.PlayerRightCastling)))
            return 10;
          else if ((!isPlayer && Serial==1 && this.ComputerLeftCastling && place==56) || (isPlayer && destination==0x100000000000000L &&  this.ComputerLeftCastling) || !isPlayer && Serial==5 && this.ComputerLeftCastling && !this.ComputerRightCastling)
            return 11;
          else if ((!isPlayer &&Serial==1 && this.ComputerRightCastling && place==63) || (isPlayer && destination==0x8000000000000000L && this.ComputerRightCastling)|| !isPlayer && Serial==5 && !this.ComputerLeftCastling && this.ComputerRightCastling)
            return 12;
          else if (!isPlayer && Serial==5 && (this.ComputerLeftCastling && this.ComputerRightCastling)) // computetr king move and we need update casttling possbilities
            return 13;
          
    return -1;
    }
    public void print()
    {
        System.out.println("player");
        System.out.print("p pawn "+Long.toHexString(this.Player.PiecesPosition[0]));
        System.out.print("  p rook "+Long.toHexString(this.Player.PiecesPosition[1]));
        System.out.print("  p knight "+Long.toHexString(this.Player.PiecesPosition[2]));
        System.out.print("  p bishop "+Long.toHexString(this.Player.PiecesPosition[3]));
        System.out.print("  p queen "+Long.toHexString(this.Player.PiecesPosition[4]));
        System.out.println("  p king "+Long.toHexString(this.Player.PiecesPosition[5]));
        
        System.out.println("computer");
        System.out.print("c pawn "+Long.toHexString(this.Computer.PiecesPosition[0]));
        System.out.print("  c rook "+Long.toHexString(this.Computer.PiecesPosition[1]));
        System.out.print("  c knight "+Long.toHexString(this.Computer.PiecesPosition[2]));
        System.out.print("  c bishop "+Long.toHexString(this.Computer.PiecesPosition[3]));
        System.out.print("  c queen "+Long.toHexString(this.Computer.PiecesPosition[4]));
        System.out.println("  c king "+Long.toHexString(this.Computer.PiecesPosition[5]));
        
        System.out.println();
        
    }


}

