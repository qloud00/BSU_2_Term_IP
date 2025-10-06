import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        try {

            IndexBinary indexer = new IndexBinary("C:/Test/testFile.bin");

            System.out.println("Всего записей: " + indexer.getRecordCount());
            int recordNumber;
            System.out.println("Введите номер записи которую хотите прочитать : ");
            recordNumber = input.nextInt();
            IndexBinary.Record record = indexer.getRecord(recordNumber);
            String recordStr = new String(String.valueOf(record)).trim();
            System.out.println("Запись " + recordNumber +  ": " + recordStr);

            indexer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
