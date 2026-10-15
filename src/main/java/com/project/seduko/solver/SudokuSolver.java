package com.project.seduko.solver;

import com.project.seduko.model.Sudoku;
import com.project.seduko.model.SudokuCell;

public class SudokuSolver {

    // Master check: returns true only if 'number' passes row, col, and box checks
    public boolean isValid(Sudoku sudoku, int row, int col, int number) {
        return !isNumberInRow(sudoku, row, number)
                && !isNumberInCol(sudoku, col, number)
                && !isNumberInBox(sudoku, row, col, number);
    }

    // Scans all 9 columns in a single horizontal row
    private boolean isNumberInRow(Sudoku sudoku, int row, int number) {
        for (int c = 0; c < Sudoku.OUR_MATRIX; c++) {
            if (sudoku.getCell(row, c).getValue() == number) {
                return true; // Match found
            }
        }
        return false;
    }

    // Scans all 9 rows down a single vertical column
    private boolean isNumberInCol(Sudoku sudoku, int col, int number) {
        for (int r = 0; r < Sudoku.OUR_MATRIX; r++) {
            if (sudoku.getCell(r, col).getValue() == number) {
                return true; // Match found
            }
        }
        return false;
    }

    // Scans the 9 cells inside the 3x3 subgrid
    private boolean isNumberInBox(Sudoku sudoku, int row, int col, int number) {
        int boxStartRow = row - (row % 3);
        int boxStartCol = col - (col % 3);

        for (int r = boxStartRow; r < boxStartRow + 3; r++) {
            for (int c = boxStartCol; c < boxStartCol + 3; c++) {
                if (sudoku.getCell(r, c).getValue() == number) {
                    return true; // Match found
                }
            }
        }
        return false;
    }

    // Solves the board using recursive backtracking
    public boolean solve(Sudoku sudoku) {
        for (int row = 0; row < Sudoku.OUR_MATRIX; row++) {
            for (int col = 0; col < Sudoku.OUR_MATRIX; col++) {
                SudokuCell cell = sudoku.getCell(row, col);

                // Find the first blank spot
                if (cell.isEmpty()) {

                    // Try every candidate digit from 1 to 9
                    for (int numberToTry = 1; numberToTry <= 9; numberToTry++) {

                        if (isValid(sudoku, row, col, numberToTry)) {
                            cell.setValue(numberToTry);

                            // Recurse down this branch
                            if (solve(sudoku)) {
                                return true;
                            }

                            // If it leads to a dead end, backtrack
                            cell.setValue(0);
                        }
                    }

                    // None of 1-9 worked, trigger backtracking in earlier calls
                    return false;
                }
            }
        }

        // Base case: No empty cells left, puzzle is solved
        return true;
    }
}