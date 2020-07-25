package wormgame.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import wormgame.game.WormGame;
import java.io.File;
import java.util.Scanner;
import java.io.FileWriter;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;

public class UserInterface implements Runnable {

    private JFrame frame;
    private WormGame game;
    private int sideLength;
    private DrawingBoard board;
    private File file;
    private int score;
    private String highScore;

    public UserInterface(WormGame game, int sideLength) {
        this.game = game;
        this.sideLength = sideLength;
        this.file = new File("scores.txt");
    }

    @Override
    public void run() {
        String highScore = "0";
        try {
            Scanner reader = new Scanner(this.file);
            highScore = reader.nextLine();
            System.out.println(reader.nextLine());
        } catch (Exception e) {
            
        }
        this.highScore = highScore;
        
        frame = new JFrame("Worm Game - Highscore: " + highScore);
        frame.setBackground(Color.red);
        int width = ((game.getWidth() + 1) * sideLength) + 16;
        int height = ((game.getHeight() + 1) * sideLength) + 39;

        
        frame.setPreferredSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        
        createComponents(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);
        
    }

    public void createComponents(Container container) {
        // Create drawing board first which then is added into container-object.
        // After this, create keyboard listener which is added into frame-object
        this.board = new DrawingBoard(this.game, sideLength);
        container.add(this.board);
        
        KeyboardListener listener = new KeyboardListener(this.game.getWorm());
        listener.setGame(this.game);
        this.frame.addKeyListener(listener);
        
    }
    
    public Updatable getUpdatable() {
        return this.board;
    }

    public int getSide() {
        return this.sideLength;
    }
    
    public String getHighScore() {
        return this.highScore;
    }
    
    public JFrame getFrame() {
        return frame;
    }
}
