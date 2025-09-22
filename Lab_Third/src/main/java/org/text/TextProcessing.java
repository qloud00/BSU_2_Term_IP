package org.text;

import java.util.Scanner;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class TextProcessing {

    public static String getTextFromConsole() {
        StringBuilder textTemp = new StringBuilder();
        String strTemp = "";
        Scanner input = new Scanner(System.in);
        while (true){
            strTemp = input.nextLine();
            if (strTemp.isEmpty()) {
                break;
            }
            textTemp.append(strTemp).append('\n');
        }

        return  textTemp.toString();
    }

    public static int[] extractNumbersFromText(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Text cannot be empty");
        }

        int[] tempNumbers = new int[text.length()];
        int count = 0;

        Pattern pattern = Pattern.compile("\\W\\d+\\W");
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            tempNumbers[count++] = Integer.parseInt(matcher.group().trim());
        }

        return Arrays.copyOf(tempNumbers, count);
    }

    public static int[] sortNumbersAscending(int[] numbers) {
        int[] sorted = numbers.clone();
        Arrays.sort(sorted);
        return sorted;
    }

    public static int[] extractAndSortNumbers(String text) {
        int[] numbers = extractNumbersFromText(text);
        return sortNumbersAscending(numbers);
    }

    public static void displaySortedNumbers(String text) {
        int[] sortedNumbers = extractAndSortNumbers(text);
        System.out.println("Extracted numbers in increasing order:");
        for (int number : sortedNumbers) {
            System.out.print(number + " ");
        }
        System.out.println();
    }
}
