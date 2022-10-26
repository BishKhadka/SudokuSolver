/*
Name: Bishal Khadka
Date: 09/17/2022
Section: B
Project: Solving Sudoku
Filename: Board.java
Course: CS231
*/

//used libraries
import java.io.*;
import java.util.Random;
import java.lang.Math;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Color;


public class Board {
    private Cell[][] board;
    public boolean finished;
    public static int size;  //default value for size but will change if passed through constructer

    //constructor
    public Board(){
        Board.size = 9;
        board = new Cell[Board.size][Board.size];
        for (int i = 0; i < Board.size; i++){
            for (int j = 0; j< Board.size ; j++){
                board[i][j] = new Cell();
                get(i,j).text.setBorder(get(i,j).localBorder(i, j));
            }
        }
    }

    //constructs a board from the txt file
    public Board(String filename){
        read(filename);
    }

    //constructs the board with a given size
    public Board(int newSize){
        if (this.isPerfectSquare(newSize)){
            Board.size = newSize;
            board = new Cell[Board.size][Board.size];
            for (int i = 0; i < Board.size; i++){
                for (int j = 0; j< Board.size ; j++){
                    board[i][j] = new Cell();
                    get(i,j).text.setBorder(get(i,j).localBorder(i, j));
                }
            }
        } else{
            System.out.println(" \n The size of the board must be a non-negative perfect square number");
        }
    }

    //constructs a board with a given size and given initial values
    public Board(int newSize, int numLock){    
        this(newSize);
        Random ran = new Random();
        int count = 0;
        while (count < numLock){
            int r = ran.nextInt(0,newSize);
            int c = ran.nextInt(0,newSize);
            int val = ran.nextInt(1,newSize+1);
            if (validValue(r,c,val)){
                board[r][c] = new Cell(r, c, val, true);
                get(r,c).text.setBorder(get(r,c).localBorder(r, c));
                count++;
            } 
        }
    }

    //returns if a number is a perfect square
    public boolean isPerfectSquare(int size){
        if (size < 0){
            return false;
        }
        else{
            int root = (int) Math.sqrt(size);
            return (root * root == size);
        }
    }

    //string representation
    public String toString(){
        String res = "";
        for (int i = 0; i < size; i++){
            for (int j = 0; j< size ; j++){
                if (i% Math.sqrt(size) == 0 && j == 0){  //doing this would create spaces exaclt after the last column of the local square
                    res += "\n";
                }
                if (j % Math.sqrt(size) == 0){
                    res += "   ";
                }
                res += board[i][j].getValue() + " ";
            }
            res += "\n";
        }
        return res;
    }

    //returns the columns
    public int getCols(){
        return Board.size;
    }

    //returns the row no.
    public int getRows(){
        return Board.size;
    }

    //returns the cell at r,c 
    public Cell get(int r, int c){
        return this.board[r][c];
    }

    //returns if a cell is locekd
    public boolean isLocked(int r, int c){
        return this.board[r][c].isLocked();
    }

    //returns the no. of locked cells
    public int numLocked(){
        int res = 0;
        for (int i = 0; i < Board.size; i++){
            for (int j = 0; j< Board.size ; j++){
                if (this.board[i][j].isLocked()){
                    res+=1;
                }
            }
        }
        return res;
    }

    //returns the value of the cell
    public int value(int r, int c){
        return this.board[r][c].getValue();
    }

    //sets the value of the cell
    public void set(int r, int c, int value){
        this.board[r][c].setValue(value);
    }

    //sets the value and the locked status of the cell
    public void set(int r, int c, int value, boolean locked){
        this.board[r][c].setValue(value);
        this.board[r][c].setLocked(locked);
    }

    //reads the filename
    public boolean read(String filename) {
        try {
          // assign to a variable of type FileReader a new FileReader object, passing filename to the constructor
          FileReader fr = new FileReader(filename);
          // assign to a variable of type BufferedReader a new BufferedReader, passing the FileReader variable to the constructor
          BufferedReader br = new BufferedReader(fr);

          // assign to a variable of type String line the result of calling the readLine method of your BufferedReader object.
          String line = br.readLine();

          // start a while loop that loops while line isn't null
          int i = 0;
          while(line != null){
            line = line.replaceAll(" ", "");
            String[] res = line.split("");
            int j = 0;
            for (String each : res){
                if (Integer.parseInt(each) != 0){
                    set(i, j, Integer.parseInt(each), true);
                } else{
                    set(i, j, Integer.parseInt(each), false);
                }
                j++;
            }
            i+=1;
            line = br.readLine();
          }
          // call the close method of the BufferedReader
          br.close();

          Board.size = i; 
          
          return true;
        }
        catch(FileNotFoundException ex) {
          System.out.println("Board.read():: unable to open file " + filename );
        }
        catch(IOException ex) {
          System.out.println("Board.read():: error reading file " + filename);
        }
        return false;
    }

    //check if it is a validValue
    public boolean validValue(int row, int col, int value){
        if (value < 1 || value > Board.size){
            return false;
        }
        for (int i = 0; i<Board.size; i++){
            if (value(i, col) == value && i != row){
                return false;
            }

            if (value(row, i) == value && i != col){
                return false;
            }
        }

        int factor = (int) Math.sqrt((double) Board.size);  //checks for any board size
        int r = row - row % factor;
        int c = col - col % factor;
        for (int i = r; i < r+factor; i++){
            for (int j = c; j <  c + factor; j++){
                if (value(i,j) == value && (i!= row || j!=col)){           
                    return false;
                }
            }
        }
        return true;
    }
    
    //check if the solution is valid
    public boolean validSolution(){
        for (int i = 0; i < Board.size; i++){
            for (int j = 0; j < Board.size; j++){
                if (validValue(i, j, value(i,j))==false){
                    return false;
                }
            }
        }
        return true;
    }

    //returns the first valid cell traversing from  from left to right
    public Cell findNextCell1(){
        for (int i = 0; i < Board.size; i++){
            for (int j = 0; j < Board.size; j++){
                if (value(i, j) == 0){
                    for (int val = 1; val <= 9; val++){
                        if (validValue(i, j, val) && (isLocked(i, j) == false)){
                            return new Cell(i, j, val);   //return the cell with a valid val if it is an empty unlocked cell  
                        }
                    }
                    return null;    //return null if no valid value
                }
            }
        }
        return null;     //return null if no empty cells (0)
        
    }

    //returns the cell with the lowest number of valid values
    public Cell findNextCell2(){
        int fewestValidValue = Board.size;
        int rowIndex = 0;
        int colIndex = 0;
        for (int i = 0; i < Board.size; i++){
            for (int j = 0; j < Board.size; j++){
                if (value(i, j) == 0){
                    int count = 0;
                    for (int val = 1; val <= Board.size; val++){
                        if (validValue(i, j, val) && (isLocked(i, j) == false)){
                            count++;
                        }
                    }
                    if (count == 0){
                        return null;
                    } else if (count < fewestValidValue){
                        fewestValidValue = count;
                        rowIndex = i;
                        colIndex = j;
                    }
                }
            }
        }
        for (int val = 1; val <= Board.size; val++){
            if (validValue(rowIndex, colIndex, val) && (isLocked(rowIndex, colIndex) == false)){
                return new Cell(rowIndex, colIndex, val);
            }
        }
        return null;
    }

    //draw method
    public void draw(Graphics g, int scale, JPanel canvas) {
        for(int i = 0; i < Board.size; i++) {
            for(int j = 0; j < Board.size; j++) {
                board[i][j].draw(g, i, j, scale, canvas);
            }
        }
        if(finished){
            if(validSolution()){
                g.setColor(new Color(0, 127, 0));
                g.drawChars("Hurray!".toCharArray(), 0, "Hurray!".length(), scale*3+5, scale*10+10);
            } else {
                g.setColor(new Color(127, 0, 0));
                g.drawChars("No solution!".toCharArray(), 0, "No Solution!".length(), scale*3+5, scale*10+10);
            }
        }
    }

    //testing
    public static void main(String[] args) {
        // //Board board = new Board();
        // // board.read(args[0]);
        Board board = new Board();
        board.read("board2initialvalues.txt");
        System.out.println(board.toString());
        // // System.out.println(board.validValue(1, 1, 3));
        // System.out.println(board.validSolution());
        
        // Board board = new Board(4, 10);
        // System.out.println(board.toString());
        // // System.out.println(board.isPerfectSquare(11));
        
    }
}
