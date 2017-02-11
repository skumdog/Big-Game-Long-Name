package cemeteryfuntimes;

import java.awt.EventQueue;
import javax.swing.JFrame;
import cemeteryfuntimes.Resources.Shared.*;
import java.awt.Dimension;

// @author David Kozloff & Tyler Law
public class Main extends JFrame implements Globals {
    
    public Main() {
        Screen game = new Screen();
        add(game);
        setTitle("Cemetery Fun Times");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        game.setPreferredSize(new Dimension(SCREENWIDTH,SCREENHEIGHT));
        pack();
        game.setFocusable(true);
        game.requestFocus();
        game.setVisible(true);
    }

    public static void main(String[] args) {
        // TODO code application logic here
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
    
}
