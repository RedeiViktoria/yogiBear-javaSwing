/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yogibear;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author vikto
 */
public class yogiBearGUI {
    
    private JFrame frame; 
    private JButton button;
    private JPanel buttonPanel;
    //private JButton leaderboardButton;
    
    private gameField field;
    
    
    public yogiBearGUI(){
        this.field = new gameField();
        
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.add(field, BorderLayout.CENTER);
        
        //buttons
        buttonPanel = new JPanel();
            //new game
        button = new JButton("New Game");
        button.addActionListener(new newGameActionListener());
        buttonPanel.add(button);
            //leaderboard
            /*
        this.leaderboardButton = new JButton("Leaderboard");
        leaderboardButton.addActionListener(new leaderBoardActionListener());
        buttonPanel.add(leaderboardButton);*/
        
        frame.add(buttonPanel, BorderLayout.SOUTH);
        
        frame.setSize(500, 500);
        //frame.pack(); //felveszi automatikusan a méretet
        
        frame.setVisible(true);
    }
    
    class newGameActionListener implements ActionListener {
        public newGameActionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            frame.remove(field);
            field = new gameField();
            frame.add(field, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();
            
            field.requestFocusInWindow();
        }
    }
    /*
    class leaderBoardActionListener implements ActionListener {
        public leaderBoardActionListener(){
            
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(frame, field.getLeaderBoard());
        }
    }*/
}
