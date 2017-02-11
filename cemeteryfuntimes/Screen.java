package cemeteryfuntimes;

import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import cemeteryfuntimes.Resources.Shared.*;
import cemeteryfuntimes.Code.*;
import java.awt.Color;
import java.util.Timer;

// @author David Kozloff & Tyler Law
public class Screen extends JPanel implements Globals {
    
    private Game game;
    private java.util.Timer gameUpdater; 
    private boolean gameRunning;
    
    
    public Screen() {
        addKeyListener(new MyKeyListener());
        game = new Game();
        gameRunning = true;
        gameUpdater = new Timer();
        gameUpdater.schedule(new GameUpdater(), 0, TIMERDELAY);
    }
    
    private class GameUpdater extends java.util.TimerTask
    {
        public void run() //this becomes the loop
        {     
            game.update();
            repaint();

            if (!gameRunning)
            {
                gameUpdater.cancel();
            }
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawRect(GAMEBORDER, 0, GAMEHEIGHT, GAMEWIDTH);
        game.draw(g);
    }
    
    private class MyKeyListener extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            game.keyPressed(e.getKeyCode());
        }

        public void keyReleased(KeyEvent e) {
            game.keyReleased(e.getKeyCode());
        }
    }
}
