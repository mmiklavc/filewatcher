package com.michaelmiklavcic.watcher;

public interface EventRunner {

    void setContext(Handler handler);

    void run();

}
