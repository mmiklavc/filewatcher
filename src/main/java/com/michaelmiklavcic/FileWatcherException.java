package com.michaelmiklavcic;

public class FileWatcherException extends RuntimeException {

    private static final long serialVersionUID = 378692769239808253L;

    public FileWatcherException() {
        super();
    }

    public FileWatcherException(String message) {
        super(message);
    }

    public FileWatcherException(String message, Throwable cause) {
        super(message, cause);
    }
}
