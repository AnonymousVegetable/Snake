package wormgame.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.Timer;
import wormgame.Direction;
import wormgame.gui.Updatable;
import wormgame.domain.Worm;
import wormgame.domain.Apple;
import wormgame.domain.Piece;
import java.util.List;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.File;
import java.nio.file.Files;
import java.util.Scanner;
import java.io.PrintWriter;
import wormgame.domain.Score10;
import wormgame.domain.Multiplier;
import wormgame.domain.Mudball;
import java.util.ArrayList;

public class WormGame extends Timer implements ActionListener {

    private int width;
    private int height;
    private boolean continues;
    private Updatable updatable;
    private Worm worm;
    private List<Apple> apples;
    private int appleSpawnCounter;
    private Score10 score10;
    private Multiplier multiplier;
    private Mudball mudball;
    private Random rand;
    private int score;
    private File file;
    private int pieceCounter;
    private int score10Timer;
    private int multiplierTimer;
    private int multiplierActiveTimer;
    private boolean activeMultiplier;
    private boolean canDirChange;
    private int mudCollected;
    private int mudSpawnTimer;
    private boolean canMove;
    

    public WormGame(int width, int height) {       
        
        super(1000, null);
        this.file = new File("scores.txt");
        this.width = width;
        this.height = height;
        this.continues = true;
        this.pieceCounter = 1;
        this.canDirChange = true;
        this.score = 0;
        this.activeMultiplier = false;
        this.apples = new ArrayList<Apple>();
        this.appleSpawnCounter = 6;
        this.canMove = true;

        addActionListener(this);
        setInitialDelay(2000);
        
        this.worm = new Worm(this.width/2, this.height/2, Direction.DOWN);
        
        
        this.rand = new Random();
    }

    public int getScore() {
        return this.score;
        
    }
    
    public boolean continues() {
        return continues;
    }

    public void setUpdatable(Updatable updatable) {
        this.updatable = updatable;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
    
    public Worm getWorm() {
        return this.worm;
    }
    
    public void setWorm(Worm worm) {
        this.worm = worm;
    }
    
    public List<Apple> getApples() {
        return this.apples;
    }
    
    public Score10 get10() {
        return this.score10;
    }
    
    public Mudball getMudball() {
        return this.mudball;
    }
    
    public Multiplier getMultiplier() {
        return this.multiplier;
    }
    
    public int getMultiplierTime() {
        return this.multiplierActiveTimer/18;
    }
    
    public boolean getDirChange() {
        return this.canDirChange;
    }
    
    public void setDirChange() {
        this.canDirChange = false;
    }
    
    public void addApple(Apple apple) {
        this.apples.add(apple);
    }
    
    public void setScore() throws Exception {
        try {
            Scanner reader = new Scanner(this.file);
            int highScore = Integer.parseInt(reader.nextLine());

            if(this.score > highScore) {
                this.file.delete();
                PrintWriter writer = new PrintWriter("scores.txt", "UTF-8");
                writer.println(Integer.toString(this.score));
                writer.close();

            }
        } catch (Exception e) {
            
        }
        
        
    }
    
    /// RESTART GAME ///
    public void restartGame() {  
        try {
                this.setScore();
           } catch(Exception e) {
           }
        this.worm.wormReset();  
        this.apples.clear();
        this.appleSpawnCounter = 5;
        this.score = 0;
        this.continues = true;
        this.pieceCounter = 1;
        this.canDirChange = true;
        this.activeMultiplier = false;
        this.mudCollected = 0;
        this.canMove = false;
    }
                
    
    public void endGame() {
        this.continues = false;
    }
    
    public void startGame() {
        this.continues = true;
    }
    
    public boolean getContinues() {
        return this.continues;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (this.continues == false) {
            return;
        }
        
        if(this.canMove == true) {
            this.worm.move();
        }
        this.canMove = true;
        
        this.canDirChange = true;
        
        
        /// APPLE SPAWN
        this.appleSpawnCounter++;
        if(this.appleSpawnCounter%8 == 0) {
            if(this.apples.size() < 3) {
                Apple newApple = new Apple(rand.nextInt(this.width), rand.nextInt(this.height));
                while (worm.runsInto(newApple)) {
                    newApple = new Apple(rand.nextInt(this.width), rand.nextInt(this.height));
                }
                this.apples.add(newApple);
            }
        }
        
        
        /// SCORE10 SPAWN
        if(this.pieceCounter%10==0) {
            pieceCounter++;
            this.score10 = new Score10(new Random().nextInt(this.width), new Random().nextInt(this.height)); 
            while (worm.runsInto(score10)) {
                this.score10 = new Score10(new Random().nextInt(this.width), new Random().nextInt(this.height));
            }
            this.score10Timer = 80;
        }
        
        
        /// MULTIPLIER SPAWN
        if(this.pieceCounter%22==0) {
            pieceCounter++;
            this.multiplier = new Multiplier(new Random().nextInt(this.width), new Random().nextInt(this.height)); 
            while (worm.runsInto(multiplier)) {
                this.multiplier = new Multiplier(new Random().nextInt(this.width), new Random().nextInt(this.height));
            }
            this.multiplierTimer = 80;
        }
        
        /// MUDBALL SPAWN
        if(this.pieceCounter%12==0) {
            this.pieceCounter++;
            this.mudball = new Mudball(new Random().nextInt(this.width), new Random().nextInt(this.height)); 
            while (worm.runsInto(this.mudball)) {
                this.mudball = new Mudball(new Random().nextInt(this.width), new Random().nextInt(this.height));
            }          
            this.mudSpawnTimer = 160;
        }

        
        /// APPLE SCORE
        List<Apple> removableApples = new ArrayList<Apple>();
        for(Apple i : this.apples) {
            if (worm.runsInto(i)) {
                this.pieceCounter++;
                worm.grow();
                
                removableApples.add(i);

                if(this.activeMultiplier) {
                    this.score+=4;
                }
                else {
                    this.score+=2;
                }


                updatable.update();
            }
        }
        for(Apple i : removableApples) {
            this.apples.remove(i);
        }
        
        
        if (this.worm.runsIntoItself()) {
            continues = false;
            try {
                this.setScore();
            } catch(Exception e) {
              }
        }   
        
        
        /// 10 SCORE EFFECT    
        if(this.score10Timer == 0) {
            this.score10 = null;
            
        } 
        if (worm.runsInto(this.score10)) {
            this.score10 = null;
            
            
            if(this.activeMultiplier) {
                this.score+=20;
            }
            else {
                this.score+=10;
            }
        }
        /// MULTIPLIER EFFECT
        if (worm.runsInto(this.multiplier)) {
            this.multiplier = null;
            this.activeMultiplier = true;
            this.multiplierActiveTimer = 180;
        }
        if (this.multiplierTimer == 0) {
            this.multiplier = null;
        }
        if (this.multiplierActiveTimer == 0) {
            this.activeMultiplier = false;
        }
        
        /// MUDBALL EFFECT
        if (worm.runsInto(this.mudball)) {
            this.mudball = null;
            this.mudCollected++;
        }
        if (this.mudSpawnTimer == 0) {
            this.mudball = null;
        }
      
        
        
        
        
            
        
        
        
        /// DEATH AT BOUNDARY - OFF
        ///} else if (worm.getHead().getX() == this.width || worm.getHead().getX() < 0) {
        ///    continues = false;
        ///    try {
        ///        this.setScore();
        ///    } catch(Exception e) {
        ///    }
        ///} else if (worm.getHead().getY() == this.height || worm.getHead().getY() < 0) {
        ///    continues = false;
        ///    try {
        ///        this.setScore();
        ///    } catch(Exception e) {
        ///    }
        
        
        /// SPAWN TIMERS
        if(this.score10 != null) {
            this.score10Timer--;
        }
        if(this.multiplier != null) {
            this.multiplierTimer --;
        }
        if(this.activeMultiplier) {
            this.multiplierActiveTimer--;
        } 
        if(this.mudball != null) {
            this.mudSpawnTimer--;
        }
        
        
        
        updatable.update();
        int delay = 1000 / (this.worm.getLength()- 6*this.mudCollected);
        if(delay < 1000/(14)) {
            delay = 1000/14;
        }
        setDelay(delay);
    }

}
