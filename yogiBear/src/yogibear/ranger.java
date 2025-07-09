/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yogibear;

import java.awt.Color;
import java.awt.Image;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author vikto
 */
public class ranger extends JPanel {
    private int x;
    private int y;
    private final int SPRITE_WIDTH = 30;
    private final int SPRITE_HEIGHT = 30;
    private Image characterImage = new ImageIcon(getClass().getResource("/ranger.png")).getImage();
    private boolean isHorizontal;
    private boolean inc; //nő vagy csökken-e az értéke a move-nál
    private final int DELAY = 100;
    private final int STEP = 10;
    
     public ranger(){
        Random random = new Random();
        
        if(random.nextInt(2)==0){
            this.isHorizontal = true;
            this.x = 0;
            this.y = random.nextInt(351) + 50;
        } else {
            this.isHorizontal = false;
            this.x = random.nextInt(351) + 50;
            this.y = 0;
        }
        
        this.inc = true;
        
        setBackground(Color.GREEN);
        
        ImageIcon icon = new ImageIcon(getClass().getResource("/ranger.png"));
        this.characterImage = icon.getImage().getScaledInstance(SPRITE_WIDTH, SPRITE_HEIGHT, Image.SCALE_SMOOTH);
    
        startMoving();
    }
    
    /**
     * elindítja a ranger mozgását
     */
    private void startMoving() {
        Timer timer = new Timer(DELAY, e -> move());
        timer.start();
    }
    
    /**
     * mozgatja a rangert
     * Ha elérte a pálya szélét, irányt vált
     */
    private void move() {
         if(isHorizontal){
             if(x>=456){inc = false;}
             if(x<=0) {inc = true;}
             if(inc){
                 x += STEP;
             } else {
                 x -= STEP;
             }
         } else {
             if(y>=397){inc = false;}
             if(y<=0) {inc = true;}
             if(inc){
                 y += STEP;
             } else {
                 y -= STEP;
             }
         }
    }
     
    /**
     * irányt vált a rangeren
     */
    public void switchDirection() {
         if(inc){
             inc = false;
         } else {
             inc = true;
         }
     }

    //getter-setter
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public Image getCharacterImage() {
        return characterImage;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
}
