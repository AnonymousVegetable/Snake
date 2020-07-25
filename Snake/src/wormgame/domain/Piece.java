/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wormgame.domain;

/**
 *
 * @author Michael
 */
public class Piece {
    private int xPos;
    private int yPos;
    
    public Piece(int x, int y) {
        this.xPos = x;
        this.yPos = y;
    }
    
    public int getX() {
        return this.xPos;
    }
    
    public int getY() {
        return this.yPos;
    }
    
    public boolean runsInto(Piece piece) {
        if(this.xPos == piece.getX() && this.yPos == piece.getY()) {
            return true;
        }
        else {
            return false;
        }
    }
    
    public String toString() {
        return "(" + this.xPos + "," + this.yPos + ")";
    }
}
