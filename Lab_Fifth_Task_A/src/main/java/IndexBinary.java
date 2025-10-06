import java.io.IOException;
import java.io.RandomAccessFile;

public class IndexBinary {
    private final RandomAccessFile file;
    private final int RECORD_SIZE = 24;

    public IndexBinary(String filename) throws IOException {
        file = new RandomAccessFile(filename, "r");
    }

    public int getRecordCount() throws IOException {
        return (int)(file.length() / RECORD_SIZE);
    }

    public Record getRecord(int recordNumber) throws IOException {
        if (recordNumber < 0 || recordNumber >= getRecordCount()) {
            throw new IndexOutOfBoundsException("Нет такой записи");
        }

        long offset = recordNumber * RECORD_SIZE;
        file.seek(offset);

        int intValue = file.readInt();

        byte[] charBytes = new byte[10];
        file.readFully(charBytes);
        String strValue = new String(charBytes).trim();

        file.skipBytes(2);

        double doubleValue = file.readDouble();


        return new Record(intValue, strValue, doubleValue);
    }

    public void close() throws IOException {
        file.close();
    }

    public static class Record {
        public int intValue;
        public String strValue;
        public double doubleValue;

        public Record(int i, String s, double d) {
            intValue = i;
            strValue = s;
            doubleValue = d;
        }

        @Override
        public String toString() {
            return "Record{" +
                    "intValue=" + intValue +
                    ", strValue='" + strValue + '\'' +
                    ", doubleValue=" + doubleValue +
                    '}';
        }
    }

}
