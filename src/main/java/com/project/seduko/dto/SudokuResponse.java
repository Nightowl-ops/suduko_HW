package com.project.seduko.dto;

import java.util.List;

public class SudokuResponse {
    private boolean solved;
    private String message;
    private String savedFilePath;
    private List<String> solutionAscii;

    public SudokuResponse(boolean solved, String message, String savedFilePath, List<String> solutionAscii) {
        this.solved = solved;
        this.message = message;
        this.savedFilePath = savedFilePath;
        this.solutionAscii = solutionAscii;
    }

    public boolean isSolved() {
        return solved;
    }

    public String getMessage() {
        return message;
    }

    public String getSavedFilePath() {
        return savedFilePath;
    }

    public List<String> getSolutionAscii() {
        return solutionAscii;
    }
}