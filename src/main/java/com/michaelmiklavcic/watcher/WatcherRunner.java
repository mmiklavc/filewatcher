package com.michaelmiklavcic.watcher;

import java.util.concurrent.ExecutorService;

import com.google.inject.Inject;

public class WatcherRunner implements EventRunner {

    @Inject
    public WatcherRunner(ExecutorService executor, DirectoryWatcher watcher) {
    }

    @Override
    public void run() {
    }

    @Override
    public void setContext(Handler handler) {
    }

}
