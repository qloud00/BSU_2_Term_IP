import java.io.*;

public class IndexBinary {
    private final static int RECORD_SIZE = 4 + 10 * 2 + 8; // int + char[10] + double = 32 байта

    public static void createBinaryFile(int[] ids, String[] names, double[] values, String filename) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(filename))) {
            for (int i = 0; i < ids.length; i++) {
                dos.writeInt(ids[i]);
                writeFixedString(dos, names[i], 10); // char[10]
                dos.writeDouble(values[i]);
            }
            System.out.println("Бинарный файл успешно создан: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static  long[] createIndexArray(String filename) {
        try (RandomAccessFile raf = new RandomAccessFile(filename, "r")) {
            long fileLength = raf.length();
            int recordCount = (int) (fileLength / RECORD_SIZE);
            long[] indexArray = new long[recordCount];

            for (int i = 0; i < recordCount; i++) {
                indexArray[i] = i * RECORD_SIZE;
            }

            System.out.println("Индексный массив создан (" + recordCount + " записей)");
            return indexArray;

        } catch (IOException e) {
            e.printStackTrace();
            return new long[0];
        }
    }

    public static void readByIndex(long[] indexArray, int recordIndex, String filename) {

        try (RandomAccessFile raf = new RandomAccessFile(filename, "r")) {
            raf.seek(indexArray[recordIndex]);

            int id = raf.readInt();
            String name = readFixedString(raf, 10);
            double value = raf.readDouble();

            System.out.println("Запись №" + (recordIndex + 1));
            System.out.println("ID: " + id + ", Имя: " + name + ", Зарплата: " + value);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeFixedString(DataOutputStream dos, String s, int length) throws IOException {
        StringBuilder sb = new StringBuilder(s);
        if (sb.length() > length) sb.setLength(length);
        else while (sb.length() < length) sb.append(' ');
        dos.writeChars(sb.toString());
    }

    private static String readFixedString(RandomAccessFile raf, int length) throws IOException {
        char[] chars = new char[length];
        for (int i = 0; i < length; i++) {
            chars[i] = raf.readChar();
        }
        return new String(chars).trim();
    }

}



