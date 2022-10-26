/*
Name: Bishal Khadka
Date: 09/17/2022
Section: B
Project: Solving Sudoku
Filename: Cell.java
Course: CS231
*/

//libraries used
import java.awt.Graphics;
import java.awt.Color;
import java.lang.Math;

// Importing the required libraries
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class Cell{
    private int row;
    private int column;
    private int value;
    private boolean locked;
    public JTextField text;  //needed to draw the cell values and is accessed and modified in other classes so static
    
    //constructor
    public Cell() {
        this.row = 0;
        this.column = 0;
        this.value = 0;
        this.locked = false;

        // initializing the JTextField
        text = new JTextField("");
        text.setHorizontalAlignment(JTextField.CENTER); //align the text to center
        text.setEditable(false);
        text.setBorder(localBorder(0, 0));
    }

    //constructor with row, col, and the value given
    public Cell(int newRow, int newCol, int value) {
        this.row = newRow;
        this.column = newCol;
        this.locked = false;
        this.value = value;
        text = new JTextField(Integer.toString(value)); //changing the int to string to add to JTextField
        text.setHorizontalAlignment(JTextField.CENTER);  
        text.setEditable(false);
        text.setBorder(localBorder(this.row, this.column));  //drawing the boarders
        setValue(this.value); //changes the color in the background so setting the value again
    }

     //constructor with row, col, the value given and locked status
    public Cell(int newRow, int newCol, int value, boolean locked) {
        // Initializing the instance variables
        this.row = newRow;
        this.column = newCol;
        this.locked = locked;
        this.value = value;
        text = new JTextField(Integer.toString(value));
        text.setHorizontalAlignment(JTextField.CENTER);
        text.setEditable(false);
        text.setBorder(localBorder(this.row, this.column));
        this.setValue(this.value);
    }

    //returns the row
    public int getRow(){
        return this.row;
    }

    //returns the column
    public int getCol(){
        return this.column;
    }

    //returns the value of the cell
    public int getValue(){
        return this.value;
    }

    //returns the locked status of the cell
    public boolean isLocked(){
        return this.locked;
    }

    //sets the value of the cell to a new value and sets the color of the cell
    public void setValue(int newVal){
        this.value = newVal;
        text.setText(Integer.toString(this.value));
        if(this.locked == true) {   //display the locked cells as green
            text.setBackground(Color.GREEN);
        }
        else if(this.value == 0 && this.locked == false) { //white if the cell has no value (0 value)
            text.setBackground(Color.WHITE);

        } else if(this.locked == false) {   // yellow if the cell is not locked and the value is not null
            text.setBackground(Color.YELLOW);
        }
    }

    //sets the locked status
    public void setLocked(boolean lock){
        this.locked = lock;
    }

    //string representation
    public String toString(){
        String res = "" + this.getValue();
        return res;
    }

    //returns the border accroding to the location of the cell
    public Border localBorder(int row, int col) {
        int topBoarder = 0;  //border point from the top
        int bottomBoarder = 0;   //border point from the bottom
        int leftBoarder = 0;   //border from the left
        int rightBoarder = 0;   //boarder from the right
        int localSquareSize = (int) Math.sqrt(Board.size);

        for (int n = 0; n < localSquareSize; n++){  //run for loop each with the step of the size of the local square cell
            if(row == localSquareSize * n) {  //horizontal border
            topBoarder = 4;
            }

            if(row == Board.size - 1) {   //vertical border
                bottomBoarder = 4;
            }

            if(col == localSquareSize * n) {  //leftborder
                leftBoarder = 4;
            }
            
            if(col == Board.size -1 ) {  //rightborder
                rightBoarder = 4;
            }
        }
        Border border = BorderFactory.createMatteBorder(topBoarder, leftBoarder, bottomBoarder, rightBoarder, Color.CYAN);  //create border with respective points
        return border;
    }

    //draw method
    public void draw(Graphics g, int x, int y, int scale, JPanel canvas) {
        canvas.add(this.text);
    }
}