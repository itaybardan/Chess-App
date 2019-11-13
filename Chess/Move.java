/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chess;


/**
 *
 * @author itayb
 */
public class Move {

    public long Origin;
    public long destination;
    public int Serial;
    public int killId;
    public int Special; /// -1 not special, 0 means castling... and so on
    
    public Move(long Origin,long destination, int Serial, int killId, int Special)
    {
        this.Origin=Origin;
        this.Serial=Serial;
        this.destination=destination;
        this.killId=killId;
        this.Special=Special;
    }

    public  void printMove()
    {
        System.out.println();
        System.out.println("Origin "+Long.toHexString(this.Origin)+" Serial "+this.Serial+" destination "+Long.toHexString(this.destination)+" Specail "+this.Special+" Kill id "+this.killId);
        System.out.println();
    }
  
}
