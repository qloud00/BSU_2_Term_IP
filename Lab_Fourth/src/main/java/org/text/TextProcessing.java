package org.text;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.lingala.zip4j.core.ZipFile;

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

    public static String getMaxSequence(String text) {
        String maxSequence = "";
        Vector<String> allWords = new Vector<>();
        if (text.isEmpty()) {
            return maxSequence;
        }
        allWords = parseText(text);
        Map<Character, String> lastLetterToWordMap = new HashMap<Character, String>();
        lastLetterToWordMap = getLastLetterToWordMap(allWords);
        Vector<Vector<String>> sequencesVector = new Vector<>();
        sequencesVector = getAllSequences(allWords, lastLetterToWordMap);
        int maxSize = sequencesVector.elementAt(0).size();
        int maxIndex = 0;
        for (int i = 0; i < sequencesVector.size(); i++) {
            if (sequencesVector.get(i).size() > maxSize) {
                maxSize = sequencesVector.get(i).size();
                maxIndex = i;
            }
        }
        for (String word : sequencesVector.elementAt(maxIndex)) {
            maxSequence = maxSequence + word + " ";
        }
        maxSequence = maxSequence.trim();
        return maxSequence;
    }

    public static Vector<String> parseText(String text) {
        Vector<String> allWords = new Vector<>();
        Pattern pattern = Pattern.compile("[A-Za-zА-Яа-яЁё]+");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String word = matcher.group();
            allWords.add(word.toLowerCase(Locale.ROOT));
        }
        return allWords;
    }

    public static Vector<Vector<String>> getAllSequences(Vector<String> allWords, Map<Character, String> lastLetterToWordMap) {
        Vector<Vector<String>> sequencesVector = new Vector<>();
        for (String word : allWords) {
            Vector<String> sequence = new Vector<>();
            Character lastLetter = word.charAt(word.length() - 1);
            sequence.add(word);
            while (lastLetterToWordMap.containsKey(lastLetter)) {
                String nextWord = lastLetterToWordMap.get(lastLetter);
                if (sequence.contains(nextWord)) break;
                sequence.add(nextWord);
                lastLetter = nextWord.charAt(nextWord.length() - 1);
            }
            sequencesVector.add(sequence);
        }
        return  sequencesVector;
    }

    public static Map<Character, String> getLastLetterToWordMap(Vector<String> allWords) {
        Map<Character, String> tempLastLetterToWordMap = new HashMap<Character, String>();
        for (int i = 0; i < allWords.size(); i++) {
            String word = allWords.elementAt(i);
            char FirsLatter = word.charAt(0);
            tempLastLetterToWordMap.put(FirsLatter, word);
        }
        return tempLastLetterToWordMap;
    }
}
