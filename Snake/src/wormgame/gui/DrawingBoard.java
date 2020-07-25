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

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;
import wormgame.game.WormGame;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.Timer;
import wormgame.Direction;
import wormgame.gui.Updatable;
import wormgame.domain.Worm;
import wormgame.domain.Piece;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.Scanner;
import javax.imageio.*;
import javax.swing.*;
import wormgame.domain.Apple;

public class DrawingBoard extends JPanel implements Updatable {
    private WormGame game;
    private int pieceLength;
    private File file;
    
    public DrawingBoard(WormGame game, int pieceLength) {
        this.game = game;
        this.pieceLength = pieceLength;
        this.file = new File("scores.txt");
    }
    
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        
        BufferedImage appleImg = null;
        try {
            appleImg = ImageIO.read(new File("Apple.png"));
        } catch (IOException e) {
        }
        BufferedImage score10Img = null;
        try {
            score10Img = ImageIO.read(new File("10Score.png"));
        } catch (IOException e) {
        }
        BufferedImage multiplierImg = null;
        try {
            multiplierImg = ImageIO.read(new File("Multiplier.png"));
        } catch (IOException e) {
        }
        BufferedImage wormPiece = null;
        try {
            wormPiece = ImageIO.read(new File("wormPiece.png"));
        } catch (IOException e) {
        }
        BufferedImage background = null;
        try {
            background = ImageIO.read(new File("Background2.png"));
        } catch (IOException e) {
        }
        BufferedImage score = null;
        try {
            score = ImageIO.read(new File("Scoreback.png"));
        } catch (IOException e) {
        }
        BufferedImage mudball = null;
        try {
            mudball = ImageIO.read(new File("Mudball.png"));
        } catch (IOException e) {
        }
        
        graphics.setColor(Color.BLACK);
        graphics.drawImage(background, 0, 0, this);
        graphics.drawImage(score, 14, 0, this);
        
        
        
        graphics.setColor(Color.ORANGE);
        graphics.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 16));
        graphics.drawString("Score: " + this.game.getScore(), 36, 20);
        
        String highScore = "0";
        try {
            Scanner reader = new Scanner(this.file);
            highScore = reader.nextLine();
            System.out.println(reader.nextLine());
        } catch (Exception e) {
            
        }       
        graphics.setColor(new Color(64,47,2));
        graphics.setFont(new Font("Engravers MT", Font.PLAIN, 10));
        graphics.drawString("| [r] to restart|", 29, 34);
        graphics.setFont(new Font("Engravers MT", Font.BOLD, 16));
        graphics.drawString("|||" + highScore + "|||", 156, 34);
        
        for(Apple i : this.game.getApples()) {
            graphics.drawImage(appleImg, pieceLength*i.getX(), pieceLength*i.getY(), this);
        ///graphics.fillOval(pieceLength*game.getApple().getX(), pieceLength*game.getApple().getY(), this.pieceLength, this.pieceLength);
        }
        
        if(this.game.get10() != null) {
            graphics.drawImage(score10Img, pieceLength*game.get10().getX(), pieceLength*game.get10().getY(), this);

            
        }
        
        if(this.game.getMultiplier() != null) {
            graphics.drawImage(multiplierImg, pieceLength*game.getMultiplier().getX(), pieceLength*game.getMultiplier().getY(), this);

            
        }
        
        if(this.game.getMudball() != null) {
            graphics.drawImage(mudball, pieceLength*game.getMudball().getX(), pieceLength*game.getMudball().getY(), this);

            
        }
        
        graphics.setColor(new Color(255, 115, 0));
        graphics.setFont(new Font("Verdana", Font.BOLD, 18));
        if(this.game.getMultiplierTime() > 0) {
            graphics.drawString("2x: " + Integer.toString(this.game.getMultiplierTime()), 536, 592);
        }
        
        for(Piece i : this.game.getWorm().getPieces()) {
            graphics.drawImage(wormPiece, pieceLength*i.getX(), pieceLength*i.getY(), this);
            ///graphics.fill3DRect(pieceLength*i.getX(), pieceLength*i.getY(), this.pieceLength, this.pieceLength, true);
        }
    }

    @Override
    public void update() {
        super.repaint();

    }
}
