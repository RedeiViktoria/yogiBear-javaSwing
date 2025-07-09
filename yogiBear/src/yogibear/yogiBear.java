/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yogibear;


import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.Image;

/**
 *
 * @author vikto
 */
public class yogiBear extends JPanel {
    private int x;
    private int y;
    private final int STEP = 10;
    private final int SPRITE_WIDTH = 30;
    private final int SPRITE_HEIGHT = 30;
    private Image characterImage = new ImageIcon(getClass().getResource("/yogiBear.png")).getImage();
    
    public yogiBear(int HP){
        this.x = 0;
        this.y = 0;
        
        ImageIcon icon = new ImageIcon(getClass().getResource("/yogiBear.png"));
        this.characterImage = icon.getImage().getScaledInstance(SPRITE_WIDTH, SPRITE_HEIGHT, Image.SCALE_SMOOTH);
    }
    
    /**
     * beállítja yogi x és y értékét 0-ra -> start helyre kerül
     */
    public void minusHP(){
        this.x = 0;
        this.y = 0;
    }

    //getter-setter
    public int getSPRITE_WIDTH() {
        return SPRITE_WIDTH;
    }
    public int getSPRITE_HEIGHT() {    
        return SPRITE_HEIGHT;
    }
    public Image getCharacterImage() {
        return characterImage;
    }
    public int getSTEP() {
        return STEP;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
}
