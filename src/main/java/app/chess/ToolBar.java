package app.chess;


import javax.swing.*;
import java.util.Objects;

public class ToolBar extends JToolBar {

    public JTextField situation = new JTextField("your turn");
    private JComboBox color;
    private final JComboBox promotion;
    private final JComboBox level;
    private final JButton Undo = new JButton("Undo");
    private final JButton newGame = new JButton("New Game");

    public ToolBar() {
        this.setBorderPainted(true);
        this.setFloatable(false);
        this.add(this.newGame);
        JTextField tf = new JTextField("   Your Color : White");
        tf.setEditable(false);
        this.add(tf);
        this.add(this.Undo);
        JTextField tf1 = new JTextField("  Promotion to : ");
        tf1.setEditable(false);
        this.add(tf1);
        String[] PieceList = {"Queen", "Rook", "Knight", "Bishop", "Pawn"};
        promotion = new JComboBox(PieceList);
        this.add(promotion);
        JTextField tf2 = new JTextField("  Level : ");
        tf2.setEditable(false);
        this.add(tf2);
        String[] Difficulties = {"Beginner", "Casual", "Advanced"};
        level = new JComboBox(Difficulties);
        this.add(level);

    }

    public JButton getUndoButton() {
        return this.Undo;
    }

    public String getPlayerPreferredColor() {
        return (String) color.getSelectedItem();
    }

    public JButton getNewGameButton() {
        return this.newGame;
    }

    public void setActionListeners(ChessBoard cb) {
        this.newGame.addActionListener(e -> {
            cb.game.newGame();
            cb.ml.resetClick();
            cb.repaint();
        });
        this.Undo.addActionListener(e -> {
            if (!cb.game.undoMoves.empty()) {
                cb.game.undoMove();
                if (cb.ml.search != null && cb.ml.search.isAlive()) cb.ml.search.stop();
                else if (!cb.game.undoMoves.empty()) cb.game.undoMove();
                cb.ml.resetClick();
                cb.repaint();
            } else {
                cb.ml.resetClick();
                cb.repaint();
            }
        });
        this.level.addActionListener(e -> {


            switch ((String) Objects.requireNonNull(level.getSelectedItem())) {
                case "Beginner":
                    cb.game.depth = 4;
                    break;
                case "Casual":
                    cb.game.depth = 5;
                    break;
                case "Advanced":
                    cb.game.depth = 6;
                    break;
            }

            isSearchAlive(cb);

        });
        this.promotion.addActionListener(e -> {


            switch ((String) Objects.requireNonNull(promotion.getSelectedItem())) {
                case "Queen":
                    cb.game.pieceToPromotion = 4;
                    break;
                case "Rook":
                    cb.game.pieceToPromotion = 1;
                    System.out.println("hey");
                    break;
                case "Bishop":
                    cb.game.pieceToPromotion = 3;
                    break;
                case "Knight":
                    cb.game.pieceToPromotion = 2;
                    break;
                case "Pawn":
                    cb.game.pieceToPromotion = 0;
                    break;
            }

            isSearchAlive(cb);


        });


    }

    private void isSearchAlive(ChessBoard cb) {
        if (cb.ml.search != null && cb.ml.search.isAlive()) {
            cb.ml.search.stop();
            cb.ml.search = new NegaMaxAlg(cb);
            cb.ml.search.start();
        }

        cb.ml.resetClick();
        cb.repaint();
    }

}

