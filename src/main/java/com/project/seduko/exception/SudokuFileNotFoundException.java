package com.project.seduko.exception;

public class SudokuFileNotFoundException extends RuntimeException {
    public SudokuFileNotFoundException(String message) {
        super(message);
    }
}