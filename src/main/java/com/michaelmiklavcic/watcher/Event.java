package com.michaelmiklavcic.watcher;

import java.nio.file.Path;
import java.util.List;

public interface Event {
    public List<Path> getPaths();
}
