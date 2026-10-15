package com.project.seduko.util;

import com.project.seduko.exception.SudokuFileNotFoundException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FileStorageUtils {

    private FileStorageUtils() {
    }

    public static List<String> readLines(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new SudokuFileNotFoundException("File path cannot be null or empty.");
        }

        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            throw new SudokuFileNotFoundException("Sudoku file not found at: " + filePath);
        }

        try {
            return Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Error reading puzzle file: " + e.getMessage(), e);
        }
    }

    public static void writeLines(String filePath, List<String> lines) {
        if (filePath == null || filePath.trim().isEmpty() || lines == null) {
            return;
        }

        Path path = Paths.get(filePath);
        Path parentDir = path.getParent();

        if (parentDir != null && !Files.exists(parentDir)) {
            try {
                Files.createDirectories(parentDir);
            } catch (IOException e) {
                System.err.println("Failed to create parent directory for " + filePath + ": " + e.getMessage());
                return;
            }
        }

        try {
            Files.write(
                    path,
                    lines,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE
            );
        } catch (IOException e) {
            System.err.println("Error writing solution file at " + filePath + ": " + e.getMessage());
        }
    }
}