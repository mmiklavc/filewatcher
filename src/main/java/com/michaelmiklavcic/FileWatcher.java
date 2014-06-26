package com.michaelmiklavcic;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.WatchEvent.Kind;

// Simple class to watch directory events.
class DirWatcher implements Runnable {

    private Path path;

    public DirWatcher(Path path) {
        this.path = path;
    }

    // print the events and the affected file
    private void printEvent(WatchEvent<?> event) {
        Kind<?> kind = event.kind();
        if (kind.equals(StandardWatchEventKinds.ENTRY_CREATE)) {
            Path pathCreated = (Path) event.context();
            System.out.println("Entry created:" + pathCreated);
        } else if (kind.equals(StandardWatchEventKinds.ENTRY_DELETE)) {
            Path pathDeleted = (Path) event.context();
            System.out.println("Entry deleted:" + pathDeleted);
        } else if (kind.equals(StandardWatchEventKinds.ENTRY_MODIFY)) {
            Path pathModified = (Path) event.context();
            System.out.println("Entry modified:" + pathModified);
        }
    }

    @Override
    public void run() {
        try {
            WatchService watchService = path.getFileSystem()
                                            .newWatchService();
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY,
                          StandardWatchEventKinds.ENTRY_DELETE);

            // loop forever to watch directory
            while (true) {
                WatchKey watchKey;
                watchKey = watchService.take(); // this call is blocking until
                                                // events are present

                // poll for file system events on the WatchKey
                for (final WatchEvent<?> event : watchKey.pollEvents()) {
                    printEvent(event);
                }

                // if the watched directed gets deleted, get out of run method
                if (!watchKey.reset()) {
                    System.out.println("No longer valid");
                    watchKey.cancel();
                    watchService.close();
                    break;
                }
            }

        } catch (InterruptedException ex) {
            System.out.println("interrupted. Goodbye");
            return;
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
    }
}

public class FileWatcher {

    public static void main(String[] args) throws InterruptedException {
        Path pathToWatch = FileSystems.getDefault()
                                      .getPath(args[0]);
        DirWatcher dirWatcher = new DirWatcher(pathToWatch);
        Thread dirWatcherThread = new Thread(dirWatcher);
        dirWatcherThread.start();

        // interrupt the program after 10 seconds to stop it.
        Thread.sleep(20000);
        dirWatcherThread.interrupt();

    }

}
