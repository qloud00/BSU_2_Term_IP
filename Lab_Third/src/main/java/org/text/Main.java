package org.text;

public class Main {
    public static void main(String[] args){
        try {
            String text = TextProcessing.getTextFromConsole();
            TextProcessing.displaySortedNumbers(text);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
