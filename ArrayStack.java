/*
Name: Bishal Khadka
Date: 09/17/2022
Section: B
Project: Solving Sudoku
Filename: ArrayStack.java
Course: CS231

Note: This code was written during lecture 
*/

public class ArrayStack {
	private int top;
	private Cell[] stack;
	private int capacity;

    //construct a stack
	public ArrayStack() {
		this.capacity = 4;
		this.stack = new Cell[this.capacity];
		this.top = -1;
	}

    //push to a stack
	public void push(Cell c) {
        //if it reseachs the maximum capacity
		if (this.top == this.capacity - 1) {
            //doubel the size of the stack
			Cell[] temp = new Cell[this.capacity * 2];
            //copy all the elements from the stack
			for (int i = 0; i < this.capacity; i++) {
				temp[i] = this.stack[i];
			}
			this.stack = temp;  //temp stack is our stack
			this.capacity *= 2;
		}
		this.top++; 
		this.stack[this.top] = c;
	}

    //returns the top element in the stack
	public Cell pop() {
		if (top == 0){
			System.out.println("Empty stack, can't pop");
			return null;
		} else {
			Cell result = this.stack[this.top];
			this.top--;
			return result;
		}
	}

    //returns the value of the top element in the stack
	public Cell peek() {
		Cell result = this.stack[this.top];
		return result;
	}

    //return size of the ArrayStack
    public int size(){
        return this.top + 1;
    }

    //return if the CellStack is empty
    public boolean empty(){
        return (this.top == -1);
    }

    //string representation of the cell
	public String toString() {
		String result = "";
		for (int i = 0; i <= this.top; i++) {
			result += this.stack[i] + ", ";
		}
		return result;
	}
}

