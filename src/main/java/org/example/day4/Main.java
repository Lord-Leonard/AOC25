package org.example.day4;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner;

        try {
            scanner = new Scanner(new File("src/input/input_4"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        List<String[]> rows = new ArrayList<>();
        int MASCounter = 0;

        while (scanner.hasNextLine()) {
            String[] characters = scanner.nextLine().split("");
            rows.add(characters);
        }

        for (int rowIndex = 1; rowIndex < rows.size() - 1; rowIndex++) {

            String[] currentRow = rows.get(rowIndex);
            for (int charIndex = 1; charIndex < currentRow.length - 1; charIndex++) {
                String currentChar = currentRow[charIndex];

                if (!StringUtils.equalsIgnoreCase("A", currentChar)) {
                    continue;
                }

                String[] rowAbove = rows.get(rowIndex - 1);
                String[] rowBelow = rows.get(rowIndex + 1);

                boolean leftToRightIsMAS = (StringUtils.equalsIgnoreCase(rowAbove[charIndex - 1], "M") && StringUtils.equalsIgnoreCase(rowBelow[charIndex + 1], "S"))
                        || (StringUtils.equalsIgnoreCase(rowAbove[charIndex - 1], "S") && StringUtils.equalsIgnoreCase(rowBelow[charIndex + 1], "M"));

                boolean rightToLeftIsMAS = (StringUtils.equalsIgnoreCase(rowAbove[charIndex + 1], "M") && StringUtils.equalsIgnoreCase(rowBelow[charIndex - 1], "S"))
                        || (StringUtils.equalsIgnoreCase(rowAbove[charIndex + 1], "S") && StringUtils.equalsIgnoreCase(rowBelow[charIndex - 1], "M"));

                if (leftToRightIsMAS && rightToLeftIsMAS) {
                    MASCounter++;
                }

            }
        }
        System.out.println(MASCounter);
    }
}