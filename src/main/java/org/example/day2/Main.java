package org.example.day2;

import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner;

        try {
            scanner = new Scanner(new File("src/input/input_2"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        List<Integer[]> safeReports = new ArrayList<>();

        while(scanner.hasNextLine()) {
            Integer[] report = Arrays.stream(scanner.nextLine().split(" "))
                    .map(Integer::parseInt)
                    .toArray(Integer[]::new);

            if (checkReport(report)) {
                safeReports.add(report);
            } else {
                if (checkReportWithDampener(report)) {
                    safeReports.add(report);
                }
            };

        }

        System.out.println(safeReports.size());
    }

    private static boolean checkReportWithDampener(Integer[] report) {
        for (int i = 0; i < report.length; i++) {
            if (checkReport(ArrayUtils.remove(report, i))) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkReport(Integer[] report) {
        Boolean increasing = null;
        for (int i = 0; i < report.length; i++) {
            if (i == 0) continue;

            int previousLevel = report[i-1];
            int currentLevel = report[i];

            if (previousLevel == currentLevel) {
                return false;
            }

            if (increasing == null) {
                increasing = previousLevel < currentLevel;
            }

            if (increasing ^ (currentLevel > previousLevel)) {
                return false;
            }

            if (Math.abs(currentLevel - previousLevel) > 3) {
                return false;
            }
        }
        return true;
    }
}
