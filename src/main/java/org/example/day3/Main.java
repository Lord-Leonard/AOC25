package org.example.day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        String content;

        try {
            content = Files.readString(Paths.get("src/input/input_3"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<String> instructions = new ArrayList<>();

        Matcher matcher = Pattern.compile("do\\(\\)|don't\\(\\)|mul\\([0-9]{1,3},[0-9]{1,3}\\)", Pattern.MULTILINE)
                .matcher(content);

        while (matcher.find()) {
            instructions.add(matcher.group());
        }

        Pattern patternNumber = Pattern.compile("[0-9]{1,3}");
        Pattern patternDo = Pattern.compile("do\\(\\)");
        Pattern patternDont = Pattern.compile("don't\\(\\)");

        List<Integer> products = new ArrayList<>();
        boolean doMultiplication = true;
        for (String instruction : instructions) {
            if (patternDo.matcher(instruction).find()) {
                doMultiplication = true;
                continue;
            }

            if (patternDont.matcher(instruction).find()) {
                doMultiplication = false;
                continue;
            }

            if (!doMultiplication) {
                continue;
            }

            Integer product = patternNumber
                    .matcher(instruction)
                    .results()
                    .map(MatchResult::group)
                    .map(Integer::parseInt)
                    .reduce(1, Math::multiplyExact);

            products.add(product);
        }

        System.out.println(products.stream().reduce(0, Integer::sum));

    }
}
