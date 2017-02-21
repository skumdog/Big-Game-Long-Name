package cemeteryfuntimes;

import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.event.KeyEvent;

import cemeteryfuntimes.Resources.Shared.*;
import cemeteryfuntimes.Code.*;
import java.awt.Dimension;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
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
    
    private BufferedImage backgroundImage;
    private BufferedImage gameBackgroundImage;
    
    private enum Action {
        //This enum is for storing all keyboard events that map to specific game actions
        MOVEUP(KeyEvent.VK_W,MOVEMENT),MOVEDOWN(KeyEvent.VK_S,MOVEMENT),MOVELEFT(KeyEvent.VK_A,MOVEMENT),MOVERIGHT(KeyEvent.VK_D,MOVEMENT),
        SHOOTUP(KeyEvent.VK_UP,SHOOT),SHOOTDOWN(KeyEvent.VK_DOWN,SHOOT),SHOOTLEFT(KeyEvent.VK_LEFT,SHOOT),SHOOTRIGHT(KeyEvent.VK_RIGHT,SHOOT);
        
        private final int keyCode;
        private final int actionType;
        
        private Action(int keyCode, int actionType) {
            this.keyCode = keyCode;
            this.actionType=actionType;
        }
        
        public int getKeyCode() {
            return keyCode;
        }
        
        public int getActionType() {
            return actionType;
        }
    }
    
    public Screen() {
        setKeyBindings();
        setupImages();
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
        g.drawImage(backgroundImage, 0, 0, null);
        g.drawImage(gameBackgroundImage, GAMEBORDER, 0, null);
        /*g.setColor(Color.WHITE);
        g.fillRect(GAMEBORDER, 0, GAMEHEIGHT, GAMEWIDTH);
        g.setColor(Color.BLACK);
        g.drawRect(GAMEBORDER, 0, GAMEHEIGHT, GAMEWIDTH);*/
        game.draw(g);
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(SCREENWIDTH,SCREENHEIGHT);
    }
    
    private void setKeyBindings() {
        //Sets up basic keybindings for game
        //Maps WASD and arrow keys to their respective actions
        InputMap inMap = getInputMap(JComponent.WHEN_FOCUSED);
        ActionMap actMap = getActionMap();
        int keyCode;
        int actionType;
        for (final Action gameAction : Action.values()) {
            keyCode = gameAction.getKeyCode();
            actionType = gameAction.getActionType();
            KeyStroke pressed = KeyStroke.getKeyStroke(keyCode, 0, false);
            KeyStroke released = KeyStroke.getKeyStroke(keyCode, 0, true);
            inMap.put(pressed,keyCode+"pressed");
            inMap.put(released,keyCode+"released");
            actMap.put(keyCode+"pressed", new GameAction(keyCode,actionType,true));
            actMap.put(keyCode+"released",new GameAction(keyCode,actionType,false));
        }
    }
    
    private class GameAction extends AbstractAction {
        //This class is to perform actions when a key is pressed
        int keyCode; //Unique integer mapped to a key see KeyEvent.VK_BLANK
        boolean isPressed; //True if this action is for key pressed false if for key released
        int actionType;
        
        GameAction(int keyCode, int actionType, boolean isPressed) {
            this.keyCode=keyCode;
            this.isPressed=isPressed;
            this.actionType=actionType;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (actionType) {
                case MOVEMENT:
                    game.movementAction(keyCode, isPressed);
                    break;
                case SHOOT:
                    game.shootAction(keyCode, isPressed);
                    break;
            }
        }
    }
    
    private void setupImages() {
        backgroundImage = cemeteryfuntimes.Resources.Shared.Other.getScaledInstance(IMAGEPATH+"General/background.jpg",SCREENWIDTH,SCREENHEIGHT,RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR,false);
        gameBackgroundImage = cemeteryfuntimes.Resources.Shared.Other.getScaledInstance(IMAGEPATH+"General/gameBackground.jpg",GAMEWIDTH,GAMEHEIGHT,RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR,false);
    }
    
}
