package org.text;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

class TextProcessingTest {
    @Test
    void parseTextTest() {
        String input = "привет мир это тест.";
        Vector<String> result = TextProcessing.parseText(input);

        Vector<String> expected = new Vector<>(Arrays.asList(
                "привет", "мир", "это", "тест"
        ));
        assertEquals(expected, result);
    }

    @Test
    void getLastLetterToWordMapTest() {
        Vector<String> words = new Vector<>(Arrays.asList(
                "манго", "банан", "яблоко", "огурец"
        ));

        Map<Character, String> result = TextProcessing.getLastLetterToWordMap(words);

        Map<Character, String> expected = new HashMap<>();
        expected.put('м', "манго");
        expected.put('б', "банан");
        expected.put('я', "яблоко");
        expected.put('о', "огурец");

        assertEquals(expected, result);
    }

    @Test
    void getAllSequencesTest() {
        Vector<String> words = new Vector<>(Arrays.asList(
                "антон", "егор", "нере", "рома"
        ));

        Map<Character, String> mapping = new HashMap<>();
        mapping.put('а', "антон");
        mapping.put('е', "егор");
        mapping.put('н', "нере");
        mapping.put('р', "рома");

        Vector<Vector<String>> result = TextProcessing.getAllSequences(words, mapping);

        assertEquals(4, result.size());
        assertTrue(result.get(0).containsAll(Arrays.asList("антон", "нере", "егор", "рома")));
    }

    @Test
    void getMaxSequenceTest_AllString() {
        String text = "антон николай йола";
        String result = TextProcessing.getMaxSequence(text);

        assertEquals("антон николай йола", result);
    }


    @Test
    void getMaxSequenceTest_SingleWord() {
        String text = "антон";
        String result = TextProcessing.getMaxSequence(text);

        assertEquals("антон", result);
    }

    @Test
    void getMaxSequenceTest_EmptyString() {
        String text = "";
        String result = TextProcessing.getMaxSequence(text);

        assertEquals("", result);
    }

    @Test
    void getMaxSequenceTest_MultipleWords() {
        String input = "старт рига трактор арфа лоб";
        String result = TextProcessing.getMaxSequence(input);

        // Ожидаемая цепочка: старт тест трактор
        assertEquals("старт трактор рига арфа", result);
    }
}