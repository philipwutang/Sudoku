/**
 * Assignment: Individual Summative Project - Sudoku
 * Course Code: ICS3U1-01
 * Author: Philip Tang
 * Date: 2 June 2015
 * Description: Creates a Sudoku puzzle that has three difficulties.
 *              The program checks the puzzle itself and scores the 
 *              user based on the time of completion. This class is
 *              the main class which contains the main method.
 */

/**
 * This class contains the main method, the entrance into the rest
 * of the program.
 */
public class Main {
  /**
   * The main method which provides the entry into the program.
   */
  public static void main(String[] args) {
    /**
     * Instantiates the GameControl class and calls the member 
     * method setUpFrame followed by control.
     */
    GameControl execute = new GameControl();
    execute.setUpFrame();
    execute.control();
  }
}