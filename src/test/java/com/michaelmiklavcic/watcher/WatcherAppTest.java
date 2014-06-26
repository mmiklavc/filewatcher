package com.michaelmiklavcic.watcher;

import static org.mockito.Mockito.verify;

import org.junit.*;
import org.mockito.*;

public class WatcherAppTest {

    @Mock EventRunner runner;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void run_starts_runner() {
        WatcherApp app = new WatcherApp(runner);
        app.run();
        verify(runner).run();
    }

}
