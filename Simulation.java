/*
Name: Bishal Khadka
Date: 09/17/2022
Section: B
Project: Solving Sudoku
Filename: Simulation.java
Course: CS231

Instructions to use: -> Sudoko(boardsize, initialValues)
                    -> Change the size of sudoku to get different sized board and make sure the size is a perfect square
                    -> This will create a new window for each sudoku, chaning the delay time in sudoku.solve() might be helpful
                    -> In the Sudoku class, change the findNextCell2() to findNextCell1() in the solve method to visuzalise a different approach
                    -> : Enlarge the sudoku board when displayed
                    -> If you want to swith between ArrayBased and NodeBased Stack, do so in the solve method by commenting one and uncommenting the other
*/

public class Simulation {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 200; i++){
            Sudoku sudoku = new Sudoku(9,13);
            long start = System.nanoTime();
            sudoku.solve(0);
            long end = System.nanoTime();
            long totalTimeTaken = (end - start) / 1000;     //in microseconds
            System.out.println(i + "," + totalTimeTaken);
        }
    }
}
