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
import wormgame.Direction;
import java.util.List;
import java.util.ArrayList;
import wormgame.game.WormGame;

public class Worm {
    
    private int originalX;
    private int originalY;
    private Direction originalDirection;
    private Direction direction;
    private List<Piece> pieces;
    private boolean growth;
    private WormGame game;
    
    public Worm(int originalX, int originalY, Direction originalDirection) {
        this.originalX = originalX;
        this.originalY = originalY;
        this.direction = originalDirection;
        this.originalDirection = originalDirection;
        this.pieces = new ArrayList<Piece>();
        this.pieces.add(new Piece(originalX, originalY));
        this.growth = false;        
    }
    
    public void wormReset() {
        this.direction = this.originalDirection;
        this.pieces.clear();
        this.pieces.add(new Piece(originalX, originalY));
        this.growth = false;   
    }
    
    public void setGame(WormGame game) {
        this.game = game;
    }
    
    public Direction getDirection() {
        return this.direction;
    }
    
    public void setDirection(Direction dir) {
        this.direction = dir;
    }
    
    public int getLength() {
        return this.pieces.size();
    }
    
    public List<Piece> getPieces() {
        return this.pieces;
    }
    
    public Piece getHead() {
        return this.pieces.get(this.getLength()-1);
    }
    
    public void move() {
        int newX = this.pieces.get(this.pieces.size() - 1).getX();
        int newY = this.pieces.get(this.pieces.size() - 1).getY();
        
        if (direction == Direction.UP) {
            newY--;
        } else if (direction == Direction.DOWN) {
            newY++;
        } else if (direction == Direction.LEFT) {
            newX--;
        } else if (direction == Direction.RIGHT) {
            newX++;
        }

        if (getLength() > 2 && !this.growth) {
            this.pieces.remove(0);
        }

        if (this.growth = true) {
            this.growth = false;
        }
        
        if(newX < 0) {
            newX = this.game.getWidth();
        }
        
        if(newX > this.game.getWidth()) {
            newX = 0;
        }
        
        if(newY < 0) {
            newY = this.game.getHeight();
        }
        
        if(newY > this.game.getHeight()) {
            newY = 0;
        }
            
        this.pieces.add(new Piece(newX, newY));
    }
    
    public void grow() {
        this.growth = true;
    }
    
    public boolean runsInto(Piece piece) {
        if(piece == null) {
            return false;
        }
        else {
            for (int i = 0; i < getLength(); i++) {
                if (this.pieces.get(i).getX() == piece.getX() && this.pieces.get(i).getY() == piece.getY()) {
                    return true;
                }
            }

            return false;
        }
    }
    
    public boolean runsIntoItself() {
        for (int i = 0; i < getLength() - 1; i++) {
            if (this.getHead().getX() == this.pieces.get(i).getX() && this.getHead().getY() == this.pieces.get(i).getY()) {
                return true;
            }
        }

        return false;
    }
}
