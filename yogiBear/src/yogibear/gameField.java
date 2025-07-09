/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yogibear;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.sql.SQLException;

/**
 * 
 * @author vikto
 */
public class gameField extends JPanel implements KeyListener {
    private yogiBear yogi;
    private basket[] baskets = new basket[0];
    private int basketNumber;
    private tree[] trees = new tree[0];
    private mountain[] mountains = new mountain[0];
    private ranger[] rangers;
    private int HP;
    private int totalBaskets;
    
    private Image backgroundImage;
    
    private Timer repaintTimer;
    private Timer hpCheckTimer;
    private Timer timeTimer;
    private Timer rangerTimer;
    
    private int minutes;
    private int seconds;
    
    public gameField(){
        backgroundImage = new ImageIcon(getClass().getResource("/background.jpg")).getImage();
        
        initTable();
        this.HP = 3;
        this.totalBaskets = 0;
        this.minutes = 0;
        this.seconds = 0;
        
        setFocusable(true);
        addKeyListener(this);
        
        //timerek elindítása
        repaintTimer = new Timer(100, e -> repaint());
        repaintTimer.start();
        
        hpCheckTimer = new Timer(100, e -> checkHP());
        hpCheckTimer.start();
        
        timeTimer = new Timer(1000, e -> time());
        timeTimer.start();
        
        rangerTimer = new Timer(100, e -> checkRanger());
        rangerTimer.start();
    }
    /**
     * megjeleníti a leaderboardot
     * @return String formátumban a leaderboardon kijelzendő érték
     */
    public String getLeaderBoard(){
        try{
            int i = 1;
            HighScores highScores = new HighScores(10);
            String leaderBoardString = "";
            for(HighScore highScore : highScores.getHighScores()){
                leaderBoardString += i + ". " + highScore.getName() + " - " + highScore.getScore() + " db kosár" + "\n";
                i++;
            }
            return leaderBoardString;
        } catch(SQLException ex){

        }
        return "Error";
    }
    
    /**
     * inicializálja a táblát
     */
    private void initTable(){
        Random random = new Random();
        
        //yogi
        this.yogi = new yogiBear(this.HP);
        
        //trees
        this.trees = new tree[random.nextInt(5) + 1];
        for(int i = 0; i<trees.length; i++){
            trees[i] = new tree();
        }
        
        //mountains
        this.mountains = new mountain[5-trees.length];
        for(int i = 0; i<mountains.length; i++){
            mountains[i] = new mountain();
        }
        
        //baskets
        this.basketNumber = random.nextInt(10) + 1;
        basketNumber = 4;
        this.baskets = new basket[basketNumber];
        for(int i = 0; i<basketNumber; i++){
            baskets[i] = new basket();
            
            while(!placeable(baskets[i].getX(), baskets[i].getY(), i)){  
                baskets[i] = new basket();
            }
        }
        
        //rangers
        this.rangers = new ranger[5-random.nextInt(4)+1];
        for(int i = 0; i<rangers.length; i++){
            rangers[i] = new ranger();
        }
    }
    
    /**
     * megnézi, hogy egy kosár lehelyezhető-e egy adott helyre
     * @param x a hely x koordinátája ahova le szeretnénk helyezni
     * @param y a hely y koordinátája ahova le szeretnénk helyezni
     * @param ind a baskets tömbben a lehelyezendő kosár indexe
     * @return lehelyezhető-e
     */
    private boolean placeable(int x, int y, int ind){ //új lehelyezendő kosár adatai
        for(int i = 0; i<ind; i++){
            if(Math.abs(baskets[i].getX()-x)<30 && Math.abs(baskets[i].getY()-y)<30){
                return false;
            }
        }
        for(tree i : this.trees){
            if(Math.abs(i.getX()-x)<30 && Math.abs(i.getY()-y)<30){
                return false;
            }
        }
        for(mountain i : mountains){
            if(Math.abs(i.getX()-x)<30 && Math.abs(i.getY()-y)<30){
                return false;
            }
        }
        return true;
    }
    
    /**
     * megnézi, hogy egy ranger ütközik-e egy átjárhatatlan akadállyal
     * ha ütközik, akkor a ranger visszafordul az ellenkező irányba
     */
    private void checkRanger() {
        for(ranger r : rangers){
            for(tree t : trees){
                if(Math.abs(t.getX()-r.getX())<30 && Math.abs(t.getY()-r.getY())<30){
                    r.switchDirection();
                }
            }
            for(mountain m : mountains){
                if(Math.abs(m.getX()-r.getX())<30 && Math.abs(m.getY()-r.getY())<30){
                    r.switchDirection();
                }
            }
        }
    }
    
    /**
     * a játék kezdetétől eltelt időt számolja
     */
    private void time() {
        this.seconds++;
        if(seconds==60){
            seconds = 0;
            minutes++;
        }
    }
    
    /**
     * megnézi, hogy yogi ütközik-e bármelyik rangerrel
     * ha ütközik, akkor levon egy HP-t
     * megnézi, hogy a HP nagyobb-e mint 0
     * ha nem, akkor leállítja a játékot
     */
    private void checkHP(){
        for(ranger i : rangers){
            if(Math.abs(yogi.getX()-i.getX())<30 && Math.abs(yogi.getY()-i.getY())<30){
                yogi.minusHP();
                HP--;
                if(HP<=0){
                    repaintTimer.stop();
                    hpCheckTimer.stop();
                    timeTimer.stop();
                    rangerTimer.stop();
                    repaint();
                    String str = JOptionPane.showInputDialog("Mi a neved?");
                    if(str!=null){
                        try{
                            HighScores highScores = new HighScores(10);
                            highScores.putHighScore(str, totalBaskets);
                        } catch(SQLException ex){

                        }
                    }
                    //JOptionPane.showMessageDialog(null, "Úgy tűnik vesztettél.", "Oops...", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }
    
    /**
     * megnézi, hogy yogi tud-e lépni a megadott irányba
     * ha nem tud, akkor visszavonjuk a már korábban megtett lépést -> így kvázi nem lépett
     * @param direction W/A/S/D
     * @param x yogi aktuális x helye
     * @param y yogi aktuális y helye
     */
    private void canStep(String direction, int x, int y){
        switch(direction){
            case "W":
                for(tree i : trees){
                    if(Math.abs(yogi.getX()-i.getX())<30 && (yogi.getY()-i.getY())<30 && (yogi.getY()-i.getY())>=0){
                        yogi.setY(yogi.getY() + yogi.getSTEP());
                    }
                }
                for(mountain i : mountains){
                    if(Math.abs(yogi.getX()-i.getX())<30 && (yogi.getY()-i.getY())<30 && (yogi.getY()-i.getY())>=0){
                        yogi.setY(yogi.getY() + yogi.getSTEP());
                    }
                }
                break;
            case "A":
                for(tree i : trees){
                    if(0<=(yogi.getX()-i.getX()) && (yogi.getX()-i.getX())<30 && Math.abs(i.getY()-yogi.getY())<30){
                        yogi.setX(yogi.getX() + yogi.getSTEP());
                    }
                }
                for(mountain i : mountains){
                    if(0<=(yogi.getX()-i.getX()) && (yogi.getX()-i.getX())<30 && Math.abs(i.getY()-yogi.getY())<30){
                        yogi.setX(yogi.getX() + yogi.getSTEP());
                        break;
                    }
                }
                break;
            case "S":
                for(tree i : trees){
                    if(Math.abs(yogi.getX()-i.getX())<30 && (i.getY()-yogi.getY())<30 && (i.getY()-yogi.getY())>=0){
                        yogi.setY(yogi.getY()-yogi.getSTEP());
                    }
                }
                for(mountain i : mountains){
                    if(Math.abs(yogi.getX()-i.getX())<30 && (i.getY()-yogi.getY())<30 && (i.getY()-yogi.getY())>=0){
                        yogi.setY(yogi.getY()-yogi.getSTEP());
                        break;
                    }
                }
                break;
            case "D": 
                for(tree i : trees){
                    if((i.getX()-yogi.getX())<30 && (i.getX()-yogi.getX())>=0 && Math.abs(i.getY()-yogi.getY())<30){
                        yogi.setX(yogi.getX()-yogi.getSTEP());
                    }
                }
                for(mountain i : mountains){
                    if((i.getX()-yogi.getX())<30 && (i.getX()-yogi.getX())>=0 && Math.abs(i.getY()-yogi.getY())<30){
                        yogi.setX(yogi.getX()-yogi.getSTEP());
                        break;
                    }
                }
                break;
        }
    }
    
    /**
     * a játéktéren szereplő karakterek illetve elemek megjelenéséért felelős
     * @param g -automatikus-
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        //karakterek
        g.drawImage(yogi.getCharacterImage(), yogi.getX(), yogi.getY(), this);
        for(basket i : baskets){
            g.drawImage(i.getCharacterImage(), i.getX(), i.getY(), this);
        }
        for(tree i : trees){
            g.drawImage(i.getCharacterImage(), i.getX(), i.getY(), this);
        }
        for(mountain i : mountains){
            g.drawImage(i.getCharacterImage(), i.getX(), i.getY(), this);
        }
        for(ranger i: rangers){
             g.drawImage(i.getCharacterImage(), i.getX(), i.getY(), this);
        }
        
        //szövegek
        g.setColor(Color.WHITE);
        g.drawString("Yogi életereje: " + HP, 365, 30);
        g.drawString("Összegyűjtött kosarak: " + totalBaskets, 320, 50);
        g.drawString("Eltelt idő: " + String.format("%02d", minutes) + ":" + String.format("%02d", seconds), 370, 70);
    }
    
    /**
     * a w,a,s,d billentyűk lenyomására yogi mozgását kezeli
     * @param e -automatikus-
     */
    @Override
    public void keyPressed(KeyEvent e) {
        
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> {yogi.setY(Math.max(0, yogi.getY() - yogi.getSTEP())); canStep("W", yogi.getX(), yogi.getY());} // Felfelé mozgás
            case KeyEvent.VK_S -> {yogi.setY(Math.min(getHeight() - yogi.getSPRITE_HEIGHT(), yogi.getY() + yogi.getSTEP())); canStep("S", yogi.getX(), yogi.getY());} // Lefelé mozgás
            case KeyEvent.VK_A -> {yogi.setX(Math.max(0, yogi.getX() - yogi.getSTEP())); canStep("A", yogi.getX(), yogi.getY());} // Balra mozgás
            case KeyEvent.VK_D -> {yogi.setX(Math.min(getWidth() - yogi.getSPRITE_WIDTH(), yogi.getX() + yogi.getSTEP())); canStep("D", yogi.getX(), yogi.getY());} // Jobbra mozgás
        }
        repaint();
        
        for(basket i : baskets){
            if(Math.abs(yogi.getX()-i.getX())<30 && Math.abs(yogi.getY()-i.getY())<30){
                i.setVisible(false);
                i.setX(600);
                i.setY(600);
                basketNumber--;
                totalBaskets++;
                if(basketNumber==0){
                    initTable();
                }
            }
        }
    }
    
    public void keyReleased(KeyEvent e) {}
    
    public void keyTyped(KeyEvent e) {}
}
