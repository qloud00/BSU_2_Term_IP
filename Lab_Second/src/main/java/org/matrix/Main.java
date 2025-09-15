package org.matrix;

import java.util.Scanner;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Введите число строк матрицы: ");
        int rows = input.nextInt();
        System.out.println("Введите число столбцов матрицы: ");
        int columns = input.nextInt();

        int[][] matrix = new int[rows][columns];

        System.out.println("\nСоздана следующая матрица:\n");

        Random random = new Random();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = random.nextInt() % 50;
                System.out.print(matrix[i][j] + " ");
            }
            System.out.print('\n');
        }

        MatrixProcessing.printRowsWithZeroOnDiag(matrix);

        System.out.println("\nОбработанная матрица: \n");
        MatrixProcessing.printMatrix(MatrixProcessing.processMatrix(matrix));

    }
}