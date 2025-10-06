package org.archive;

import java.io.*;
import java.nio.file.*;
import java.util.zip.*;

public class Archiver {
    public static void zipFolder(String sourceDirPath, String zipFilePath) throws IOException {
        Path sourceDir = Paths.get(sourceDirPath);

        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFilePath))) {
            Files.walk(sourceDir)
                    .filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        String zipEntryName = sourceDir.relativize(path).toString();
                        try (InputStream is = Files.newInputStream(path)) {
                            ZipEntry zipEntry = new ZipEntry(zipEntryName);
                            zos.putNextEntry(zipEntry);

                            byte[] buffer = new byte[4096];
                            int length;
                            while ((length = is.read(buffer)) > 0) {
                                zos.write(buffer, 0, length);
                            }

                            zos.closeEntry();
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    });
        }
    }
}
