package org.text;

public class Main {
    public static void main(String[] args) {
        String text = TextProcessing.getTextFromConsole();
        String maxSequence = TextProcessing.getMaxSequence(text);
        System.out.println(maxSequence);
    }
}
