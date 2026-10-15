package com.project.seduko.controller;

import com.project.seduko.dto.SudokuResponse;
import com.project.seduko.exception.InvalidCharacterException;
import com.project.seduko.exception.SudokuFileNotFoundException;
import com.project.seduko.service.SudokuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/sudoku")
public class SudokuController {

    private final SudokuService sudokuService;

    public SudokuController(SudokuService sudokuService) {
        this.sudokuService = sudokuService;
    }

    @PostMapping("/solve-file")
    public ResponseEntity<?> solveFile(@RequestParam("file") MultipartFile file) {
        try {
            SudokuResponse response = sudokuService.processAndSolve(file);

            if (response.isSolved()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
            }
        } catch (SudokuFileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (InvalidCharacterException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing file: " + e.getMessage());
        }
    }
}