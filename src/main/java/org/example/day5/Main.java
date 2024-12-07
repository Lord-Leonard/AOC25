package org.example.day5;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static List<Integer[]> rulesToOrder = new ArrayList<>();
    public static LinkedList<Integer> orderingOrder = new LinkedList<>();
    public static List<Integer[]> updates = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner;

        try {
            scanner = new Scanner(new File("src/input/input_5"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        boolean scanningConditions = true;
        while (scanner.hasNextLine()) {
            String nextLine = scanner.nextLine();

            if (nextLine.equalsIgnoreCase(StringUtils.EMPTY)) {
                scanningConditions = false;
                continue;
            }

            if (scanningConditions) {
                Integer[] orderingRule = Arrays.stream(nextLine.split("\\|"))
                        .map(Integer::parseInt)
                        .toArray(Integer[]::new);
                rulesToOrder.add(orderingRule);
            } else {
                Integer[] update = Arrays.stream(nextLine.split(","))
                        .map(Integer::parseInt)
                        .toArray(Integer[]::new);
                updates.add(update);
            }
        }

        // ----
        Set<Integer> mySet = new HashSet<>();
        rulesToOrder.stream().flatMap(Arrays::stream).forEachOrdered(mySet::add);
        System.out.println(mySet.size());

        Integer initialPageHead = rulesToOrder.getFirst()[0];
        goDeeperTrailing(initialPageHead);
        goUpThere(initialPageHead);

        int sum = updates.stream()
                .peek(update -> System.out.println(Arrays.toString(update)))
                .map(update -> Arrays.stream(update)
                        .map(page -> orderingOrder.indexOf(page))
                        .sorted()
                        .map((pageIndice) -> orderingOrder.get(pageIndice))
                        .toArray(Integer[]::new))
                .mapToInt(sortedUpdate -> sortedUpdate[sortedUpdate.length / 2])
                .peek(System.out::println)
                .sum();

        System.out.println(sum);
    }



    private static void goUpThere(Integer page) {
        goUpThere(page, 0);
    }

    private static void goUpThere(Integer page, int indexOfTailing) {
        Integer[] leading = getLeading(page, rulesToOrder);

        boolean pageInList = orderingOrder.stream().anyMatch(orderedPage -> orderedPage.intValue() == page.intValue());
        if (!pageInList) {
            orderingOrder.add(indexOfTailing, page);
        }

        for (Integer leadingPage : leading) {
            boolean pageAllreadySorted = orderingOrder.stream().anyMatch(orderedPage -> orderedPage.intValue() == leadingPage.intValue());
            if (pageAllreadySorted) {
                continue;
            }

            int indexOfInsertedPage = orderingOrder.indexOf(page);


            goUpThere(leadingPage, indexOfInsertedPage);
        }
    }

    private static void goDeeperTrailing(Integer page) {
        goDeeperTrailing(page, -1);
    }

    private static void goDeeperTrailing(Integer page, int indexOfParent) {
        Integer[] trailing = getTrailing(page, rulesToOrder);

        orderingOrder.add(indexOfParent + 1, page);

        for (Integer trailingPage : trailing) {
            boolean pageAllreadySorted = orderingOrder.stream().anyMatch(orderedPage -> orderedPage.intValue() == trailingPage.intValue());
            if (pageAllreadySorted) {
                continue;
            }

            int indexOfInsertedPage = orderingOrder.indexOf(page);

            goDeeperTrailing(trailingPage, indexOfInsertedPage);
        }
    }

    static Integer[] getLeading(int page, List<Integer[]> rulesToSort) {
        return rulesToSort.stream()
                .filter(rule -> rule[1] == page)
                .map(rule -> rule[0])
                .toArray(Integer[]::new);
    }

    static Integer[] getTrailing(int page, List<Integer[]> rulesToSort) {
        return rulesToSort.stream()
                .filter(rule -> rule[0] == page)
                .map(rule -> rule[1])
                .toArray(Integer[]::new);
    }
}

class Page {
    int page;
    Integer[] leading;
    Integer[] trailing;

    public Page(int page, Integer[] leading, Integer[] training) {
        this.page = page;
        this.leading = leading;
        this.trailing = training;
    }
}

