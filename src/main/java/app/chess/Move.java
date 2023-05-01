package app.chess;


public class Move {

    public final long Origin;
    public final long destination;
    public final int Serial;
    public final int killId;
    public final int Special; /// -1 not special, 0 means castling... and so on

    public Move(long Origin, long destination, int Serial, int killId, int Special) {
        this.Origin = Origin;
        this.Serial = Serial;
        this.destination = destination;
        this.killId = killId;
        this.Special = Special;
    }

    public void printMove() {
        System.out.println();
        System.out.println("Origin " + Long.toHexString(this.Origin) + " Serial " + this.Serial + " destination " + Long.toHexString(this.destination) + " Special " + this.Special + " Kill id " + this.killId);
        System.out.println();
    }

}
