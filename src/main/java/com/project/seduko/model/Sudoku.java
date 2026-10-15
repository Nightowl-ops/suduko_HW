package com.project.seduko.model;

import com.project.seduko.exception.InvalidCharacterException;
import com.project.seduko.util.FileStorageUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Sudoku {
public static final int OUR_MATRIX= 9;
// created a 2D array that tell that each element is holds a SudokuCell
private final SudokuCell[][] board =new SudokuCell[OUR_MATRIX][OUR_MATRIX];

// this is used to create the grid
    // next pass row and column and give it a value of zero at the begining

public Sudoku(){
    for(int r=0 ; r<OUR_MATRIX; r++){
        for (int c=0 ; c<OUR_MATRIX ; c++){
            // this is used so the values wont be null
            board[r][c] = new SudokuCell(r,c,0);
        }
    }
}

public void loadFromFile(String filepath){
    List<String> lines = FileStorageUtils.readLines(filepath);
    int targetRow=0;


/// an exmaple output after this method loadFromFile

///lines.get(0)  = "Puzzle 1"
/// lines.get(1)  = "0  0  0  |  1  5  0  |  0  7  0"
/// lines.get(2)  = "1  0  6  |  0  0  0  |  8  2  0"
/// lines.get(3)  = "3  0  0  |  8  6  0  |  0  4  0"
/// lines.get(4)  = "----------------------------------"
/// lines.get(5)  = "9  0  0  |  4  0  0  |  5  6  7"

    for(String rawLine : lines) {

        String line = rawLine.trim();
        if (line.isEmpty() || line.startsWith("-") || line.toLowerCase().contains("puzzle")) {
            continue;
        }

        if (targetRow >= OUR_MATRIX) {
            break;
        }

        int targetCol = 0;
        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);

            if (Character.isWhitespace(ch) || ch == '|') {
                continue;
            }

            if (Character.isDigit(ch)) {
                if (targetCol < OUR_MATRIX) {
                    int val = Character.getNumericValue(ch);
                    board[targetRow][targetCol] = new SudokuCell(targetRow, targetCol, val);
                    targetCol++;
                }
            } else {
                throw new InvalidCharacterException(
                        "invalid charecter " + ch + "encountered at line :" + rawLine
                );
            }
        }
            if (targetCol > 0) {
                targetRow++;
            }
        }
}


        // Converts the 81 SudokuCell objects back into formatted ASCII text
        public List<String> formatBoardAsLines () {
            List<String> lines = new ArrayList<>();

            for (int r = 0; r < OUR_MATRIX; r++) {
                StringBuilder sb = new StringBuilder();

                for (int c = 0; c < OUR_MATRIX; c++) {
                    sb.append(board[r][c].getValue()).append("  ");

                    // Add vertical divider after column 2 and 5
                    if (c == 2 || c == 5) {
                        sb.append("|  ");
                    }
                }

                lines.add(sb.toString().stripTrailing());

                // Add horizontal divider after row 2 and 5
                if (r == 2 || r == 5) {
                    lines.add("----------------------------------");
                    lines.add("");
                }
            }

            return lines;
        }

    // Prints the board directly to the console
    public void printBoard() {
        for (String line : formatBoardAsLines()) {
            System.out.println(line);
        }
    }

    // Writes the solved puzzle to disk
    public void saveToFile(String filePath) {
        List<String> formattedLines = formatBoardAsLines();
        FileStorageUtils.writeLines(filePath, formattedLines);
    }

    // Bridge method: allows SudokuSolver to inspect a specific cell
    public SudokuCell getCell(int row, int col) {
        return board[row][col];
    }

    // Allows modifying a cell's value directly
    public void setCellValue(int row, int col, int value) {
        board[row][col].setValue(value);
    }

    public SudokuCell[][] getBoard() {
        return board;
    }

    // Sets the entire board from an existing 2D integer array
    public void setBoardFromValues(int[][] values) {
        for (int r = 0; r < OUR_MATRIX; r++) {
            for (int c = 0; c < OUR_MATRIX; c++) {
                board[r][c] = new SudokuCell(r, c, values[r][c]);
            }
        }
    }

    // Load directly from an in-memory list of lines (used by REST API)
    public void loadFromLines(List<String> lines) {
        int targetRow = 0;

        for (String rawLine : lines) {
            String line = rawLine.trim();

            if (line.isEmpty() || line.startsWith("-") || line.toLowerCase().contains("puzzle")) {
                continue;
            }

            if (targetRow >= OUR_MATRIX) {
                break;
            }

            int targetCol = 0;
            for (int i = 0; i < line.length(); i++) {
                char ch = line.charAt(i);

                if (Character.isWhitespace(ch) || ch == '|') {
                    continue;
                }

                if (Character.isDigit(ch)) {
                    if (targetCol < OUR_MATRIX) {
                        int val = Character.getNumericValue(ch);
                        board[targetRow][targetCol] = new SudokuCell(targetRow, targetCol, val);
                        targetCol++;
                    }
                } else {
                    throw new InvalidCharacterException(
                            "Invalid character '" + ch + "' encountered at line: " + rawLine
                    );
                }
            }

            if (targetCol > 0) {
                targetRow++;
            }
        }
    }

    }