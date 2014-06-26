package com.michaelmiklavcic.watcher;

import static java.lang.String.format;

import java.util.concurrent.*;

import org.apache.commons.cli.*;

import com.google.inject.AbstractModule;
import com.michaelmiklavcic.FileWatcherException;
import com.michaelmiklavcic.cli.CLIParser;

/**
 * Guice module for dependency injection
 */
public class WatcherModule extends AbstractModule {

    private String[] args;

    public WatcherModule(String[] args) {
        this.args = args;
    }

    @Override
    protected void configure() {
        CLIParser parser = new CLIParser();
        parser.addOption("handler", true, "");
        CommandLine cmdline = null;
        try {
            cmdline = parser.parse(args);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String handler = cmdline.getOptionValue("handler");
        bind(EventRunner.class).to(WatcherRunner.class);
        bind(Handler.class).to(getHandlerClass(handler));
        bind(ExecutorService.class).toInstance(Executors.newSingleThreadExecutor());
    }

    @SuppressWarnings("unchecked")
    // handled with exception
    private Class<Handler> getHandlerClass(String name) {
        try {
            return (Class<Handler>) Class.forName(name);
        } catch (ClassNotFoundException e) {
            throw new FileWatcherException(format("Unable to create handler for class '%s'", args[0]), e);
        }
    }

}
