package org.matrix;

import java.util.Arrays;

public class MatrixProcessing {

    public static int[][] processMatrix(int[][] matrix) {
        int[][] result = copyMatrix(matrix);

        int maxRowIndex = findRowWithMaxElement(result);
        int minRowIndex = findRowWithMinElement(result);

        if (maxRowIndex != minRowIndex) {
            swapRows(result, maxRowIndex, minRowIndex);
        }

        return result;
    }

    static int findRowWithMaxElement(int[][] matrix) {
        int maxValue = matrix[0][0];
        int maxRowIndex = 0;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] > maxValue) {
                    maxValue = matrix[i][j];
                    maxRowIndex = i;
                }
            }
        }

        return maxRowIndex;
    }

    static int findRowWithMinElement(int[][] matrix) {
        int minValue = matrix[0][0];
        int minRowIndex = 0;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] < minValue) {
                    minValue = matrix[i][j];
                    minRowIndex = i;
                }
            }
        }

        return minRowIndex;
    }

    private static void swapRows(int[][] matrix, int row1, int row2) {
        int[] temp = matrix[row1];
        matrix[row1] = matrix[row2];
        matrix[row2] = temp;
    }

    public static void printRowsWithZeroOnDiag(int[][] matrix) {
        for (int i = 0; i < matrix.length && i < matrix[i].length; i++) {
            if (matrix[i][i] == 0) {
                int maxInRow = findMaxInRow(matrix[i]);
                System.out.println("Строка " + i + ": максимальный элемент = " + maxInRow);
            }
        }
    }

    static int findMaxInRow(int[] row) {
        int max = row[0];
        for (int value : row) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    static int[][] copyMatrix(int[][] matrix) {
        int[][] copy = new int[matrix.length][];
        for (int i = 0; i < matrix.length; i++) {
            copy[i] = Arrays.copyOf(matrix[i], matrix[i].length);
        }
        return copy;
    }

    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
}
