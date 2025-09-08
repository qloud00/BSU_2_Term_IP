import java.util.InputMismatchException;
import java.util.Scanner;

public class Calculator {
    static int k;
    static  double x;
    public static void getInputData() {
        Scanner input = new Scanner(System.in);
        while(true) {
            try {
                System.out.print("Введите значение k: ");
                k = input.nextInt();
                if (k <= 0) {
                    throw new IllegalArgumentException();
                }
                System.out.print("Введите значение x: ");
                x = input.nextDouble();
                break;
            }
            catch (IllegalArgumentException error) {
                System.out.println("Введенное вами число k не является натуральным.");
            }
            catch (InputMismatchException error) {
                System.out.println("Введите число корректно.");
            }
        }
    }

    public static double getAccuracy(int k) {
        double accuracy = 1;
        for (int i = 0; i < k; i++) {
            accuracy /= 10;
        }
        return accuracy;
    }

    public static double abs(double num) {
        if (num < 0) {
            return -num;
        }
        return num;
    }

    public static double calculateExpX () {
        getInputData();
        double accuracy = getAccuracy(k);
        double term = 1;
        double result = 1;
        int n = 1;
        for ( ; ;n++) {
            term = (term * x) / n;
            if (abs(term) > Double.MAX_VALUE) {
                System.out.println("Слагаемое вышло за пределы размерности типа данных!");
            }
            if (term < accuracy) {
                break;
            }
            result += term;
        }
        return result;
    }

    public static void startCalculator (){
        double myExpX;
        double javaExpX;
        myExpX = calculateExpX();
        String firstResult = String.format("%.10f", myExpX);
        System.out.println("Значение exp^x полученное вычислением ряда Тейлора: " + firstResult);
        javaExpX = Math.exp(x);
        String secondResult = String.format("%.10f", javaExpX);
        System.out.print("Значение exp^x полученное функцией библиотеки Java: " + secondResult);
    }
}
