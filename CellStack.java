/*
Name: Bishal Khadka
Date: 09/17/2022
Section: B
Project: Solving Sudoku
Filename: CellStack.java
Course: CS231
*/

public class CellStack {

    private class Node{  //creating Node class
        Cell cell;
        Node next;

        public Node(Cell cell){
            this.cell = cell;
            this.next = null;  //set next to null
        }
    }

    private Node head;  //head node
    private int size; //size of the stack

    //default constructor
    public CellStack(){  
        this.head = null;
        size = 0;
    }

    //push to the CellStack
    public void push(Cell c){
        Node newNode = new Node(c);
        if (size == 0){
            this.head = newNode;
            this.head.next = null;
        } else{
            newNode.next = this.head;
            this.head = newNode;
        }
        size ++;
    }

    //get the value of the head from the CellStack
    public Cell peek(){
        return this.head.cell;
    }

    //pop from the CellStack
    public Cell pop(){
        Cell res = null;
        if (size == 0){
            System.out.println("Empty stack, cannot pop");
            return res;
        } if (size == 1){
            res = this.head.cell;
            this.head = null;
        } else{
            res = this.head.cell;
            this.head = this.head.next;
        }
        size --;
        return res;
    }

    //return size of the CellStack
    public int size(){
        return this.size;
    }

    //return if the CellStack is empty
    public boolean empty(){
        return (this.size == 0);
    }
}