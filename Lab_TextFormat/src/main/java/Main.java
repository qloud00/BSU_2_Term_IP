import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;


public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    try {
        System.out.print("Введите имя файла: ");
        String fileName = scanner.nextLine();

        Path path = Paths.get(fileName);
        if (!Files.exists(path)) {
            System.out.println("Ошибка: Файл не найден!");
            return;
        }

        System.out.print("Введите ширину текста: ");
        int userWidth = scanner.nextInt();

        String content = Files.readString(path, StandardCharsets.UTF_8);

        int maxWordLength = TextFormatter.findMaxWordLength(content);

        if (userWidth < maxWordLength) {
            System.out.println("Ошибка! Введенная ширина (" + userWidth + ") меньше длины самого длинного слова в тексте.");
            System.out.println("Самое длинное слово имеет длину: " + maxWordLength + " символов.");
            System.out.println("Пожалуйста, перезапустите программу и введите ширину не менее " + maxWordLength);
            return;
        }

        if (userWidth < TextFormatter.INDENT_SIZE + 1) {
            System.out.println("Ошибка: Ширина слишком мала даже для отступа красной строки.");
            return;
        }

        String[] paragraphs = content.split("\\n");

        System.out.println("\nРезультат: \n");

        for (String paragraph : paragraphs) {
            TextFormatter.printFormattedParagraph(paragraph, userWidth);
        }

    } catch (IOException e) {
        System.out.println("Ошибка при чтении файла: " + e.getMessage());
    }
}

