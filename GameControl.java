/**
 * Assignment: Individual Summative Project - Sudoku
 * Course Code: ICS3U1-01
 * Author: Philip Tang
 * Date: 2 June 2015
 * Description: Creates a Sudoku puzzle that has three difficulties.
 *              The program checks the puzzle itself and scores the 
 *              user based on the time of completion. This class is
 *              the class which initializes the JFrame used to display
 *              the puzzle and starts a new thread to run the program.
 */

import javax.swing.*;
import java.awt.Dimension;

/**
 * The class which coordinates how the Sudoku puzzle runs. It initializes
 * the frame and starts a new thread to run the program.
 */
public class GameControl {
  // Initializes the JFrame
  JFrame frame = new JFrame("ISP - Sudoku By: Philip Tang");
  
  // Method which sets up the behaviors of the frame.
  protected void setUpFrame() {
    frame.setVisible(true);
    frame.setResizable(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     
    frame.setMinimumSize(new Dimension(391, 482));
    frame.setLocationRelativeTo(null);
  }
  
  // Method which creates a new thread for the puzzle to run.
  protected void control() {
    PuzzleInterface application = new PuzzleInterface(frame);
    new Thread(application).start();
  }
}