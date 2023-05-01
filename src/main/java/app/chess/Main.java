package app.chess;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        Runnable r = () -> {
            JFrame f = new JFrame("My app.Chess Game");
            JPanel p1 = new JPanel();
            ChessBoard cb = new ChessBoard();  // chess board is the top object of the game, he contains game,mouseListener;
            ToolBar tb = new ToolBar();
            tb.setActionListeners(cb);
            p1.add(tb);
            p1.add(cb);
            BoxLayout b1 = new BoxLayout(p1, BoxLayout.Y_AXIS);
            p1.setLayout(b1);
            f.add(p1);

            f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            f.setLocationByPlatform(false);

            // ensures the frame is the minimum size it needs to be
            // in order display the components within it
            f.setResizable(true);
            f.pack();

            // ensures the minimum size is enforced.
            f.setMinimumSize(f.getSize());
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        };

        SwingUtilities.invokeLater(r);
    }
}

