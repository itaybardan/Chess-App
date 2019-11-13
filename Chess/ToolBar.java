/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chess;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JToolBar;

public class ToolBar extends JToolBar {   
    
    private JComboBox color;
    private JComboBox promotion;
    private JComboBox level;
    private JButton Undo=new JButton("Undo");
    private JButton newGame=new JButton("New Game");
    public  JTextField situation=new JTextField("your turn");
    
    public ToolBar()
    {   
        this.setBorderPainted(true);
        this.setFloatable(false);
        this.add(this.newGame);
        JTextField tf=new JTextField("   Your Color : White");
        tf.setEditable(false);
        this.add(tf);
        //String[] Color={"White","Black"};
        //color=new JComboBox(Color);
        //this.add(color);
        this.add(this.Undo);
        JTextField tf1=new JTextField("  Promotion to : ");
        tf1.setEditable(false);
        this.add(tf1);
        //this.add(this.situation);
        String[] PieceList={"Queen","Rook","Knight","Bishop","Pawn"};
        promotion=new JComboBox(PieceList);
        this.add(promotion);
        JTextField tf2=new JTextField("  Level : ");
        tf2.setEditable(false);
        this.add(tf2);
        String[] Difficulities={"Beginner","Casual","Advanced"};
        level=new JComboBox(Difficulities);
        this.add(level);
       
    }

    public JButton getUndoButton()
    {
        return this.Undo;
    }
    public String getPlayerPrefferedColor()
    {
        return (String)color.getSelectedItem();
    }
     public JButton getNewGameButton()
    {
        return this.newGame;
    }
    public void setActionListeners(ChessBoard cb)
    {
        this.newGame.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
              cb.game.newGame();
              cb.ml.resetClick();
              cb.repaint();
            }
        });
        this.Undo.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if (!cb.game.undoMoves.empty())
                {
                  cb.game.undoMove();
                  if(cb.ml.search!=null && cb.ml.search.isAlive()) cb.ml.search.stop();
                  else if (!cb.game.undoMoves.empty()) cb.game.undoMove();
                      cb.ml.resetClick();
                 cb.repaint();
                }
                else
                {
                    cb.ml.resetClick();
                    cb.repaint();
                }
            }
        });
        this.level.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){


                switch((String)level.getSelectedItem()){
                    case "Beginner":
                        cb.game.depth=4;
                        break;
                    case "Casual":
                        cb.game.depth=5;
                        break;
                    case "Advanced":
                        cb.game.depth=6;
                        break;    
                }
                
                if(cb.ml.search!=null && cb.ml.search.isAlive()){
                    cb.ml.search.stop();
                    cb.ml.search=new NegaMax(cb);
                    cb.ml.search.start();
                }
                
                cb.ml.resetClick();
                cb.repaint();
              
            }
        });
        this.promotion.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){


                switch((String)promotion.getSelectedItem()){
                    case "Queen":
                        cb.game.pieceToPromotion=4;
                        break;
                    case "Rook":
                        cb.game.pieceToPromotion=1;
                        System.out.println("hey");
                        break;
                    case "Bishop":
                        cb.game.pieceToPromotion=3;
                        break;
                    case "Knight":
                        cb.game.pieceToPromotion=2;
                        break;
                    case "Pawn":
                        cb.game.pieceToPromotion=0;
                        break;     
                }
                
                if(cb.ml.search!=null && cb.ml.search.isAlive()){
                    cb.ml.search.stop();
                    cb.ml.search=new NegaMax(cb);
                    cb.ml.search.start();
                }
                
                cb.ml.resetClick();
                cb.repaint();
                
                
            }
        });
        
       
    }

}

