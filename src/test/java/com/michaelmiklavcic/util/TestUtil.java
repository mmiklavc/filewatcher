package com.michaelmiklavcic.util;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class TestUtil {

    private static Charset CHARSET = Charset.forName("UTF-8");

    public static Path writeLines(Path base, String file, String[] content) {
        Path out = base.resolve(file);
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(out, CHARSET))) {
            for (String line : content) {
                writer.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }

    public static void cleanDir(Path path) throws IOException {
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                // try to delete the file anyway, even if its attributes
                // could not be read, since delete-only access is
                // theoretically possible
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                if (exc == null) {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                } else {
                    // directory iteration failed; propagate exception
                    throw exc;
                }
            }
        });
    }

    public static Path makeTempDir() throws IOException {
        return Files.createTempDirectory(null);
    }
}
