package app.chess;

import app.listeners.ChessBoardMouseListener;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ChessBoard extends JPanel {

    public final int BoardSize = 8;
    public final int CellSize = 75;
    public final Game game;
    public final ChessBoardMouseListener ml;
    public long ColoredSquares = 0x0L;
    public long ColoredPieces = 0x0L;


    public ChessBoard() {
        this.game = new Game();
        this.ml = new ChessBoardMouseListener(this);


        this.setBorder(BorderFactory.createLineBorder(new Color(182, 155, 76), 3, true));
        this.setPreferredSize(new Dimension(600, 600));
        this.addMouseListener(ml);


    }

    public void addColoredSquare(long bit) {
        this.ColoredSquares |= bit;
    }

    public void addColoredPiece(long bit) {
        this.ColoredPieces |= bit;
    }

    public void clearColoredPieces() {
        this.ColoredPieces = 0x0L;
    }

    public void clearColoredSquares() {
        this.ColoredSquares = 0x0L;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < BoardSize; i++) {
            for (int j = 0; j < BoardSize; j++) {
                if ((i + j) % 2 == 0) {
                    g.setColor(new Color(255, 230, 170));
                } else {
                    g.setColor(new Color(219, 176, 100));
                }
                g.fillRect(j * CellSize, i * CellSize, CellSize, CellSize);
                g.setColor(Color.BLACK);
                g.drawRect(j * CellSize, i * CellSize, CellSize, CellSize);

            }
        }

        int i;
        int j;
        long lowest;
        long TempColoredPieces = this.ColoredPieces;

        while (TempColoredPieces != 0x0L) {
            lowest = Long.lowestOneBit(TempColoredPieces);
            i = Long.numberOfTrailingZeros(TempColoredPieces) / 8;
            j = Long.numberOfTrailingZeros(TempColoredPieces) % 8;

            g.setColor(new Color(214, 219, 207, 200));
            g.fillRect(j * CellSize, (7 - i) * CellSize, CellSize, CellSize);
            g.setColor(Color.BLACK);
            g.drawRect(j * CellSize, (7 - i) * CellSize, CellSize, CellSize);

            TempColoredPieces ^= lowest;
        }
        long TempColoredSquares = this.ColoredSquares;
        while (TempColoredSquares != 0x0L) {
            lowest = Long.lowestOneBit(TempColoredSquares);
            i = Long.numberOfTrailingZeros(TempColoredSquares) / 8;
            j = Long.numberOfTrailingZeros(TempColoredSquares) % 8;

            g.setColor(new Color(13, 253, 106, 250));
            g.fillRect(j * CellSize, (7 - i) * CellSize, CellSize, CellSize);
            g.setColor(Color.BLACK);
            g.drawRect(j * CellSize, (7 - i) * CellSize, CellSize, CellSize);

            TempColoredSquares ^= lowest;
        }

        this.paintLastMove(g);

        for (i = 0; i < 6; i++) {
            long Pieces = game.Computer.PiecesPosition[i];
            while (Pieces != 0x0L) {
                DrawImage(g, this.game, false, Long.numberOfTrailingZeros(Pieces), i);
                Pieces ^= Long.lowestOneBit(Pieces);
            }

            Pieces = game.Player.PiecesPosition[i];
            while (Pieces != 0x0L) {
                DrawImage(g, this.game, true, Long.numberOfTrailingZeros(Pieces), i);
                Pieces ^= Long.lowestOneBit(Pieces);

            }
        }


    }

    public void paintLastMove(Graphics g) {
        if (!game.undoMoves.isEmpty()) {
            int i = Long.numberOfTrailingZeros(game.undoMoves.lastElement().Origin) / 8;
            int j = Long.numberOfTrailingZeros(game.undoMoves.lastElement().Origin) % 8;
            drawLastMove(g, i, j);

            i = Long.numberOfTrailingZeros(game.undoMoves.lastElement().destination) / 8;
            j = Long.numberOfTrailingZeros(game.undoMoves.lastElement().destination) % 8;
            drawLastMove(g, i, j);

        }
    }

    private void drawLastMove(Graphics g, int i, int j) {
        g.setColor(new Color(142, 213, 253, 150));
        g.fillRect(j * CellSize, (7 - i) * CellSize, CellSize, CellSize);
        g.setColor(Color.BLACK);
        g.drawRect(j * CellSize, (7 - i) * CellSize, CellSize, CellSize);
    }

    public void DrawImage(Graphics g, Game game, boolean Player, int bitNum, int PieceSerial) {
        char Color;
        String Piece;
        switch (PieceSerial) {
            case 0:
                Piece = "Pawn";
                break;
            case 1:
                Piece = "Rook";
                break;
            case 2:
                Piece = "Knight";
                break;
            case 3:
                Piece = "Bishop";
                break;
            case 4:
                Piece = "Queen";
                break;
            case 5:
                Piece = "King";
                break;
            default:
                Piece = null;
                break;
        }

        Color = Player ? game.Player.Color : game.Computer.Color;
        ImageIcon piece = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/" + Color + Piece + ".png")));
        g.drawImage(piece.getImage(), (bitNum % BoardSize) * CellSize, ((7 - (bitNum / BoardSize)) * CellSize), CellSize, CellSize, this);
    }


}