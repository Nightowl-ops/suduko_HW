package com.project.seduko.service;

import com.project.seduko.dto.SudokuResponse;
import com.project.seduko.exception.SudokuFileNotFoundException;
import com.project.seduko.model.Sudoku;
import com.project.seduko.solver.SudokuSolver;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SudokuService {

    private final SudokuSolver solver = new SudokuSolver();

    public SudokuResponse processAndSolve(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new SudokuFileNotFoundException("Uploaded file is empty.");
        }

        // 1. Read lines from the uploaded file
        List<String> lines = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8)
        ).lines().collect(Collectors.toList());

        // 2. Initialize and load board
        Sudoku sudoku = new Sudoku();
        sudoku.loadFromLines(lines);

        // 3. Solve the board
        boolean solved = solver.solve(sudoku);

        if (solved) {
            // 4. Derive output file name: e.g. "puzzle1.txt" -> "puzzle1.solution.txt"
            String originalName = file.getOriginalFilename();
            if (originalName == null || originalName.isBlank()) {
                originalName = "puzzle.txt";
            }

            String outputFileName;
            if (originalName.endsWith(".txt")) {
                outputFileName = originalName.replace(".txt", ".solution.txt");
            } else {
                outputFileName = originalName + ".solution.txt";
            }

            // 5. Save the solved puzzle to disk (same mechanism as puzzle1)
            sudoku.saveToFile(outputFileName);

            List<String> formattedSolution = sudoku.formatBoardAsLines();
            return new SudokuResponse(
                    true,
                    "Puzzle solved and saved successfully!",
                    outputFileName,
                    formattedSolution
            );
        } else {
            return new SudokuResponse(false, "No valid solution exists for this puzzle.", null, null);
        }
    }
}