/**
 * Assignment: Individual Summative Project - Sudoku
 * Course Code: ICS3U1-01
 * Author: Philip Tang
 * Date: 2 June 2015
 * Description: Creates a Sudoku puzzle that has three difficulties.
 *              The program checks the puzzle itself and scores the 
 *              user based on the time of completion. This class
 *              displays all the visual components of the Sudoku puzzle
 *              in the JFrame. It also adjusts the puzzle based on the
 *              user's difficulty and tabulates a score. The puzzle
 *              keeps running until the user does not want to play 
 *              again.
 */

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.swing.*;

/**
 * The class which creates the visual components of the Sudoku puzzle.
 * Displays the puzzle in the JFrame as well as the menu, buttons and
 * dialog boxes to help the user.
 */
public class PuzzleInterface extends JPanel implements Runnable, ActionListener{
  /*
   * Declares global variables for the program.
   */ 
  int difficulty, randRow, randColumn;
  long startTime, timeElapsed, timeElapsedSeconds, timeElapsedMinutes;
  String fileName;
  boolean click, newHighScore;
  
  int[][] boardValues, checkValues;
  JButton[][] boardButtons;
  
  Button check, generate, solve, time;
  JFrame frame;
  JPanel panel1, panel2, panel3;
  JLabel gameLabel;
  
  /*
   * Method which initiates a value to all variables.
   */ 
  protected void initialize(){
    difficulty = 0;
    randColumn = 0;
    randRow = 0;
    
    // Boolean values to control program progression.
    click = true;
    newHighScore = false;
    
    // Creates a 9 by 9 array for all board values.
    boardValues = new int[9][9];
    boardButtons = new JButton[9][9];
    checkValues = new int[9][9];
    
    // Fills the button array with buttons, all with a blank label.
    for(int rowNumber = 0; rowNumber < 9; rowNumber++){
      for(int colNumber = 0; colNumber < 9; colNumber++){
        boardButtons[rowNumber][colNumber] = new JButton("");
      }
    }
    
    // Creates four buttons.
    check = new Button("Check");
    generate = new Button("Generate");
    solve = new Button("Solve");
    time = new Button("Time");
    
    // Creates three new panels to hold content in the JFrame.
    panel1 = new JPanel();
    panel2 = new JPanel(new GridLayout(9, 9, 2, 2));;
    panel3 = new JPanel();
    
    // Creates two labels.
    gameLabel = new JLabel("Sudoku");
  }
  
  /*
   * Method which adds the buttons into the center panel of the JFrame.
   * The buttons are colored to divide the 3x3 squares.
   */
  protected void displayBoard(){
    for(int rowNumber = 0; rowNumber < 9; rowNumber++){
      for(int colNumber = 0; colNumber < 9; colNumber++){
        // Colours the buttons light grey
        if (((3 > colNumber || colNumber > 5) && 
             (3 > rowNumber || rowNumber > 5)) || 
            ((3 <= colNumber && colNumber <= 5) && 
             (3 <= rowNumber && rowNumber <= 5))){
          boardButtons[rowNumber][colNumber].setMargin
            (new Insets(4, 0, 4, 0));
          boardButtons[rowNumber][colNumber].setBackground
            (new Color(190, 190, 190));
          boardButtons[rowNumber][colNumber].setForeground(Color.WHITE);
          boardButtons[rowNumber][colNumber].setFocusPainted(false);
          boardButtons[rowNumber][colNumber].setFont
            (new Font("Arial", Font.PLAIN, 20));
          panel2.add(boardButtons[rowNumber][colNumber]);
        }
        // Colours the buttons dark grey.
        else{
          boardButtons[rowNumber][colNumber].setMargin
            (new Insets(4, 0, 4, 0));
          boardButtons[rowNumber][colNumber].setBackground
            (new Color(165, 165, 165));
          boardButtons[rowNumber][colNumber].setForeground(Color.WHITE);
          boardButtons[rowNumber][colNumber].setFocusPainted(false);
          boardButtons[rowNumber][colNumber].setFont
            (new Font("Arial", Font.PLAIN, 20));
          panel2.add(boardButtons[rowNumber][colNumber]);
        }
      }
    }
    // Adds buttons to the JFrame.
    frame.add(panel2, BorderLayout.CENTER);
  }
  
  /*
   * Method which hides certain squares for the user to fill in.
   */
  protected void hideSquares(){
    int hiddenTiles;
    
    // Clears the panel of all buttons.
    panel2.removeAll();
    
    /*
     * If statements the determines which level of difficulty the user
     * is playing and will hide the respectable number of squares.
     */
    if (difficulty == 0){
      // Easy difficulty hides 30 squares.
      hiddenTiles = 30;
      fileName = "EasyScore.txt";
    }
    
    else if (difficulty == 1){
      // Easy difficulty hides 40 squares.
      hiddenTiles = 40;
      fileName = "MediumScore.txt";
    }
    
    else {
      // Easy difficulty hides 50 squares.
      hiddenTiles = 50;
      fileName = "HardScore.txt";
    }
    
    /*
     * Sets the labels in the button array to the numbers of the 
     * generated Sudoku board.
     */
    for(int rowNumber = 0; rowNumber < 9; rowNumber++){
      for(int colNumber = 0; colNumber < 9; colNumber++){
        boardButtons[rowNumber][colNumber] = new JButton
          (String.valueOf(boardValues[rowNumber][colNumber]));
      }
    }
    
    /*
     * For loop which hides certain squres based on the difficulty
     */ 
    for(int hide = 0; hide < hiddenTiles; hide++){
      while (true){
        // Calls the random number generator.
        Random random = new Random();
        // Randomly generates a location on the Sudoku board.
        randRow = random.nextInt(9);
        randColumn = random.nextInt(9);
        
        // If the button isn't already hidden, the lebel is hidden
        if ((boardButtons[randRow][randColumn]).getText() != ""){
          boardButtons[randRow][randColumn] = new JButton("");
          
          /*
           * Adds an action listener to the buttons that are hidden.
           * When the user selects the button, they can decide which 
           * number (1-9) should go in the square. This number becomes
           * the new label.
           */
          boardButtons[randRow][randColumn].addActionListener
            (new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
              JButton source = (JButton) event.getSource();
              Object[] options = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
              // Pop out option pane appears.
              int value = JOptionPane.
                showOptionDialog(frame,
                                 "Select a number:",
                                 "Value",
                                 JOptionPane.YES_NO_CANCEL_OPTION,
                                 JOptionPane.QUESTION_MESSAGE,
                                 null,
                                 options,
                                 options[0]);
              // Button label is updated.
              boardButtons[randRow][randColumn] = new JButton
                (String.valueOf(value + 1));
              source.setText(String.valueOf(value + 1));
            }
          });
          break;
        }
        
        // If the current square is alread hidden, a new one is generated.
        else {
          randRow = random.nextInt(9);
          randColumn = random.nextInt(9);
        }
      }
    }
    // The drawBoard method is called to add the buttons to the board.
    displayBoard();
  }
  
  /*
   * Method that is called to generate a new Sudoku board at the 
   * specified difficulty.
   */ 
  protected void createPuzzle(){
    String puzzleDifficulty;
    
    // If statement that adjusts the level difficulty displayed.
    if (difficulty == 0){
      puzzleDifficulty = "easy";
    }
    else if (difficulty == 1){
      puzzleDifficulty = "medium";
    }
    else {
      puzzleDifficulty = "hard";
    }
    
    // Creates a dialog box while the program generates a new puzzle.
    JOptionPane waitPane = new JOptionPane
      ("We are hard at work \n" +
       "generating a " + puzzleDifficulty + " puzzle. \n" + 
       "Hold on one second!", JOptionPane.INFORMATION_MESSAGE);
    JDialog waitDialog = waitPane.createDialog(null, "Please Wait");
    
    // Displays the dialog box.
    waitDialog.setVisible(true);
    
    /**
     * Try-catch which attempts to prevent any errors when generating
     * a new puzzle.
     */ 
    try{
      // A new puzzle is generated.
      PuzzleGenerator generate = new PuzzleGenerator();
      generate.init(boardValues); 
      generate.newPuzzle(boardValues);
    }
    catch (java.lang.NullPointerException error){}
    
    // Some buttons are allowed to be clicked more than once.
    click = true;
    
    // Certain squares are hidden.
    hideSquares();
    
    // The dialog box is removed.
    waitDialog.setVisible(false);
    
    // A new timer starts for the new puzzle.
    startTime = System.currentTimeMillis();
  }
  
  /*
   * Method that creates the menu at the top of the JFrame. This menu
   * contains difficulty settings, high scores, rules and information
   * about the developer.
   */ 
  protected JMenuBar createMenu(){
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem menuItem;
    
    // Create the menu bar.
    menuBar = new JMenuBar();
    
    // Builds first menu.
    menu = new JMenu("Settings");
    menu.setMnemonic(KeyEvent.VK_S);
    menu.getAccessibleContext().
      setAccessibleDescription("Adjust Game Settings");
    menuBar.add(menu);
    
    // Adds items to the first menu.
    menuItem = new JMenuItem("Difficulty", KeyEvent.VK_D);
    menuItem.setAccelerator
      (KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
    menuItem.getAccessibleContext().
      setAccessibleDescription("Select Game Mode");
    menuItem.addActionListener(this);
    menu.add(menuItem);
    
    // Build second menu.
    menu = new JMenu("Scores");
    menu.setMnemonic(KeyEvent.VK_C);
    menu.getAccessibleContext().setAccessibleDescription("View Score");
    menuBar.add(menu);
    
    // Adds items to the second menu.
    menuItem = new JMenuItem("High Scores", KeyEvent.VK_H);
    menuItem.setAccelerator
      (KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
    menuItem.getAccessibleContext().
      setAccessibleDescription("View High Scores");
    menuItem.addActionListener(this);
    menu.add(menuItem);
    
    // Build third menu.
    menu = new JMenu("Instructions");
    menu.setMnemonic(KeyEvent.VK_I);
    menu.getAccessibleContext().setAccessibleDescription("View Score");
    menuBar.add(menu);
    
    // Adds items to the third menu.
    menuItem = new JMenuItem("Sudoku Instructions", KeyEvent.VK_S);
    menuItem.setAccelerator
      (KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
    menuItem.getAccessibleContext().
      setAccessibleDescription("View Instructions");
    menuItem.addActionListener(this);
    menu.add(menuItem);
    
    // Builds the last menu.
    menu = new JMenu("About");
    menu.setMnemonic(KeyEvent.VK_A);
    menu.getAccessibleContext().setAccessibleDescription("View Score");
    menuBar.add(menu);
    
    // Adds items to the forth menu.
    menuItem = new JMenuItem("Information", KeyEvent.VK_I);
    menuItem.setAccelerator
      (KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
    menuItem.getAccessibleContext().
      setAccessibleDescription("View Information About the Project");
    menuItem.addActionListener(this);
    menu.add(menuItem);
    
    return menuBar;
  }
  
  /*
   * Method which adds an action listener to the menu bar.
   */
  public void actionPerformed(ActionEvent event) {
    JMenuItem source = (JMenuItem)(event.getSource());
    /*
     * A pop up option pane appears that asks for the difficulty the 
     * user wants to play at. After a difficulty is selected, a new
     * board is created for that difficulty.
     */
    if (source.getText() == "Difficulty") {
      Object[] options = {"Easy", "Medium", "Hard"};
      difficulty = JOptionPane.showOptionDialog
        (frame,
         "Please select a difficulty:",
         "Difficulty",
         JOptionPane.YES_NO_CANCEL_OPTION,
         JOptionPane.QUESTION_MESSAGE,
         null,
         options,
         options[0]);
      createPuzzle();
    }
    
    /*
     * A pop up message dialog appears that displays the current high
     * scores for all three difficulties
     */
    if (source.getText() == "High Scores"){
      /*
       * Try-catch statement which attempts to catch any errors when 
       * using the buffer reader
       */
      try {
        // The easy high score is read.
        FileInputStream easyFile = new FileInputStream("EasyScore.txt");
        BufferedReader readEasy = new BufferedReader
          (new InputStreamReader(easyFile));
        String easyScore = readEasy.readLine() + ":" + readEasy.readLine();
        readEasy.close();
        
        // The medium high score is read.
        FileInputStream mediumFile = new FileInputStream("MediumScore.txt");
        BufferedReader readMedium = new BufferedReader
          (new InputStreamReader(mediumFile));
        String mediumScore = readMedium.readLine() + ":" + readMedium.readLine();
        readMedium.close();
        
        // The hard high score is read.
        FileInputStream hardFile = new FileInputStream("HardScore.txt");
        BufferedReader readHard = new BufferedReader
          (new InputStreamReader(hardFile));
        String hardScore = readHard.readLine() + ":" + readHard.readLine();
        readHard.close();
        
        // A pop up message dialog appears that displays all the scores.
        JOptionPane.showMessageDialog
          (frame,
           "Sudoku High Scores: \n" + 
           "Easy: " + easyScore + " \n" +
           "Medium: " + mediumScore + " \n" +
           "Hard: " + hardScore,
           "High Scores",
           JOptionPane.PLAIN_MESSAGE);
      }
      catch(IOException error){}
    }
    
    // A pop up message dialog displays the instructions of sudoku.
    if (source.getText() == "Sudoku Instructions"){
      JOptionPane.showMessageDialog
        (frame,
         "1. Sudoku is played over a 9x9 grid, divided to 3x3 sub " + 
         "grids called 'regions' \n" + 
         "2. Sudoku begins with some of the grid cells already filled " +
         "with numbers \n" +
         "3. The object of Sudoku is to fill the other empty cells with numbers\n" +
         "     between 1 and 9 (1 number only in each cell) according \n" +
         "     to the following guidelines: \n" +
         "          Numbers can appear only once in each row \n" +
         "          Numbers can appear only once in each column \n" +
         "          Numbers can appear only once in each region \n \n" + 
         "Hidden Squares:  Easy [30],  Medium [40],  Hard [50] \n" + 
         "Finish the puzzle faster for a better time!",
         "Sudoku Instructions",
         JOptionPane.PLAIN_MESSAGE);
    }
    
    // A pop up message dialog displays information about the project
    if (source.getText() == "Information"){
      JOptionPane.showMessageDialog
        (frame,
         "Developer: Philip Tang \n" + 
         "Course: ISU3U1-01 \n" +
         "Program: Sudoku ISP \n" +
         "Last Modified: 06/01/2015 05:00 PM",
         "About",
         JOptionPane.PLAIN_MESSAGE);
    }
  }
  
  /*
   * Method which takes the time it took to complete the Sudoku and
   * saves it to the respectible high score file.
   */
  protected void highScores(){
    // Try-catch statement that attempts to prevent any errors.
    try {
      FileInputStream readFile = new FileInputStream(fileName);
      // Buffer reader that recieves the old score.
      BufferedReader readScore = new BufferedReader
        (new InputStreamReader(readFile));
      
      int oldMinutes = Integer.valueOf(readScore.readLine());
      int oldSeconds = Integer.valueOf(readScore.readLine());
      
      // Closes the buffer reader.
      readScore.close();
      
      // Determines the total time elapsed to solve the puzzle.
      timeElapsed = System.currentTimeMillis() - startTime;
      // Formats the times into minutes and seconds.
      timeElapsedSeconds = timeElapsed / 1000 % 60;
      timeElapsedMinutes = timeElapsed / (60 * 1000) % 60;
      
      // If the new time is faster than the previous, the high score is updated
      if (timeElapsedMinutes < oldMinutes
            ||(timeElapsedMinutes == oldMinutes)
            && (timeElapsedSeconds <= oldSeconds)){
        // Creats new buffer writer to add the high score.
        File file = new File(fileName);
        BufferedWriter writeScore = new BufferedWriter
          (new FileWriter(file));
        
        // Removes the old score from the text file.
        writeScore.flush();
        // Writes the new high score to the file.
        writeScore.write(String.format("%02d", timeElapsedMinutes));
        writeScore.newLine();
        writeScore.write(String.format("%02d", timeElapsedSeconds));
        // Closes the file writer.
        writeScore.close();
        
        newHighScore = true;
      }
    }
    catch(IOException error){}
  }
  
  /*
   * Method which adds action listeners to the buttons in the JFrame.
   */
  protected void buttonActions(){    
    // Creates an action listener when the user wants to generate a new puzzle.
    generate.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent event){
        // A new puzzle is generated.
        createPuzzle();
      }
    });
    
    // Creates an action listener which solves the Sudoku puzzle
    solve.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent event){
        if (click) {
          // The values of the Sudoku board are set to the button labels.
          for(int rowNumber = 0; rowNumber < 9; rowNumber++){
            for(int colNumber = 0; colNumber < 9; colNumber++){
              boardButtons[rowNumber][colNumber] = new JButton
                (String.valueOf(boardValues[rowNumber][colNumber]));
            }
          }
          // The center panel is cleared.
          panel2.removeAll();
          // The new buttons are added to the panel.
          displayBoard();
          // The panel is refreshed to display the new buttons.
          panel2.revalidate();
          panel2.repaint();
        }
      }
    });
    
    // Creates an action listener which checks if the Sudoku is solved
    check.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent event){
        try {
          for(int rowNumber = 0; rowNumber < 9; rowNumber++){
            for(int colNumber = 0; colNumber < 9; colNumber++){    
              // The values of the board are added to a int array
              checkValues[rowNumber][colNumber] = Integer.parseInt
                (boardButtons[rowNumber][colNumber].getText());
            }
          }
          
          // If the new array matches the original board, the puzzle is solved
          if (Arrays.deepEquals(boardValues, checkValues)){
            // The score is checked to see if it is a high score.
            highScores();
            
            /** 
             * If statment that displays a dialog box with a message
             * based on how the user performs.
             */
            if (newHighScore == true){
              // A pop up message dialog displays when there is a new high score.
              JOptionPane.showMessageDialog
                (frame,
                 "Congratulations! \n" + 
                 "You got a new high score. \n" +
                 "You solved it in " +
                 String.format("%02d", (timeElapsedMinutes)) + 
                 ":" + String.format("%02d", (timeElapsedSeconds)) + "!");
              newHighScore = false;
            }
            
            else {
              // A pop up message dialog displays the time the puzzle was solved in.
              JOptionPane.showMessageDialog
                (frame,
                 "Awesome Job! \n" + 
                 "You solved it in " +
                 String.format("%02d", (timeElapsedMinutes)) + 
                 ":" + String.format("%02d", (timeElapsedSeconds)) + "!");
            }
            
            // A new pop up option dialog asks if the user wants to play again.
            Object[] options = {"Yes", "No"};
            int exit = JOptionPane.
              showOptionDialog
              (frame,
               "Would you like to play again?",
               "Play Again?",
               JOptionPane.YES_NO_CANCEL_OPTION,
               JOptionPane.QUESTION_MESSAGE,
               null,
               options,
               options[0]);
            
            // A new puzzle is generated
            if (exit == 1){
              frame.dispose();
              System.exit(0);
            }
            // The game end and the JFrame closes
            else {
              createPuzzle();
            }
          }
          
          // If the puzzle doesn't match up the user is prompted to try again.
          else {
            JOptionPane.showMessageDialog
              (frame,
               "Nice try! \n" +
               "But you have some mistakes \n" + 
               "Give it another try!");
          }
        }
        
        /*
         * If the array is left empty the catch statement prevents an 
         * error. Since the puzzle wasn't completed, the user is 
         * prompted to fill it out completely.
         */
        catch (java.lang.NumberFormatException error){
          // A pop up messag dialog prompts the user to finish the puzzle.
          JOptionPane.showMessageDialog
            (frame,
             "Hold On! \n" +
             "You have to fill out the board before you check it \n" + 
             "Go ahead, fill it out!",
             "Sudoku Incomplete",
             JOptionPane.WARNING_MESSAGE);
        }
      }
    });
  }
  
  /**
   * Method that sets up the layout of the JFrame.
   */
  protected void setUpLayout(){
    // Sets the layout of the JFrame to a Border Layout.
    frame.setLayout(new BorderLayout());
    
    // Sets a default font for the label.
    gameLabel.setFont(new Font ("Times New Roman", Font.PLAIN, 24)); 
    
    // Adds panels to the JFrame.
    panel1.add(gameLabel);
    panel3.add(generate);
    panel3.add(solve);
    panel3.add(check);
    panel3.add(time);
    
    // Adds panels to the JFrame.
    frame.add(panel1, BorderLayout.NORTH);
    frame.add(panel3, BorderLayout.SOUTH);
  }
  
  /**
   * Helper method which we use to get the focus
   */
  protected void getFocus(final JFrame frame)
  {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        frame.requestFocus();
      }
    });
  }
  
  /**
   * Runnable method which that sets up the layout of the JFrame.
   */
  public void run(){
    // Calls the method which creates the top menu bar.
    createMenu();
    // Adds the menu bar to the JFrame.
    frame.setJMenuBar(createMenu());
    
    // Adds the action listeners to the bottom buttons.
    buttonActions();
    
    // Sets up the lauout of the JFrame.
    setUpLayout();
    
    // Creates the first puzzle.
    createPuzzle();

    // While loop that keeps refreshing the JFrame.
    while(true){
      // The center panel is refreshed.
      panel2.revalidate();
      panel2.repaint();      
      
      // Determines the amount of time spent solving the puzzle.
      timeElapsed = System.currentTimeMillis() - startTime;
      // Converts the elapsed time into seconds and minutes.
      timeElapsedSeconds = timeElapsed / 1000 % 60;
      timeElapsedMinutes = timeElapsed / (60 * 1000) % 60;
      
      // The time is formatted.
      String scoreTime = String.format("%02d", timeElapsedMinutes) + ":" + 
        String.format("%02d", timeElapsedSeconds);
      // The time is displayed on a the button label.
      time.setLabel("Time: " + scoreTime);
      
      // The bottom panel is refreshed.
      panel3.revalidate();
      panel3.repaint();
    }
  }
  
  /**
   * Constructor
   */
  protected PuzzleInterface(final JFrame frame){
    this.frame = frame;
    // Focuses the JFrame
    getFocus(frame);
    // Initiates variables used
    initialize();
    // Calls the Runnable method to start the program.
    run();
  }
}