package com.michaelmiklavcic;

import java.io.IOException;
import java.nio.file.Path;

import com.michaelmiklavcic.util.TestUtil;

public class TestParent {
    private Path tmpDir;

    public void setupTempDir() throws IOException {
        tmpDir = TestUtil.makeTempDir();
    }

    /**
     * Recursive delete
     * 
     * @throws IOException
     */
    public void cleanupTempDir() throws IOException {
        TestUtil.cleanDir(tmpDir);
    }

    /**
     * Writes to file in a tmp dir. Adds newline to each item in content.
     * 
     * @param file
     *            filename
     * @param content
     *            lines to write
     * @return Path of file written
     */
    public Path writeLines(String file, String[] content) {
        return TestUtil.writeLines(tmpDir, file, content);
    }
}
