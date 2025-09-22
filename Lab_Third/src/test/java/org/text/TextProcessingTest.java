package org.text;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class TextProcessingTest {
    @Test
    void testExtractNumbersFromTextWithMultipleNumbers() {
        String text = "Numbers: 1, 2, 3, 4";
        int[] result = TextProcessing.extractNumbersFromText(text);

        int[] expected = {1, 2, 3, 4};
        assertArrayEquals(expected, result);
    }

    @Test
    void testExtractNumbersFromTextWithNegativeNumbers() {
        String text = "Numbers: -10, 20, \n -30, 40";
        int[] result = TextProcessing.extractNumbersFromText(text);

        int[] expected = {-10, 20, -30, 40};
        assertArrayEquals(expected, result);
    }

    @Test
    void testExtractNumbersFromTextWithNoNumbers() {
        String text = "Text \n without numbers";
        int[] result = TextProcessing.extractNumbersFromText(text);

        assertEquals(0, result.length);
    }

    @Test
    void testExtractNumbersFromTextWithEmptyString() {
        String text = "";
        int[] result = TextProcessing.extractNumbersFromText(text);

        assertEquals(0, result.length);
    }

    @Test
    void testSortNumbersAscending() {
        int[] numbers = {5, -2, 8, 1, -3};
        int[] result = TextProcessing.sortNumbersAscending(numbers);

        int[] expected = {-3, -2, 1, 5, 8};
        assertArrayEquals(expected, result);
    }

}