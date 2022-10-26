/*
Name: Bishal Khadka
Date: 09/17/2022
Section: B
Project: Solving Sudoku
Filename: Sudoku.java
Course: CS231

Instruction : Change the findNextCell2() to findNextCell1() in the solve method to visuzalise a different approach in finding cell to solve sudoku 
            : Enlarge the sudoku board when displayed
            : In the solve method, swith the stack type from Node based to Array Based by commmenting and uncommenting the other, no need to worry about any other thing
*/

//used libraries
import java.lang.Thread;

public class Sudoku {

    //fields
    private Board board;  
    private LandscapeDisplay ld;

    //constructor
    public Sudoku(){
        this.board = new Board();
        ld = new LandscapeDisplay(this.board);
    }

    //constructor with board size and initial values
    public Sudoku(int size, int numToBeLocked){
        board = new Board(size, numToBeLocked);
        ld = new LandscapeDisplay(board);
    }

    //solve method
    public boolean solve(int delay) throws InterruptedException{
        // ArrayStack stack = new ArrayStack();
        CellStack stack = new CellStack();
        while (stack.size() < (81 - board.numLocked())){
            if (delay > 0)
                Thread.sleep(delay);
            if (ld != null)
                ld.repaint();
            Cell nextCell = board.findNextCell1();       //finds empty cell and returns cell associated with a valid value
            //this statement will run until none of the values from (1-9) is a valid value in that specific last run
            if (nextCell != null){   // meaning the value is a valid value and it is not locked
                stack.push(nextCell);
                board.set(nextCell.getRow(), nextCell.getCol(), nextCell.getValue());
            } else{         // nextCell is null; meaning the value is not a valid value or it is locked
                boolean noValidValue = true;       //initializing a boolean variable
                while ((stack.empty() != true) && (this.board.validSolution() == false) && noValidValue){  //stack has a cell; solution is not obtained; stuck
                    Cell poppedCell = stack.pop(); //pop from the stack because the last value used in the last cell won't allow us to go further as at least one duplicate value will be found
                    for (int i = poppedCell.getValue() + 1; i <= 9; i++){   //try the next value 
                        if (board.validValue(poppedCell.getRow(), poppedCell.getCol(), i)){
                            poppedCell.setValue(i);
                            stack.push(poppedCell);
                            board.set(poppedCell.getRow(), poppedCell.getCol(), i);
                            noValidValue = false;
                            break;
                        }
                    }
                    if (noValidValue){   //if popping out one cell and replacing the cell with another value (1-9) still does not work then you set the value to 0
                        poppedCell.setValue(0);
                        board.set(poppedCell.getRow(), poppedCell.getCol(), poppedCell.getValue());
                    } 
                    if (stack.size() == 0){  //meaning that no solution was found
                        return false;
                    }
                }
            }
        }
        board.finished = true;
        return true;
    }

    public static void main(String[] args) throws InterruptedException {
        Sudoku sudoku = new Sudoku(9, 11);
        System.out.println(sudoku.board);
        sudoku.solve(100);
        System.out.println(sudoku.board);
        // sudoku.ld.repaint();
        // Sudoku sudoku1 = new Sudoku(9, 5);
        // sudoku1.solve(300);


    }
}