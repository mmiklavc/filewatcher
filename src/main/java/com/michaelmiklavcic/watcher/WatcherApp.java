package com.michaelmiklavcic.watcher;

import com.google.inject.*;

public class WatcherApp {

    private final EventRunner runner;

    @Inject
    public WatcherApp(EventRunner runner) {
        this.runner = runner;
    }

    public void run() {
        runner.run();
    }

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new WatcherModule(args));
        WatcherApp app = injector.getInstance(WatcherApp.class);
        app.run();
    }

}
