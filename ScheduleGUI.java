/* CS230 Final Project
 * ScheduleGUI.java
 * Meher Vohra, Remi Kobayashi, Rosanne Hu
 * 1/12/16
 * 
 * This class creates a JFrame containing the SchedulePanel.
 * 
 * @author Remi Kobayashi
 * @version 1.0
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ScheduleGUI{
  
  /**
   * Creates the GUI containing SchedulePanel.
   */
  public ScheduleGUI(){
    // Creates and shows a Frame 
    JFrame frame = new JFrame("Course Schedule");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setPreferredSize(new Dimension(800,800));
    
    // Creates a panel, and add it to the frame
    SchedulePanel panel = new SchedulePanel();
    
    frame.getContentPane().add(panel);
    
    frame.pack();
    frame.setVisible(true);
  }
  
  public static void main(String[] args){
    ScheduleGUI gui = new ScheduleGUI();
  }
  
  
}