package com.project.seduko;

import com.project.seduko.model.Sudoku;
import com.project.seduko.solver.SudokuAnalyzer;
import com.project.seduko.solver.SudokuSolver;

import java.util.Scanner;

public class CreateAndSolveRunner {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Sudoku sudoku = new Sudoku();
        SudokuSolver solver = new SudokuSolver();

        System.out.println("=========================================");
        System.out.println("     CREATE & SOLVE A SUDOKU PUZZLE      ");
        System.out.println("=========================================");

        // 1. Ask for the file name to create
        System.out.print("Enter the name of the puzzle file to CREATE (e.g., myPuzzle.txt): ");
        String puzzleFileName = scanner.nextLine().trim();
        if (!puzzleFileName.endsWith(".txt")) {
            puzzleFileName += ".txt";
        }

        // 2. User inputs the 9 rows of the puzzle
        System.out.println("\nEnter your 9 rows (9 numbers per row, 0 for empty):");
        System.out.println("Tip: You can separate digits with spaces or just type 9 digits (e.g., 0 0 3 0 2 0 6 0 0):");

        int[][] customGrid = new int[Sudoku.OUR_MATRIX][Sudoku.OUR_MATRIX];

        for (int r = 0; r < Sudoku.OUR_MATRIX; r++) {
            while (true) {
                System.out.print("Row " + (r + 1) + ": ");
                String line = scanner.nextLine().trim();

                // Strip spaces, pipes, and tabs so only digits remain
                String cleanDigits = line.replaceAll("[^0-9]", "");

                if (cleanDigits.length() == Sudoku.OUR_MATRIX) {
                    for (int c = 0; c < Sudoku.OUR_MATRIX; c++) {
                        customGrid[r][c] = Character.getNumericValue(cleanDigits.charAt(c));
                    }
                    break; // Row successfully captured
                } else {
                    System.out.println("Error: Must provide exactly 9 digits! You provided "
                            + cleanDigits.length() + ". Please re-enter Row " + (r + 1) + ".");
                }
            }
        }

        // 3. Populate board and save the user's custom puzzle to disk
        sudoku.setBoardFromValues(customGrid);
        sudoku.saveToFile(puzzleFileName);
        System.out.println("\n[✓] Successfully created and saved puzzle to: " + puzzleFileName);

        System.out.println("\n--- Your Initial Board ---");
        sudoku.printBoard();

        // 4. Optional: Inspect constraints on any cell
        System.out.print("\nDo you want to inspect a cell's constraints before solving? (y/n): ");
        String inspectChoice = scanner.nextLine().trim();
        if (inspectChoice.equalsIgnoreCase("y")) {
            System.out.print("Enter row (0-8): ");
            int r = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Enter column (0-8): ");
            int c = Integer.parseInt(scanner.nextLine().trim());

            SudokuAnalyzer.inspectCellConstraints(sudoku, r, c);
        }

        // 5. Solve the board
        System.out.println("Solving your puzzle...");
        boolean solved = solver.solve(sudoku);

        if (solved) {
            System.out.println("\n--- Solved Board ---");
            sudoku.printBoard();

            // 6. Ask for the solution file name to create
            System.out.print("\nEnter the name of the SOLUTION file to create (e.g., myPuzzle_solution.txt): ");
            String solutionFileName = scanner.nextLine().trim();
            if (!solutionFileName.endsWith(".txt")) {
                solutionFileName += ".txt";
            }

            sudoku.saveToFile(solutionFileName);
            System.out.println("\n[right] Solution successfully saved to: " + solutionFileName);
        } else {
            System.out.println("\n[X] No valid solution exists for this board.");
        }

        scanner.close();
    }
}