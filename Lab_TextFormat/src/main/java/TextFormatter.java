import java.util.ArrayList;
import java.util.List;

public class TextFormatter {

    public static final int INDENT_SIZE = 4;

    public static int findMaxWordLength(String text) {
        String cleanText = text.replace("\r\n", " ").replace("\n", " ");
        String[] words = cleanText.split("\\s+");

        int maxLen = 0;
        for (String word : words) {
            if (word.length() > maxLen) {
                maxLen = word.length();
            }
        }
        return maxLen;
    }

    public static void printFormattedParagraph(String paragraph, int width) {
        String cleanParagraph = paragraph.replace("\r\n", " ").replace("\n", " ").trim();
        String[] words = cleanParagraph.split("\\s+");

        if (words.length == 0) return;

        List<String> currentLine = new ArrayList<>();
        int currentLen = INDENT_SIZE;
        boolean isFirstLine = true;

        for (String word : words) {
            int wordLen = word.length();
            int spaceNeeded = (currentLine.isEmpty()) ? 0 : 1;

            if (currentLen + spaceNeeded + wordLen <= width) {
                currentLine.add(word);
                currentLen += spaceNeeded + wordLen;
            } else {
                System.out.println(formatLine(currentLine, width, isFirstLine, false));

                currentLine.clear();
                currentLine.add(word);
                currentLen = wordLen;
                isFirstLine = false;
            }
        }

        System.out.println(formatLine(currentLine, width, isFirstLine, true));
    }

    private static String formatLine(List<String> words, int width, boolean isFirstLine, boolean isLastLine) {
        StringBuilder sb = new StringBuilder();

        if (isFirstLine) {
            sb.append(" ".repeat(INDENT_SIZE));
        }

        if (isLastLine || words.size() == 1) {
            for (int i = 0; i < words.size(); i++) {
                sb.append(words.get(i));
                if (i < words.size() - 1) sb.append(" ");
            }
            return sb.toString();
        }

        int totalWordsLen = 0;
        for (String w : words) totalWordsLen += w.length();

        int availableSpace = width - totalWordsLen - (isFirstLine ? INDENT_SIZE : 0);
        int gaps = words.size() - 1;

        int spacesPerGap = availableSpace / gaps;
        int extraSpaces = availableSpace % gaps;

        for (int i = 0; i < words.size(); i++) {
            sb.append(words.get(i));
            if (i < gaps) {
                sb.append(" ".repeat(spacesPerGap));
                if (extraSpaces > 0) {
                    sb.append(" ");
                    extraSpaces--;
                }
            }
        }

        return sb.toString();
    }
}