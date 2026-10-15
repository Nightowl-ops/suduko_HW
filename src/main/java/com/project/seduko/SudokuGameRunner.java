package com.project.seduko;

import com.project.seduko.model.Sudoku;
import com.project.seduko.solver.SudokuSolver;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@Component
public class SudokuGameRunner implements CommandLineRunner {

    @Override
    public void run(String... args) {
        String inputFilePath = "puzzle1.txt";
        String outputFilePath = "puzzle1.solution.txt";

        try {
            Sudoku sudoku = new Sudoku();

            System.out.println("Loading " + inputFilePath + "...");
            sudoku.loadFromFile(inputFilePath);

            System.out.println("\n\t\tPuzzle 1");
            sudoku.printBoard();

            SudokuSolver solver = new SudokuSolver();

            if (solver.solve(sudoku)) {
                System.out.println("\n\t\tPuzzle 1 Solved");
                sudoku.printBoard();

                sudoku.saveToFile(outputFilePath);
                System.out.println("\nSolution saved to " + outputFilePath);
            } else {
                System.out.println("\nNo valid solution exists for this puzzle.");
            }

        } catch (Exception e) {
            System.err.println("Execution Error: " + e.getMessage());
        }
    }
}