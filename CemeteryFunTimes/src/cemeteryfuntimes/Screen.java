package cemeteryfuntimes;

import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.event.KeyEvent;

import cemeteryfuntimes.Resources.Shared.*;
import cemeteryfuntimes.Code.*;
import java.awt.event.ActionEvent;
import java.util.Timer;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

// @author David Kozloff & Tyler Law



public class Screen extends JPanel implements Globals {
    
    private final Game game;
    private final java.util.Timer gameUpdater; 
    private boolean gameRunning;
    
    private enum Action {
        //This enum is for storing all keyboard events that map to specific game actions
        MOVEUP(KeyEvent.VK_W),MOVEDOWN(KeyEvent.VK_S),MOVELEFT(KeyEvent.VK_A),MOVERIGHT(KeyEvent.VK_D),
        SHOOTUP(KeyEvent.VK_UP),SHOOTDOWN(KeyEvent.VK_DOWN),SHOOTLEFT(KeyEvent.VK_LEFT),SHOOTRIGHT(KeyEvent.VK_RIGHT);
        
        private final int keyCode;
        
        private Action(int keyCode) {
            this.keyCode = keyCode;
        }
        
        public int getKeyCode() {
            return keyCode;
        }
    }
    
    public Screen() {
        setKeyBindings();
        game = new Game();
        gameRunning = true;
        
        //Start updating game 
        gameUpdater = new Timer();
        gameUpdater.schedule(new GameUpdater(), 0, TIMERDELAY);
    }
    
    private class GameUpdater extends java.util.TimerTask
    {
        //This class is called by the gameUpdater
        //Periodically based on TIMERDELAY updates the game
        public void run() 
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
    
    private void setKeyBindings() {
        //Sets up basic keybindings for game
        //Maps WASD and arrow keys to their respective actions
        InputMap inMap = getInputMap(JComponent.WHEN_FOCUSED);
        ActionMap actMap = getActionMap();
        int keyCode;
        for (final Action gameAction : Action.values()) {
            keyCode = gameAction.getKeyCode();
            KeyStroke pressed = KeyStroke.getKeyStroke(keyCode, 0, false);
            KeyStroke released = KeyStroke.getKeyStroke(keyCode, 0, true);
            inMap.put(pressed,keyCode+"pressed");
            inMap.put(released,keyCode+"released");
            actMap.put(keyCode+"pressed", new GameAction(keyCode,true));
            actMap.put(keyCode+"released",new GameAction(keyCode,false));
        }
    }
    
    private class GameAction extends AbstractAction {
        //This class is to perform actions when a key is pressed
        int keyCode; //Unique integer mapped to a key see KeyEvent.VK_BLANK
        boolean pressed; //True if this action is for key pressed false if for key released
        
        GameAction(int keyCode, boolean pressed) {
            this.keyCode=keyCode;
            this.pressed=pressed;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            if (pressed) { game.keyPressed(keyCode); }
            else { game.keyReleased(keyCode); } 
        }
    }
    
}
