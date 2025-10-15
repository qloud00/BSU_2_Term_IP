import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String filename = "records.bin";

        int[] ids = {1, 2, 3, 4};
        String[]  names = {"Алиса", "Борис", "Сергей", "Дмитрий"};
        double[] values = {40.5, 45.5, 30.5, 10.2};

        IndexBinary.createBinaryFile(ids, names, values, filename);
        long [] indexArray = IndexBinary.createIndexArray(filename);

        System.out.println("Введите номер записи: ");
        int recordNumber;
        recordNumber = input.nextInt();

        IndexBinary.readByIndex(indexArray, recordNumber - 1, filename);
    }
}
