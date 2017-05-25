package cemeteryfuntimes;
import cemeteryfuntimes.Code.Shared.Globals;
import java.awt.EventQueue;
import javax.swing.JFrame;
/**
* Cemetery Fun Times (Working Title)
* Top-Down Shooter Game
* 
* @author David Kozloff & Tyler Law
* @since  2017-02-05
*/
public class Main extends JFrame implements Globals {
    /**
    * Main class constructor sets up the Screen object.
    */
    public Main() {
        Screen game = new Screen();
        add(game);
        setTitle("Cemetery Fun Times");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        pack();
        game.setFocusable(true);
        game.requestFocus();
        game.setVisible(true);
    }
    /**
    * Begins program execution and handles main application logic.
    * @param args Unused.
    */
    public static void main(String[] args) {
        // TODO code application logic here
        EventQueue.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }
}
