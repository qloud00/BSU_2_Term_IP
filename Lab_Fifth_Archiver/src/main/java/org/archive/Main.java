package org.archive;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String sourceFolder = "C:/Work_Folder/Test/";
        String outputZip = "C:/Work_Folder/Test.zip";

        try {
            Archiver.zipFolder(sourceFolder, outputZip);
            System.out.println("Архивация завершена: " + outputZip);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
