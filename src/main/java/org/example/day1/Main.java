package org.example.day1;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner;
        try {
            scanner = new Scanner(new File("src/input/input_1"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String[] parts = scanner.nextLine().split(" {3}");

            left.add(Integer.parseInt(parts[0]));
            right.add(Integer.parseInt(parts[1]));
        }

        int sum = 0;
        for (int leftNum : left) {
            int frequencyInRightList = Collections.frequency(right, leftNum);
            sum += frequencyInRightList * leftNum;
        }

        System.out.println(sum);
    }
}
