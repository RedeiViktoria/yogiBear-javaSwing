/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yogibear;

import java.awt.Image;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author vikto
 */
public class tree extends JPanel {
    private int x;
    private int y;
    private final int SPRITE_WIDTH = 30;
    private final int SPRITE_HEIGHT = 30;
    private Image characterImage = new ImageIcon(getClass().getResource("/tree.png")).getImage();
    
    public tree(){
        Random random = new Random();
        
        this.x = random.nextInt(351) + 50;
        this.y = random.nextInt(351) + 50;
        
        ImageIcon icon = new ImageIcon(getClass().getResource("/tree.png"));
        this.characterImage = icon.getImage().getScaledInstance(SPRITE_WIDTH, SPRITE_HEIGHT, Image.SCALE_SMOOTH);
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
