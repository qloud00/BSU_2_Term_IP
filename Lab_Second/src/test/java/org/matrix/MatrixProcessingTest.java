package org.matrix;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class MatrixProcessingTest {
    ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterAll
    static void reset() {
        System.setOut(System.out);
    }

    @Test
    void testProcessMatrix() {
        int[][] input = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        int[][] expected = {
                {7, 8, 9},
                {4, 5, 6},
                {1, 2, 3}
        };

        int[][] result = MatrixProcessing.processMatrix(input);
        assertArrayEquals(expected, result);
    }

    @Test
    void testProcessMatrixWithMinMaxInSameRow() {
        int[][] input = {
                {1, 10, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        int[][] expected = {
                {1, 10, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        int[][] result = MatrixProcessing.processMatrix(input);
        assertArrayEquals(expected, result);
    }

    @Test
    void testProcessMatrixWithNegativeNumbers() {
        int[][] input = {
                {-5, -2, -1},
                {0, 3, 6},
                {7, 8, 9}
        };

        int[][] expected = {
                {7, 8, 9},
                {0, 3, 6},
                {-5, -2, -1}
        };

        int[][] result = MatrixProcessing.processMatrix(input);
        assertArrayEquals(expected, result);
    }

    @Test
    void testPrintRowsWithZeroOnDiag() {
        int[][] matrix = {
                {0, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };

        MatrixProcessing.printRowsWithZeroOnDiag(matrix);

        String output = outputStream.toString();
        assertTrue(output.contains("Строка 0: максимальный элемент = 3"));
        assertTrue(output.contains("Строка 2: максимальный элемент = 8"));
        assertFalse(output.contains("Строка 1"));
    }

    @Test
    void testPrintRowsWithZeroOnDiagonalNoMatches() {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        MatrixProcessing.printRowsWithZeroOnDiag(matrix);

        String output = outputStream.toString();
        assertEquals("", output.trim());
    }

    @Test
    void testFindMaxInRow() {
        int[] row = {3, 1, 4, 1, 5, 9, 2};
        assertEquals(9, MatrixProcessing.findMaxInRow(row));
    }

    @Test
    void testFindRowWithMaxElement() {
        int[][] matrix = {
                {1, 2, 3},
                {4, 15, 6},
                {7, 8, 9}
        };

        assertEquals(1, MatrixProcessing.findRowWithMaxElement(matrix));
    }

    @Test
    void testFindRowWithMinElement() {
        int[][] matrix = {
                {1, 2, 3},
                {4, -5, 6},
                {7, 8, 9}
        };

        assertEquals(1, MatrixProcessing.findRowWithMinElement(matrix));
    }
}