package com.michaelmiklavcic.watcher;

import static org.junit.Assert.*;

import java.util.concurrent.ExecutorService;

import org.junit.*;
import org.mockito.Mock;

public class WatcherRunnerTest {

    @Mock ExecutorService executor;
    @Mock DirectoryWatcher watcher;
    
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void run_submits_watcher_to_executor_service() {
        WatcherRunner runner = new WatcherRunner(executor, watcher);
        fail("Not yet implemented");
    }

    // ExecutorService executor = Executors.newSingleThreadExecutor();
    // Future<?> future = executor.submit(watcher);
    // future.get();
}
