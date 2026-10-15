package com.project.seduko.solver;

import com.project.seduko.model.Sudoku;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class SudokuAnalyzer {

    // Helper method to format a set as {1, 2, 3} instead of [1, 2, 3]
    private static String formatSet(Set<Integer> set) {
        String numbers = set.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
        return "{" + numbers + "}";
    }

    // Inspects and prints Present and Missing values for any coordinate (row, col)
    public static void inspectCellConstraints(Sudoku sudoku, int row, int col) {
        Set<Integer> allDigits = Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9);

        // 1. Column analysis
        Set<Integer> presentCol = new TreeSet<>();
        for (int r = 0; r < Sudoku.OUR_MATRIX; r++) {
            int val = sudoku.getCell(r, col).getValue();
            if (val != 0) {
                presentCol.add(val);
            }
        }
        Set<Integer> missingCol = new TreeSet<>(allDigits);
        missingCol.removeAll(presentCol);

        // 2. Row analysis
        Set<Integer> presentRow = new TreeSet<>();
        for (int c = 0; c < Sudoku.OUR_MATRIX; c++) {
            int val = sudoku.getCell(row, c).getValue();
            if (val != 0) {
                presentRow.add(val);
            }
        }
        Set<Integer> missingRow = new TreeSet<>(allDigits);
        missingRow.removeAll(presentRow);

        // 3. Sub-grid (3x3 Box) analysis
        Set<Integer> presentBox = new TreeSet<>();
        int boxStartRow = row - (row % 3);
        int boxStartCol = col - (col % 3);
        for (int r = boxStartRow; r < boxStartRow + 3; r++) {
            for (int c = boxStartCol; c < boxStartCol + 3; c++) {
                int val = sudoku.getCell(r, c).getValue();
                if (val != 0) {
                    presentBox.add(val);
                }
            }
        }
        Set<Integer> missingBox = new TreeSet<>(allDigits);
        missingBox.removeAll(presentBox);

        // 4. Output matching the exact format from your assignment
        System.out.println("\nConstraints for Cell [" + row + "][" + col + "]:");
        System.out.println("  • Column " + col);
        System.out.println("      ◦ Present values: " + formatSet(presentCol));
        System.out.println("      ◦ Missing values: " + formatSet(missingCol));
        System.out.println("  • Row " + row + ":");
        System.out.println("      ◦ Present values: " + formatSet(presentRow));
        System.out.println("      ◦ Missing values: " + formatSet(missingRow));
        System.out.println("  • Sub-grid:");
        System.out.println("      ◦ Present values: " + formatSet(presentBox));
        System.out.println("      ◦ Missing values: " + formatSet(missingBox) + "\n");
    }
}