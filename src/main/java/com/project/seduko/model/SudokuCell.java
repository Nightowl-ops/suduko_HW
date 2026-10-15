package com.project.seduko.model;

// this whole class if for the creation of the cell one piece of info one varbale its value and where its coordinates

public class SudokuCell {

    // before we do anything we need to delcare the basis of an element where it in rows and columns
    //these varables will help in this department

    private final int row;
    private final int columns;
    private int value; //0 means empty , anynumber from 1-9 means its filled


// this will be used to check if the value is empty or not
    public boolean isEmpty(){
        return this.value ==0;
    }

    // this constructor is used when a new cell is born via new SudokuCell( r,c,val)

public SudokuCell(int row, int columns, int value){
        this.row=row;
        this.columns=columns;
        this.value=value;

}
    // when the solver needs to inspect a number or find the box cell belongs to
    public int getColumns() {
        return columns;
    }

    public int getRow() {
        return row;
    }

    public int getValue() {
        return value;
    }
    //when we want to change avalue from zero to either 1-9

    public void setValue(int value){
        this.value=value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }




}