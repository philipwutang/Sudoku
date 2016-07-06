/**
 * Assignment: Individual Summative Project - Sudoku
 * Course Code: ICS3U1-01
 * Author: Philip Tang
 * Date: 2 June 2015
 * Description: Creates a Sudoku puzzle that has three difficulties.
 *              The program checks the puzzle itself and scores the 
 *              user based on the time of completion. This class
 *              creates a new Sudoku puzzle for the user to solve. It
 *              user the backtrack method of generating Sudoku.
 */

import java.util.*;

/**
 * The class which creates a Sudoku puzzle using the backtrack method.
 */
public class PuzzleGenerator{
  // Declares variables
  boolean firstCheck, proceed, secondCheck;
  ArrayList<Integer> tempColValues, tempRowValues, tempSquareValues;
  Set<Integer> tempValues, tempValues2;
  
  /**
   * Method which sets default values to the variables.
   */
  protected void init(int[][] boardValues){
    firstCheck = true;
    proceed = false;
    secondCheck = false;
    
    // Creates a temporary list that holds the avaliable values of each row.
    tempRowValues = new ArrayList<Integer>();
    
    // Adds the numbers 1 - 9 into the temporary list of row values.
    for (int i = 1; i < 10; i++){
      tempRowValues.add(i);
    }
    
    // Randomizes the positions of the values in the row.
    Collections.shuffle(tempRowValues);  
    
    // Sets the values of the first row to the values of the temporary list.
    for (int colNumber = 0; colNumber < 9; colNumber++){
      boardValues[0][colNumber] = tempRowValues.get(colNumber);
    }
    
    // Creates sets that will be used to reorder the board values.
    tempValues = new HashSet<Integer>();
    tempValues2 = new HashSet<Integer>();
  }
  
  /*
   * Method which checks for duplicate values in the rows of the baord. If there
   * is a duplicate the program will backtrack.
   */
  protected void newPuzzle(int[][] boardValues){    
    /*
     * For loops that checks if duplicate values are found in the 
     * rows of the board.
     */
    for (int rowNumber = 0; rowNumber < 9; rowNumber++){
      // Checks for duplicate values in the 3x3 squares
      if (rowNumber % 3 == 0 && rowNumber != 0){
        // Determines if the value is already on the board
        firstCheck = squareCheck(boardValues, rowNumber);
        // The program backtracks to the beginning of 3x3 row and tries again
        if (firstCheck == false)
          rowNumber = rowNumber - 3;
      }
      
      // Checks for duplicates in the columns
      for (int colNumber = 0; colNumber < 9; colNumber++){
        firstCheck = colCheck(boardValues, rowNumber, tempRowValues);
        // The program backtracks.
        if (firstCheck == false){
          rowNumber = rowNumber - 1;
          break;
        }
        // If the number is unique, it will be added to the Sudoku board
        else {
          boardValues[rowNumber][colNumber] = tempRowValues.get(colNumber);
        }
      }
      // Randomizes the possibilities to try again
      Collections.shuffle(tempRowValues);
    }
  }
  
  /*
   * Method which checks for duplicate values in the nine 3x3 squares that 
   * make up the Sudoku board. If there is a duplicate the program 
   * will backtrack.
   */
  protected boolean squareCheck(int[][] boardValues, int rowNumber) {
    proceed = true;
    
    // Creates a list of the temporary values of the 3x3 squares
    tempSquareValues = new ArrayList<Integer>();
    
    /*
     * Only checks the squares if the row number matches the row number of the 
     * beginning of a 3x3 square.
     */
    if (rowNumber % 3 == 0){
      // Adds the current values of board to the possible values in the list
      for (int i = rowNumber - 3; i < rowNumber; i++){
        for (int j = 0; j < 3; j++){
          tempSquareValues.add(j, boardValues[i][j]);
        }
      }
      
      // Set method removes duplicates and reorders the list
      tempValues = new HashSet<Integer>(tempSquareValues);
      
      // If there are more possibilities, exit and backtrack
      if (tempSquareValues.size() > tempValues.size()){
        proceed = false;
      }
      
      /*
       * Since there are fewer possibilities, moves to the next 3x3 square.
       */
      else {
        // Clears the temporary values.
        tempSquareValues.clear();
        tempValues.clear();
        
        // Adds possible values to the list
        for(int i = rowNumber - 3; i < rowNumber; i++ ){
          for(int j = 3; j < 6; j++){
            tempSquareValues.add(boardValues[i][j]);
          }
        }
        
        // Set method removes duplicates and reorders the list
        tempValues = new HashSet<Integer>(tempSquareValues);
        
        // If there are more possibilities, exit and backtrack
        if(tempSquareValues.size() > tempValues.size()){
          proceed = false;
        }
      }
    }
    // If the 3x3 square consists of unique values, the program moves on
    return proceed;
  }
  
  /*
   * Method which checks for duplicate values in the columns that 
   * make up the Sudoku board. If there is a duplicate the program 
   * will backtrack.
   */
  protected boolean colCheck(int[][] boardValues, int rowNumber, 
                             ArrayList<Integer> tempRowValues) {
    secondCheck = true; 
    tempColValues = new ArrayList<Integer>();
    
    // Sets the default values of the first column
    for(int colNumber = 0; colNumber < 9; colNumber++){
      boardValues[rowNumber][colNumber] = tempRowValues.get(colNumber);
    }
    
    // Adds the list of column values to the first column
    for(int i = 0; i < 9; i++){
      for(int colNumber = 0; colNumber <= rowNumber; colNumber++){
        tempColValues.add(colNumber, boardValues[colNumber][i]); 
      }
      
      // Creates a set method that removes duplicates and reorders the list
      tempValues2 = new HashSet<Integer>(tempColValues);
      
      // If there are more possibilities, exit and backtrack
      if(tempColValues.size() >  tempValues2.size()){
        secondCheck = false; 
        break;
      }
      
      // Clears all possibilities and tries again
      else {
        tempColValues.clear();
        tempValues2.clear();
      }
    }
    // Determines if the check was successful, if not the program will backtrack
    return secondCheck;
  }
}