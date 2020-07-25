/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wormgame.gui;

/**
 *
 * @author Michael
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.Timer;
import wormgame.Direction;
import wormgame.gui.Updatable;
import wormgame.domain.Worm;
import wormgame.domain.Apple;
import java.util.List;
import java.util.ArrayList;
import java.awt.event.KeyListener;
import wormgame.game.WormGame;

public class KeyboardListener implements KeyListener {
    private Worm worm;
    private WormGame game;
    
    public KeyboardListener(Worm worm) {
        this.worm = worm;
    }

    public void setGame(WormGame game) {
        this.game = game;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP && !this.worm.getDirection().equals(Direction.DOWN) && this.game.getDirChange()) {
            this.worm.setDirection(Direction.UP);
            this.game.setDirChange();
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN && !this.worm.getDirection().equals(Direction.UP) && this.game.getDirChange()) {
            this.worm.setDirection(Direction.DOWN);
            this.game.setDirChange();
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT && !this.worm.getDirection().equals(Direction.RIGHT) && this.game.getDirChange()) {
            this.worm.setDirection(Direction.LEFT);
            this.game.setDirChange();
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT && !this.worm.getDirection().equals(Direction.LEFT) && this.game.getDirChange()) {
            this.worm.setDirection(Direction.RIGHT);
            this.game.setDirChange();
        }
        if(e.getKeyChar() == 'r' ) {
            this.game.restartGame();           
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
    
}
